package co.edu.uni.acme.ariline.management.passenger.controller;

import co.edu.uni.acme.aerolinea.commons.dto.UserDTO;
import co.edu.uni.acme.ariline.management.passenger.dto.CreateUserDto;
import co.edu.uni.acme.ariline.management.passenger.service.ICreateUserService;
import co.edu.uni.acme.ariline.management.passenger.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final ICreateUserService iCreateUserService;

    private final IUserService iPassengerService;

    @PostMapping("create")
    public ResponseEntity<String> createPassenger(@RequestBody CreateUserDto passenger) {
        boolean isCreate = iCreateUserService.createUser(passenger);
        return ResponseEntity.status(isCreate ? 200 : 400).body(isCreate ? "Se creo correctamente el usuario" : "No se creo el passager");
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllPassengers() {
        return ResponseEntity.ok(iPassengerService.getAllUsers());
    }

    @GetMapping("/{codePassenger}")
    public ResponseEntity<Map<String, Object>> getPassengerByCode(@PathVariable String codePassenger) {
        UserDTO user = iPassengerService.getUserByCode(codePassenger);
        return ResponseEntity.ok(Map.of(
                "message", "Usuario encontrado",
                "data", user
        ));
    }

    @PutMapping("/{codePassenger}")
    public ResponseEntity<Map<String, String>> updatePassenger(@PathVariable String codePassenger,
                                                               @RequestBody @Valid UserDTO dto) {
        iPassengerService.updateUser(codePassenger, dto);
        return ResponseEntity.ok(Map.of("message", "Usuario actualizado correctamente"));
    }

    @DeleteMapping("/{codePassenger}")
    public ResponseEntity<Map<String, String>> deletePassenger(@PathVariable String codePassenger) {
        iPassengerService.deleteUser(codePassenger);
        return ResponseEntity.ok(Map.of("message", "Usuario eliminado correctamente"));
    }

}
