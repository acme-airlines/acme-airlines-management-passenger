package co.edu.uni.acme.ariline.management.passenger.controller;

import co.edu.uni.acme.aerolinea.commons.dto.PassengerDTO;
import co.edu.uni.acme.ariline.management.passenger.dto.CreatePassengerDto;
import co.edu.uni.acme.ariline.management.passenger.service.ICreatePassengerService;
import co.edu.uni.acme.ariline.management.passenger.service.IPassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/public/passenger")
@RequiredArgsConstructor
public class PassengerController {

    private final ICreatePassengerService iCreatePassengerService;

    private final IPassengerService iPassengerService;

    @PostMapping("create")
    public ResponseEntity<String> createPassenger(@RequestBody CreatePassengerDto passenger) {
        boolean isCreate = iCreatePassengerService.createPassenger(passenger);
        return ResponseEntity.status(isCreate ? 200 : 400).body(isCreate ? "Se creo correctamente el passager" : "No se creo el passager");
    }

    @GetMapping
    public ResponseEntity<List<PassengerDTO>> getAllPassengers() {
        return ResponseEntity.ok(iPassengerService.getAllPassengers());
    }

    @GetMapping("/{codePassenger}")
    public ResponseEntity<Map<String, Object>> getPassengerByCode(@PathVariable String codePassenger) {
        PassengerDTO passenger = iPassengerService.getPassengerByCode(codePassenger);
        return ResponseEntity.ok(Map.of(
                "message", "Pasajero encontrado",
                "data", passenger
        ));
    }

    @PutMapping("/{codePassenger}")
    public ResponseEntity<Map<String, String>> updatePassenger(@PathVariable String codePassenger,
                                                               @RequestBody @Valid PassengerDTO dto) {
        iPassengerService.updatePassenger(codePassenger, dto);
        return ResponseEntity.ok(Map.of("message", "Pasajero actualizado correctamente"));
    }

    @DeleteMapping("/{codePassenger}")
    public ResponseEntity<Map<String, String>> deletePassenger(@PathVariable String codePassenger) {
        iPassengerService.deletePassenger(codePassenger);
        return ResponseEntity.ok(Map.of("message", "Pasajero eliminado correctamente"));
    }

}
