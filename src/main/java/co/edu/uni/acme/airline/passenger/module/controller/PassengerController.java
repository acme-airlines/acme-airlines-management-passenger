package co.edu.uni.acme.airline.passenger.module.controller;

import co.edu.uni.acme.aerolinea.commons.dto.PassengerDTO;
import co.edu.uni.acme.airline.passenger.module.service.IPassengerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/passengers")
public class PassengerController {

    private final IPassengerService passengerService;

    public PassengerController(IPassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> registerPassenger(@RequestBody @Valid PassengerDTO dto) {
        PassengerDTO created = passengerService.registerPassenger(dto);
        return ResponseEntity.status(201).body(Map.of(
                "message", "Pasajero creado correctamente",
                "data", created
        ));
    }

    @GetMapping
    public ResponseEntity<List<PassengerDTO>> getAllPassengers() {
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    @GetMapping("/{codePassenger}")
    public ResponseEntity<Map<String, Object>> getPassengerByCode(@PathVariable String codePassenger) {
        PassengerDTO passenger = passengerService.getPassengerByCode(codePassenger);
        return ResponseEntity.ok(Map.of(
                "message", "Pasajero encontrado",
                "data", passenger
        ));
    }

    @PutMapping("/{codePassenger}")
    public ResponseEntity<Map<String, String>> updatePassenger(@PathVariable String codePassenger,
                                                               @RequestBody @Valid PassengerDTO dto) {
        passengerService.updatePassenger(codePassenger, dto);
        return ResponseEntity.ok(Map.of("message", "Pasajero actualizado correctamente"));
    }

    @DeleteMapping("/{codePassenger}")
    public ResponseEntity<Map<String, String>> deletePassenger(@PathVariable String codePassenger) {
        passengerService.deletePassenger(codePassenger);
        return ResponseEntity.ok(Map.of("message", "Pasajero eliminado correctamente"));
    }
}

