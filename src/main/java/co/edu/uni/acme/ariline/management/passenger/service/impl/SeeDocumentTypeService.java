package co.edu.uni.acme.ariline.management.passenger.service.impl;

import co.edu.uni.acme.aerolinea.commons.dto.DocumentTypeDTO;
import co.edu.uni.acme.aerolinea.commons.utils.exceptions.GlobalExceptionHandler;
import co.edu.uni.acme.aerolinea.commons.utils.mappers.TypeDocumentMapper;
import co.edu.uni.acme.ariline.management.passenger.repository.DocumentTypeRepository;
import co.edu.uni.acme.ariline.management.passenger.service.ISeeDocumentTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class SeeDocumentTypeService implements ISeeDocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;

    private final TypeDocumentMapper typeDocumentMapper;

    @Override
    public List<DocumentTypeDTO> seeAllDocumentType() {
        List<DocumentTypeDTO> documentType = new ArrayList<>();
        try{
            documentType = typeDocumentMapper.listEntityToListDto(documentTypeRepository.findAll());
        }catch (Exception ex){
            log.error(ex);
        }
        return documentType;
    }
}
