package co.edu.uni.acme.ariline.management.passenger.controller;

import co.edu.uni.acme.aerolinea.commons.entity.QrEntity;
import co.edu.uni.acme.ariline.management.passenger.dto.BookingRequestDto;
import co.edu.uni.acme.ariline.management.passenger.service.IQrCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/qr")
@RequiredArgsConstructor
public class QrController {

    private final IQrCodeService iQrCodeService;

    @GetMapping("create")
    public ResponseEntity<QrEntity> createAndSaveQr(@RequestParam String userCode, @RequestParam String flightCode) throws Exception {
        return ResponseEntity.ok(iQrCodeService.createAndSaveQr(userCode, flightCode));
    }

}
