package co.edu.uni.acme.ariline.management.passenger.service;

import co.edu.uni.acme.aerolinea.commons.dto.PassengerDTO;

import java.util.List;

public interface IPassengerService {


    /**
     * Actualiza un pasajero existente por código único.
     *
     * @param code         Código del pasajero a actualizar.
     * @param passengerDTO Nuevos datos del pasajero.
     */
    void updatePassenger(String code, PassengerDTO passengerDTO);

    /**
     * Obtiene todos los pasajeros registrados.
     *
     * @return Lista de pasajeros.
     */
    List<PassengerDTO> getAllPassengers();

    /**
     * Obtiene un pasajero por su código único.
     *
     * @param code Código del pasajero.
     * @return El DTO del pasajero si existe.
     */
    PassengerDTO getPassengerByCode(String code);

    /**
     * Elimina un pasajero por código.
     *
     * @param code Código del pasajero a eliminar.
     */
    void deletePassenger(String code);
}

