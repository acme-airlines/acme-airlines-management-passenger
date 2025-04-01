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

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Log4j2
public class CreatePassengerService implements ICreatePassengerService {

    private final PassengerUserRepository passengerUserRepository;

    private final PassengerMapper passengerMapper;

    @Override
    @Transactional
    public Boolean createPassenger(CreatePassengerDto passengerDTO) {
        boolean create = false;
        try{
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            passengerDTO.setCreationDate(LocalDate.now());
            passengerDTO.setHashPassword(passwordEncoder.encode(passengerDTO.getPassword()));
            passengerUserRepository.save(passengerMapper.dtoToEntity(passengerDTO));
            create = true;
        }catch(Exception ex){
            log.error("Error ", ex);
        }
        return create;
    }

}

