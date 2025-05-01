package co.edu.uni.acme.ariline.management.passenger.service.impl;

import co.edu.uni.acme.aerolinea.commons.dto.PassengerDTO;
import co.edu.uni.acme.aerolinea.commons.entity.DocumentTypeEntity;
import co.edu.uni.acme.aerolinea.commons.entity.PassengerEntity;
import co.edu.uni.acme.aerolinea.commons.utils.mappers.PassengerMapper;
import co.edu.uni.acme.aerolinea.commons.utils.mappers.TypeDocumentMapper;
import co.edu.uni.acme.ariline.management.passenger.repository.DocumentTypeRepository;
import co.edu.uni.acme.ariline.management.passenger.repository.PassengerUserRepository;
import co.edu.uni.acme.ariline.management.passenger.service.IPassengerService;
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
    public void updatePassenger(String code, PassengerDTO dto) {
        PassengerEntity existing = passengerRepository.findById(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PASSENGER_NOT_FOUND));

        validatePassenger(dto);

        existing.setNamePassenger(dto.getNamePassenger());
        existing.setLastNamePassenger(dto.getLastNamePassenger());
        existing.setPhonePassenger(dto.getPhonePassenger());
        existing.setNumberDocumentPassenger(dto.getNumberDocumentPassenger());
        existing.setEmailPassenger(dto.getEmailPassenger());
        existing.setBirthDate(dto.getBirthDate());
        existing.setGenderPassenger(dto.getGenderPassenger());
        existing.setHashPassword(dto.getHashPassword());

        String documentTypeCode = dto.getDocumentTypePassengerFk().getCodeTypeDocument();
        DocumentTypeEntity documentType = documentTypeRepository.findById(documentTypeCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, DOCUMENT_TYPE_NOT_FOUND));
        existing.setDocumentTypePassengerFk(documentType);

        passengerRepository.save(existing);
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
