package co.edu.uni.acme.ariline.management.passenger.controller;

import co.edu.uni.acme.ariline.management.passenger.dto.CreatePassengerDto;
import co.edu.uni.acme.ariline.management.passenger.service.ICreatePassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/passenger")
@RequiredArgsConstructor
public class PassengerController {

    private final ICreatePassengerService iCreatePassengerService;

    @PostMapping("create")
    public ResponseEntity<String> createPassenger(@RequestBody CreatePassengerDto passenger) {
        boolean isCreate = iCreatePassengerService.createPassenger(passenger);
        return ResponseEntity.status(isCreate ? 200 : 400).body(isCreate ? "Se creo correctamente el passager" : "No se creo el passager");
    }

}
