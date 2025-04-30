package co.edu.uni.acme.airline.passenger.module.repository;

import co.edu.uni.acme.aerolinea.commons.entity.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity, String> {

    /**
     * Busca un pasajero por su número de documento.
     *
     * @param numberDocument Número de documento del pasajero.
     * @return Optional con el pasajero si existe.
     */
    Optional<PassengerEntity> findByNumberDocumentPassenger(String numberDocument);

    /**
     * Verifica si existe un pasajero con el mismo número de documento.
     *
     * @param numberDocument Número de documento del pasajero.
     * @return true si existe, false si no.
     */
    boolean existsByNumberDocumentPassenger(String numberDocument);


}
