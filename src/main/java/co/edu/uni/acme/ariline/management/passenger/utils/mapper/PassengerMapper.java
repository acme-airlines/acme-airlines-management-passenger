package co.edu.uni.acme.ariline.management.passenger.utils.mapper;

import co.edu.uni.acme.aerolinea.commons.dto.PassengerDto;
import co.edu.uni.acme.aerolinea.commons.entity.PassengerEntity;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface PassengerMapper {

    PassengerDto toDto(PassengerEntity entity);

    PassengerEntity toEntity(PassengerDto dto);

}
