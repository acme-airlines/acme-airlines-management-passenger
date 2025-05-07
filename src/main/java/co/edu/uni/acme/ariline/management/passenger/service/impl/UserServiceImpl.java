package co.edu.uni.acme.ariline.management.passenger.service.impl;

import co.edu.uni.acme.aerolinea.commons.dto.UserDTO;
import co.edu.uni.acme.aerolinea.commons.entity.DocumentTypeEntity;
import co.edu.uni.acme.aerolinea.commons.entity.UserEntity;
import co.edu.uni.acme.aerolinea.commons.entity.UserEntity;
import co.edu.uni.acme.aerolinea.commons.utils.mappers.TypeDocumentMapper;
import co.edu.uni.acme.aerolinea.commons.utils.mappers.UserMapper;
import co.edu.uni.acme.ariline.management.passenger.repository.DocumentTypeRepository;
import co.edu.uni.acme.ariline.management.passenger.repository.UserRepository;
import co.edu.uni.acme.ariline.management.passenger.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static co.edu.uni.acme.ariline.management.passenger.utils.constantes.Constants.*;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final UserMapper userMapper;

 
    @Override
    public List<UserDTO> getAllUsers() {
        return userMapper.listEntityToListDto(userRepository.findAll());
    }

    @Override
    public UserDTO getUserByCode(String code) {
        return userRepository.findById(code)
                .map(userMapper::entityToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PASSENGER_NOT_FOUND));
    }

    @Override
    public void deleteUser(String code) {
        if (!userRepository.existsById(code)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, PASSENGER_NOT_FOUND);
        }
        userRepository.deleteById(code);
    }

    @Override
    public void updateUser(String code, UserDTO dto) {
        UserEntity existing = userRepository.findById(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PASSENGER_NOT_FOUND));

        validateUser(dto);

        existing.setNameUser(dto.getNameUser());
        existing.setLastNameUser(dto.getLastNameUser());
        existing.setPhoneUser(dto.getPhoneUser());
        existing.setNumberDocumentUser(dto.getNumberDocumentUser());
        existing.setEmailUser(dto.getEmailUser());
        existing.setBirthDate(dto.getBirthDate());
        existing.setGenderUser(dto.getGenderUser());
        existing.setHashPassword(dto.getHashPassword());

        String documentTypeCode = dto.getDocumentTypeUserFk().getCodeTypeDocument();
        DocumentTypeEntity documentType = documentTypeRepository.findById(documentTypeCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, DOCUMENT_TYPE_NOT_FOUND));
        existing.setDocumentTypeUserFk(documentType);

        userRepository.save(existing);
    }

    private void validateUser(UserDTO dto) {
        if (dto.getNameUser() == null || dto.getNameUser().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, NAME_REQUIRED);
        }

        if (dto.getLastNameUser() == null || dto.getLastNameUser().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, LASTNAME_REQUIRED);
        }

        if (dto.getNumberDocumentUser() == null || !dto.getNumberDocumentUser().matches(DOCUMENT_REGEX)) {
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
