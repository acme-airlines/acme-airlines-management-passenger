// src/main/java/co/edu/uni/acme/ariline/management/passenger/service/impl/CreatePassengerService.java
package co.edu.uni.acme.ariline.management.passenger.service.impl;

import co.edu.uni.acme.aerolinea.commons.dto.*;
import co.edu.uni.acme.ariline.management.passenger.dto.*;
import co.edu.uni.acme.ariline.management.passenger.repository.*;
import co.edu.uni.acme.ariline.management.passenger.service.ICreatePassengerService;
import co.edu.uni.acme.ariline.management.passenger.utils.helper.Helpers;
import co.edu.uni.acme.ariline.management.passenger.utils.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import static co.edu.uni.acme.ariline.management.passenger.utils.constantes.Constants.*;

@Service
@RequiredArgsConstructor
public class CreatePassengerService implements ICreatePassengerService {

    private final PassengerMapper            passengerMapper;
    private final PassengerRepository        passengerRepository;
    private final PassengerFlightRepository  passengerFlightRepository;
    private final EmergencyInformationRepository emergencyInformationRepository;
    private final PassengerFlightMapper      passengerFlightMapper;
    private final EmergencyContactMapper     emergencyContactMapper;
    private final FlightRepository           flightRepository;

    private static final SecureRandom random  = new SecureRandom();
    private static final String      LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String      DIGITS  = "0123456789";
    private static final String      ALL     = LETTERS + DIGITS;

    @Override
    @Transactional
    public Map<String, String> createPassenger(BookingRequestDto booking) {
        String flightCode     = booking.getCodeFlight();
        String feeCode        = booking.getFeeCode();
        String codeCompanion  = null;
        Map<String, String>   codes = new HashMap<>();

        Long capacity = Helpers.checkType(
                flightRepository.capacityFlight(flightCode).get(0)[0],
                Long.class
        ).orElse(0L);

        if (countPassengersToRegister(booking) > capacity) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, PASSENGER_COUNT_EXCEEDS_CAPACITY
            );
        }

        MainPassengerDto main = booking.getMainPassenger();
        if (main.getAge() < 18 && main.getCompanion() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, MAIN_PASSENGER_MINOR_REQUIRES_COMPANION
            );
        }

        if (main.getCompanion() != null) {
            CompanionDto comp = main.getCompanion();
            if (comp.getRelationShip() == null || comp.getRelationShip().isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, COMPANION_RELATIONSHIP_REQUIRED
                );
            }
            codeCompanion = saveCompanion(comp, flightCode, feeCode);
            codes.put(comp.getRol(),codeCompanion);
        }

        String mainCode = saveMainPassenger(main, flightCode, feeCode, codeCompanion);
        codes.put(main.getRol(),mainCode);

        booking.getAdditionalPassengers().forEach(add -> {
            if (add.isSpecialCondition() && add.getEmergencyContact() == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, SPECIAL_CONDITION_EMERGENCY_CONTACT_REQUIRED
                );
            }
            if (add.getAge() < 18 && add.getEmergencyContact() == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, MINOR_EMERGENCY_CONTACT_REQUIRED
                );
            }

            String additionalCode = saveAdditionalPassenger(add, flightCode, feeCode);
            codes.put(add.getRol(),additionalCode);

            EmergencyContactDto ec = add.getEmergencyContact();
            if (ec != null) {
                if (ec.getRelationShip() == null || ec.getRelationShip().isEmpty()) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, EMERGENCY_CONTACT_RELATIONSHIP_REQUIRED
                    );
                }
                saveEmergencyContact(additionalCode,ec);
            }
        });

        return codes;
    }

    private String saveMainPassenger(
            MainPassengerDto main,
            String flightCode,
            String feeCode,
            String codeCompanion
    ) {
        PassengerDto dto = PassengerDto.builder()
                .code(generateCode())
                .firstName(main.getFirstName())
                .lastName(main.getLastName())
                .document(main.getDocument())
                .age(String.valueOf(main.getAge()))
                .role(main.getCompanion() != null ? "main-with-companion" : "main")
                .typeDocument(DocumentTypeDTO.builder()
                        .codeTypeDocument(main.getDocumentType())
                        .build())
                .email(main.getEmail())
                .genre(main.getGender())
                .birthDate(LocalDate.parse(main.getBirthDate()))
                .fee(FeeDTO.builder().codeFee(feeCode).build())
                .companion(codeCompanion != null
                        ? PassengerDto.builder().code(codeCompanion).build()
                        : null)
                .build();

        validatePassenger(dto);

        PassengerDto saved = passengerMapper.toDto(
                passengerRepository.save(passengerMapper.toEntity(dto))
        );

        linkPassengerToFlight(saved.getCode(), flightCode);
        return saved.getCode();
    }

    private String saveCompanion(
            CompanionDto comp,
            String flightCode,
            String feeCode
    ) {
        PassengerDto dto = PassengerDto.builder()
                .code(generateCode())
                .firstName(comp.getFirstName())
                .lastName(comp.getLastName())
                .document(comp.getDocument())
                .age(String.valueOf(comp.getAge()))
                .role("companion-main")
                .typeDocument(DocumentTypeDTO.builder()
                        .codeTypeDocument(comp.getDocumentType())
                        .build())
                .email(comp.getEmail())
                .genre(comp.getGender())
                .birthDate(LocalDate.parse(comp.getBirthDate()))
                .fee(FeeDTO.builder().codeFee(feeCode).build())
                .relationship(comp.getRelationShip())
                .build();

        validatePassenger(dto);

        PassengerDto saved = passengerMapper.toDto(
                passengerRepository.save(passengerMapper.toEntity(dto))
        );

        linkPassengerToFlight(saved.getCode(), flightCode);
        return saved.getCode();
    }

    private String saveAdditionalPassenger(
            AdditionalPassengerDto add,
            String flightCode,
            String feeCode
    ) {
        PassengerDto dto = PassengerDto.builder()
                .code(generateCode())
                .firstName(add.getFirstName())
                .lastName(add.getLastName())
                .document(add.getDocument())
                .age(String.valueOf(add.getAge()))
                .role("additional")
                .typeDocument(DocumentTypeDTO.builder()
                        .codeTypeDocument(add.getDocumentType())
                        .build())
                .email(add.getEmail())
                .birthDate(LocalDate.parse(add.getBirthDate()))
                .relationship(add.getRelationShip())
                .fee(FeeDTO.builder().codeFee(feeCode).build())
                .build();

        validatePassenger(dto);

        PassengerDto saved = passengerMapper.toDto(
                passengerRepository.save(passengerMapper.toEntity(dto))
        );

        linkPassengerToFlight(saved.getCode(), flightCode);
        return saved.getCode();
    }

    private void saveEmergencyContact(String code, EmergencyContactDto ec) {
        EmergencyInformationDto info = new EmergencyInformationDto();
        info.setName(ec.getName());
        info.setPassenger(PassengerDto.builder().code(code).build());
        info.setPhone(ec.getPhone());
        info.setRelationShip(ec.getRelationShip());
        emergencyInformationRepository.save(
                emergencyContactMapper.toEntity(info)
        );
    }

    private void linkPassengerToFlight(String passengerCode, String flightCode) {
        PassengerFlightIdDto id = new PassengerFlightIdDto();
        id.setPassengerCode(passengerCode);
        id.setFlightCode(flightCode);

        PassengerFlightDto pf = new PassengerFlightDto();
        pf.setId(id);
        pf.setPassenger(PassengerDto.builder().code(passengerCode).build());
        pf.setFlight(FlightDTO.builder().codeFlight(flightCode).build());

        passengerFlightRepository.save(
                passengerFlightMapper.toEntity(pf)
        );
    }

    private long countPassengersToRegister(BookingRequestDto booking) {
        long count = 1; // main
        if (booking.getMainPassenger().getCompanion() != null) {
            count++;
        }
        count += booking.getAdditionalPassengers().size();
        return count;
    }

    private String generateCode() {
        List<Character> chars = new ArrayList<>(10);
        chars.add(LETTERS.charAt(random.nextInt(LETTERS.length())));
        chars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        for (int i = 2; i < 10; i++) {
            chars.add(ALL.charAt(random.nextInt(ALL.length())));
        }
        Collections.shuffle(chars, random);

        StringBuilder sb = new StringBuilder(10);
        chars.forEach(sb::append);
        return sb.toString();
    }

    private void validatePassenger(PassengerDto dto) {
        if (dto.getFirstName() == null || dto.getFirstName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, NAME_REQUIRED);
        }
        if (dto.getLastName() == null || dto.getLastName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, LASTNAME_REQUIRED);
        }
        if (dto.getDocument() == null || !dto.getDocument().matches(DOCUMENT_REGEX)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, DOCUMENT_INVALID);
        }
        if (dto.getBirthDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BIRTHDATE_REQUIRED);
        }
        int age = Period.between(dto.getBirthDate(), LocalDate.now()).getYears();
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AGE_INVALID);
        }
    }
}
