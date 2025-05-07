package co.edu.uni.acme.ariline.management.passenger.service;

import co.edu.uni.acme.ariline.management.passenger.dto.BookingRequestDto;

import java.util.List;

public interface ICreatePassengerService {

    List<String> createPassenger(BookingRequestDto booking);


}
