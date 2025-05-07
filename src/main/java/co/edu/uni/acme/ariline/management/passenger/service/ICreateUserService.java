package co.edu.uni.acme.ariline.management.passenger.service;

import co.edu.uni.acme.ariline.management.passenger.dto.CreateUserDto;

public interface ICreateUserService {

    Boolean createUser(CreateUserDto passengerDTO);

}
