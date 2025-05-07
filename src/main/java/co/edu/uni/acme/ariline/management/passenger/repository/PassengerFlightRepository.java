package co.edu.uni.acme.ariline.management.passenger.repository;

import co.edu.uni.acme.aerolinea.commons.entity.PassengerFlightEntity;
import co.edu.uni.acme.aerolinea.commons.entity.PassengerFlightId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerFlightRepository extends JpaRepository<PassengerFlightEntity, PassengerFlightId> {
}
