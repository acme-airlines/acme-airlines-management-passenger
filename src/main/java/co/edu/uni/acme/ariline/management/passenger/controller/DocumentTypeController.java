package co.edu.uni.acme.ariline.management.passenger.controller;

import co.edu.uni.acme.aerolinea.commons.dto.DocumentTypeDTO;
import co.edu.uni.acme.ariline.management.passenger.service.ISeeDocumentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/document-type")
@RequiredArgsConstructor
public class DocumentTypeController {

    private final ISeeDocumentTypeService iSeeDocumentTypeService;

    @GetMapping("all")
    public ResponseEntity<List<DocumentTypeDTO>> allDocumentType() {
        List<DocumentTypeDTO> documentsType = iSeeDocumentTypeService.seeAllDocumentType();
        return ResponseEntity.status(200).body(documentsType);
    }

}
