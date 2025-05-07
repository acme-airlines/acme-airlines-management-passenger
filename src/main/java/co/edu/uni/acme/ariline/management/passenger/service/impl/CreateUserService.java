package co.edu.uni.acme.ariline.management.passenger.service.impl;

import co.edu.uni.acme.aerolinea.commons.entity.FlightEntity;
import co.edu.uni.acme.aerolinea.commons.entity.UserEntity;
import co.edu.uni.acme.aerolinea.commons.utils.mappers.UserMapper;
import co.edu.uni.acme.ariline.management.passenger.dto.CreateUserDto;
import co.edu.uni.acme.ariline.management.passenger.repository.UserRepository;
import co.edu.uni.acme.ariline.management.passenger.service.ICreateUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CreateUserService implements ICreateUserService {

    private final UserRepository UserUserRepository;
    private final UserMapper UserMapper;

    private static final SecureRandom random = new SecureRandom();
    private static final String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMEROS = "0123456789";
    private static final String TODOS = LETRAS + NUMEROS;

    @Override
    @Transactional
    public Boolean createUser(CreateUserDto UserDTO) {
        try {
            log.info("🚀 Iniciando creación de pasajero: {}", UserDTO);

            int edad = java.time.Period.between(UserDTO.getBirthDate(), LocalDate.now()).getYears();
            if (edad < 18 && UserDTO.getEmergencyContact() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los menores de edad deben tener un contacto de emergencia.");
            }

            if (edad < 0 || edad > 120) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Edad no válida para el pasajero.");
            }


            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            UserDTO.setHashPassword(passwordEncoder.encode(UserDTO.getPassword()));
            UserDTO.setCreationDate(LocalDate.now());
            UserDTO.setCodeUser(generarCodigo());

            UserEntity entity = UserMapper.dtoToEntity(UserDTO);

            log.info("🗃️ Entidad lista para guardar: {}", entity);
            UserUserRepository.save(entity);

            log.info("✅ Pasajero creado exitosamente con código: {}", entity.getCodeUser());
            return true;

        } catch (ResponseStatusException e) {
            log.error("❌ Error controlado al crear pasajero: {}", e.getReason());
            throw e;
        } catch (Exception ex) {
            log.error("❌ Error inesperado al crear pasajero: {}", ex.getMessage(), ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al crear pasajero.");
        }
    }

    public String generarCodigo() {
        List<Character> caracteres = new ArrayList<>(10);
        caracteres.add(LETRAS.charAt(random.nextInt(LETRAS.length())));
        caracteres.add(NUMEROS.charAt(random.nextInt(NUMEROS.length())));
        for (int i = 2; i < 10; i++) {
            caracteres.add(TODOS.charAt(random.nextInt(TODOS.length())));
        }
        Collections.shuffle(caracteres, random);
        StringBuilder codigo = new StringBuilder();
        for (char c : caracteres) {
            codigo.append(c);
        }
        return codigo.toString();
    }
}