package br.com.flexpag.traineereportapi.controller;

import br.com.flexpag.traineereportapi.service.aws.AWSService;
import br.com.flexpag.traineereportapi.service.fileservice.ServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Classe principal de requisições da API
 */
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ServiceFactory serviceFactory;
    private final AWSService awsService;
    private static final String BUCKET_NAME = "trainee-payments-api";

    /**
     * Requisição principal para a criação do relatório
     * @param reportType tipo de relatório
     * @param clientId parâmetro opcional do id do cliente
     * @param paymentType parâmetro opcional do tipo de pagamento
     * @param status parâmetro opcional do status do pagamento
     * @return ByteArrayOutputStream
     * @throws IOException caso aconteça um erro na escrita do arquivo
     */
    @GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> createReport(@RequestParam String reportType,
                                  @RequestParam(required = false) Long clientId,
                                  @RequestParam(required = false) String paymentType,
                                  @RequestParam(required = false) String status) throws IOException {

        ByteArrayOutputStream outputStream = serviceFactory
                .getService(reportType)
                .getFileReport(reportType, clientId, paymentType, status);
        awsService.putObject(BUCKET_NAME, outputStream);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "report.xlsx");

        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }

    /**
     * Requisição para fazer o download do relatório armazenado no S3
     * @param objectName O nome do arquivo armazenado
     * @return ByteArrayOutputStream
     * @throws IOException Caso Aconteça um erro de conversão do arquivo
     */
    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadReport(@RequestParam String objectName) throws IOException {

        ByteArrayOutputStream outputStream = awsService.downloadReport(BUCKET_NAME, objectName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "%s.xlsx".formatted(objectName));

        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }

}
