package co.edu.uni.acme.ariline.management.passenger.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookingRequestDto {

    private String codeFlight;

    private String feeCode;

    private MainPassengerDto mainPassenger;

    private List<AdditionalPassengerDto>  additionalPassengers;

}
