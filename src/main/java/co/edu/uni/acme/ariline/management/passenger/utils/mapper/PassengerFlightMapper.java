package co.edu.uni.acme.ariline.management.passenger.utils.mapper;

import co.edu.uni.acme.aerolinea.commons.dto.PassengerFlightDto;
import co.edu.uni.acme.aerolinea.commons.entity.PassengerFlightEntity;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = PassengerFlightIdMapper.class
)
public interface PassengerFlightMapper {

    PassengerFlightDto toDto(PassengerFlightEntity entity);

    PassengerFlightEntity toEntity(PassengerFlightDto dto);

}
