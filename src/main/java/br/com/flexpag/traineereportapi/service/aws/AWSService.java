package br.com.flexpag.traineereportapi.service.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Classe de serviços da AWS. Responsavel por gerar o nome do arquivo, armazenamento e
 * retornar o arquivo salvo na bucket
 */
@Service
@RequiredArgsConstructor
public class AWSService {

    private final AmazonS3 amazonS3;

    private int number = 0;

    /**
     * Armazena um novo arquivo no S3
     * @param bucketName Nome do arquivo
     * @param outputStream Arquivo que será salvo
     */
    public void putObject(String bucketName, ByteArrayOutputStream outputStream) {

        var inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        var metadata = new ObjectMetadata();
        metadata.setContentLength(outputStream.size());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, generateName(), inputStream, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);

        amazonS3.putObject(putObjectRequest);

    }

    /**
     * Método auxiliar para gerar o nome do arquivo
     * @return String
     */
    private String generateName() {
        number++;
        return "report-%d.xlsx".formatted(number);
    }

    /**
     * Método que faz o Download do arquivo do S3 bucket e o converte em ByteArrayOutputStream
     * @param bucketName Nome do bucket
     * @param objectName Nome do arquivo
     * @return Arquivo convertido
     * @throws IOException Caso haja erro de conversão
     */
    public ByteArrayOutputStream downloadReport(String bucketName, String objectName) throws IOException {

        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketName, objectName));
        var outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = s3Object.getObjectContent().read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        return outputStream;

    }
}
