package co.edu.uni.acme.ariline.management.passenger.controller;

import co.edu.uni.acme.ariline.management.passenger.dto.BookingRequestDto;
import co.edu.uni.acme.ariline.management.passenger.dto.CreateUserDto;
import co.edu.uni.acme.ariline.management.passenger.service.ICreatePassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/passenger")
@RequiredArgsConstructor
public class PassengerController {

    private final ICreatePassengerService iCreatePassengerService;

    @PostMapping("create")
    public ResponseEntity<List<String>> createPassenger(@RequestBody BookingRequestDto booking) {
        return ResponseEntity.ok(iCreatePassengerService.createPassenger(booking));
    }

}
