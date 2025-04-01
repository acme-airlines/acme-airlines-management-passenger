package co.edu.uni.acme.ariline.management.passenger.service;

import co.edu.uni.acme.ariline.management.passenger.dto.CreatePassengerDto;

public interface ICreatePassengerService {

    Boolean createPassenger(CreatePassengerDto passengerDTO);

}
