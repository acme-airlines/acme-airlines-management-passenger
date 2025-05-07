package co.edu.uni.acme.ariline.management.passenger.dto;

import co.edu.uni.acme.aerolinea.commons.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class CreateUserDto extends UserDTO {

    private String password;

    @Valid
    private EmergencyContactDto emergencyContact;

    private String codeFlight;

}
