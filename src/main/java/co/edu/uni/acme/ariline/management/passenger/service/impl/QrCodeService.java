package co.edu.uni.acme.ariline.management.passenger.service.impl;

import co.edu.uni.acme.aerolinea.commons.entity.QrEntity;
import co.edu.uni.acme.ariline.management.passenger.repository.QrRepository;
import co.edu.uni.acme.ariline.management.passenger.service.IQrCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QrCodeService implements IQrCodeService {


    private final QrRepository qrRepository;

    /**
     * Genera un QR código PNG a partir del texto provisto.
     *
     * @param text   El contenido a codificar (por ejemplo un JSON con userCode y flightCode).
     * @param width  Ancho del QR en píxeles.
     * @param height Alto del QR en píxeles.
     * @return Array de bytes con la imagen PNG.
     * @throws Exception Si ocurre algún error durante la generación.
     */
    private byte[] generateQrImage(String text, int width, int height) throws Exception {
        // Configuración de hints (nivel de corrección de errores, encoding, margen, etc.)
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M); // nivel medio de corrección
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1); // margen mínimo alrededor

        // Creamos la matriz de bits para el QR a partir del texto
        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                text,
                BarcodeFormat.QR_CODE,
                width,
                height,
                hints
        );

        // Convertimos el BitMatrix a PNG en un ByteArrayOutputStream
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
            return baos.toByteArray();
        }
    }

    /**
     * Genera un QR (con un contenido JSON que incluye userCode y flightCode),
     * lo persiste en la tabla QR y retorna la entidad guardada.
     *
     * @param userCode   El código de usuario que irá dentro del QR.
     * @param flightCode El código de vuelo que irá dentro del QR.
     * @return QrEntity recién persistida.
     * @throws Exception Si falla la generación del QR.
     */
    public QrEntity createAndSaveQr(String userCode, String flightCode) throws Exception {
        // 1) Construir el JSON interno del QR.
        //    Aquí usamos un JSON muy simple. Si tienes Jackson disponible, podrías serializar un Map directamente.
        String contenidoQr = String.format(
                "{\"user\":\"%s\",\"flight\":\"%s\"}",
                userCode,
                flightCode
        );

        // 2) Generar el PNG como byte[]
        byte[] qrBytes = generateQrImage(contenidoQr, 300, 300);

        // 3) Preparar la entidad para persistir
        QrEntity entity = new QrEntity();

        // Vamos a usar un UUID para el código de QR (puedes cambiar la lógica según tus necesidades)
        String codigoQr = UUID.randomUUID().toString();
        entity.setCodigoQr(codigoQr);

        // Fecha de creación = hoy
        LocalDate hoy = LocalDate.now();
        entity.setDateCreatedQr(hoy);

        // Fecha de expiración = hoy + 1 año (puedes ajustar tu propia regla de negocio)
        LocalDate expiracion = hoy.plusYears(1);
        entity.setDateExpiredQr(expiracion);

        // Los bytes de la imagen
        entity.setImagenQr(qrBytes);

        // 4) Persistir en la base de datos
        return qrRepository.save(entity);
    }

    /**
     * (Opcional) Opción para persistir a disco como .png, si alguna vez se desea guardar localmente:
     */
    public void writeQrToFile(String text, int width, int height, String filePath) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                text,
                BarcodeFormat.QR_CODE,
                width,
                height,
                hints
        );

        java.nio.file.Path path = java.nio.file.FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

}
