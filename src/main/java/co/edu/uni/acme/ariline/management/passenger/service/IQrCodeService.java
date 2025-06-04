package co.edu.uni.acme.ariline.management.passenger.service;

import co.edu.uni.acme.aerolinea.commons.entity.QrEntity;

public interface IQrCodeService {

    void writeQrToFile(String text, int width, int height, String filePath) throws Exception;

    QrEntity createAndSaveQr(String userCode, String flightCode) throws Exception;

}
