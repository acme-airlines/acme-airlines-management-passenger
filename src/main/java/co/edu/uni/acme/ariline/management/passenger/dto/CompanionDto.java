package co.edu.uni.acme.ariline.management.passenger.dto;

import co.edu.uni.acme.aerolinea.commons.dto.DocumentTypeDTO;
import lombok.Data;

@Data
public class CompanionDto {

    private String firstName;

    private String  lastName;

    private String document;

    private String documentType;

    private String birthDate;

    private String gender;

    private String email;

    private int age;

    private String relationShip;

    private String rol;

}
