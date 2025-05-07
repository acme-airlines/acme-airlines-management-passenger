package co.edu.uni.acme.ariline.management.passenger.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmergencyContactDto {

    @NotBlank
    private String name;

    @NotBlank
    private String relationShip;

    @NotBlank
    private String phone;
}
