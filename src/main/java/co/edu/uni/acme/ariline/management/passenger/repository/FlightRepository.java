package co.edu.uni.acme.ariline.management.passenger.repository;

import co.edu.uni.acme.aerolinea.commons.entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, String> {

    @Query(value = """
        SELECT f.capacity FROM acme_airlines.flight f where f.code_flight = :codeFlight 
    """, nativeQuery = true)
    List<Object[]> capacityFlight(@Param("codeFlight")  String codeFlight);

}
