package co.edu.uni.acme.ariline.management.passenger.service;


import co.edu.uni.acme.aerolinea.commons.dto.UserDTO;

import java.util.List;

public interface IUserService {


    /**
     * Actualiza un pasajero existente por código único.
     *
     * @param code         Código del pasajero a actualizar.
     * @param userDTO Nuevos datos del usuario.
     */
    void updateUser(String code, UserDTO userDTO);

    /**
     * Obtiene todos los pasajeros registrados.
     *
     * @return Lista de usuarios.
     */
    List<UserDTO> getAllUsers();

    /**
     * Obtiene un pasajero por su código único.
     *
     * @param code Código del pasajero.
     * @return El DTO del pasajero si existe.
     */
    UserDTO getUserByCode(String code);

    /**
     * Elimina un pasajero por código.
     *
     * @param code Código del pasajero a eliminar.
     */
    void deleteUser(String code);
}

