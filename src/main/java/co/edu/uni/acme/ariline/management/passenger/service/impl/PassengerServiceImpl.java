package co.edu.uni.acme.ariline.management.passenger.service.impl;

import co.edu.uni.acme.aerolinea.commons.dto.PassengerDTO;
import co.edu.uni.acme.aerolinea.commons.entity.DocumentTypeEntity;
import co.edu.uni.acme.aerolinea.commons.entity.PassengerEntity;
import co.edu.uni.acme.aerolinea.commons.utils.mappers.PassengerMapper;
import co.edu.uni.acme.aerolinea.commons.utils.mappers.TypeDocumentMapper;
import co.edu.uni.acme.ariline.management.passenger.repository.DocumentTypeRepository;
import co.edu.uni.acme.ariline.management.passenger.repository.PassengerUserRepository;
import co.edu.uni.acme.ariline.management.passenger.service.IPassengerService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static co.edu.uni.acme.ariline.management.passenger.utils.constantes.Constants.*;

@Service
public class PassengerServiceImpl implements IPassengerService {

    private final PassengerUserRepository passengerRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final TypeDocumentMapper typeDocumentMapper;
    private final PassengerMapper passengerMapper;

    public PassengerServiceImpl(PassengerUserRepository passengerRepository, DocumentTypeRepository documentTypeRepository, TypeDocumentMapper typeDocumentMapper, PassengerMapper passengerMapper) {
        this.passengerRepository = passengerRepository;
        this.documentTypeRepository = documentTypeRepository;
        this.typeDocumentMapper = typeDocumentMapper;
        this.passengerMapper = passengerMapper;
    }

    @Override
    public List<PassengerDTO> getAllPassengers() {
        return passengerMapper.listEntityToListDto(passengerRepository.findAll());
    }

    @Override
    public PassengerDTO getPassengerByCode(String code) {
        return passengerRepository.findById(code)
                .map(passengerMapper::entityToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PASSENGER_NOT_FOUND));
    }

    @Override
    public void deletePassenger(String code) {
        if (!passengerRepository.existsById(code)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, PASSENGER_NOT_FOUND);
        }
        passengerRepository.deleteById(code);
    }

    @Override
    @Transactional
    public void updatePassenger(String codePassenger, PassengerDTO dto) {
        PassengerEntity existing = passengerRepository.findById(codePassenger)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pasajero no encontrado"));

        // Conserva la contraseña original si no se proporciona una nueva
        if (dto.getHashPassword() == null || dto.getHashPassword().isBlank()) {
            dto.setHashPassword(existing.getHashPassword());
        }

        // También puedes mantener otros datos inmutables si aplica
        dto.setCodePassenger(codePassenger);
        dto.setCreationDate(existing.getCreationDate());
        //dto.setCodeFlightFk(existing.getCodeFlightFk());

        PassengerEntity updated = passengerMapper.dtoToEntity(dto);
        passengerRepository.save(updated);
    }


    private void validatePassenger(PassengerDTO dto) {
        if (dto.getNamePassenger() == null || dto.getNamePassenger().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, NAME_REQUIRED);
        }

        if (dto.getLastNamePassenger() == null || dto.getLastNamePassenger().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, LASTNAME_REQUIRED);
        }

        if (dto.getNumberDocumentPassenger() == null || !dto.getNumberDocumentPassenger().matches(DOCUMENT_REGEX)) {
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
