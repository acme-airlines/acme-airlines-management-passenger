package co.edu.uni.acme.ariline.management.passenger.utils.mapper;

import co.edu.uni.acme.aerolinea.commons.dto.EmergencyInformationDto;
import co.edu.uni.acme.aerolinea.commons.dto.PassengerFlightDto;
import co.edu.uni.acme.aerolinea.commons.entity.EmergencyInformationEntity;
import co.edu.uni.acme.aerolinea.commons.entity.PassengerFlightEntity;
import co.edu.uni.acme.ariline.management.passenger.dto.EmergencyContactDto;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface EmergencyContactMapper {

    EmergencyInformationDto toDto(EmergencyInformationEntity entity);

    EmergencyInformationEntity toEntity(EmergencyInformationDto dto);

}
