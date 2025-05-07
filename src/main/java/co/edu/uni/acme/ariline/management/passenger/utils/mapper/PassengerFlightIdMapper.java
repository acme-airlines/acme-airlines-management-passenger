package co.edu.uni.acme.ariline.management.passenger.utils.mapper;

import co.edu.uni.acme.aerolinea.commons.dto.PassengerFlightIdDto;
import co.edu.uni.acme.aerolinea.commons.entity.PassengerFlightId;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface PassengerFlightIdMapper {

    PassengerFlightIdDto toDto(PassengerFlightId passengerFlightId);

    PassengerFlightId toEntity(PassengerFlightIdDto passengerFlightIdDto);

}
