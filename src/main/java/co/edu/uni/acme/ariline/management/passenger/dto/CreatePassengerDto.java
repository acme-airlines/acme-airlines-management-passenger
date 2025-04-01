package co.edu.uni.acme.ariline.management.passenger.dto;

import co.edu.uni.acme.aerolinea.commons.dto.PassengerDTO;
import lombok.Data;

@Data
public class CreatePassengerDto extends PassengerDTO {

    private String password;

}
