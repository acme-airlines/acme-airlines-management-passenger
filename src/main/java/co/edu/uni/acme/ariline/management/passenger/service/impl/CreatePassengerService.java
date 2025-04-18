package co.edu.uni.acme.ariline.management.passenger.service.impl;

import co.edu.uni.acme.aerolinea.commons.utils.mappers.PassengerMapper;
import co.edu.uni.acme.ariline.management.passenger.dto.CreatePassengerDto;
import co.edu.uni.acme.ariline.management.passenger.repository.PassengerUserRepository;
import co.edu.uni.acme.ariline.management.passenger.service.ICreatePassengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CreatePassengerService implements ICreatePassengerService {

    private final PassengerUserRepository passengerUserRepository;

    private final PassengerMapper passengerMapper;

    private static final String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMEROS = "0123456789";
    private static final String TODOS = LETRAS + NUMEROS;
    private static final SecureRandom random = new SecureRandom();

    @Override
    @Transactional
    public Boolean createPassenger(CreatePassengerDto passengerDTO) {
        boolean create = false;
        try{
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            passengerDTO.setCreationDate(LocalDate.now());
            passengerDTO.setCodePassenger(generarCodigo());
            passengerDTO.setHashPassword(passwordEncoder.encode(passengerDTO.getPassword()));
            passengerUserRepository.save(passengerMapper.dtoToEntity(passengerDTO));
            create = true;
        }catch(Exception ex){
            log.error("Error ", ex);
        }
        return create;
    }

    public  String generarCodigo() {
        // Se asegura de incluir al menos una letra y un número.
        List<Character> caracteres = new ArrayList<>(10);
        caracteres.add(LETRAS.charAt(random.nextInt(LETRAS.length())));
        caracteres.add(NUMEROS.charAt(random.nextInt(NUMEROS.length())));

        // Rellena los 8 caracteres restantes con letras y números al azar.
        for (int i = 2; i < 10; i++) {
            caracteres.add(TODOS.charAt(random.nextInt(TODOS.length())));
        }

        // Mezcla los caracteres para que la letra y el número iniciales se distribuyan aleatoriamente.
        Collections.shuffle(caracteres, random);

        // Construye y retorna la cadena resultante.
        StringBuilder codigo = new StringBuilder();
        for (char c : caracteres) {
            codigo.append(c);
        }
        return codigo.toString();
    }

}

