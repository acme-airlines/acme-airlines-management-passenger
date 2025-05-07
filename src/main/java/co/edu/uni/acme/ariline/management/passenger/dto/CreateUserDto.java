package co.edu.uni.acme.ariline.management.passenger.dto;

import co.edu.uni.acme.aerolinea.commons.dto.UserDTO;
import lombok.Data;

@Data
public class CreateUserDto extends UserDTO {

    private String password;

}
