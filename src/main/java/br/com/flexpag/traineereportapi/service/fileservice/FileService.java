package br.com.flexpag.traineereportapi.service.fileservice;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public interface FileService {

    ByteArrayOutputStream getFileReport(String reportType,
                                        Long clientId,
                                        String paymentType,
                                        String status) throws IOException;

}
