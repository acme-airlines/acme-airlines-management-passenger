package co.edu.uni.acme.ariline.management.passenger.service;

import co.edu.uni.acme.ariline.management.passenger.dto.BookingRequestDto;

import java.util.List;
import java.util.Map;

public interface ICreatePassengerService {

    Map<String, String> createPassenger(BookingRequestDto booking);


}
