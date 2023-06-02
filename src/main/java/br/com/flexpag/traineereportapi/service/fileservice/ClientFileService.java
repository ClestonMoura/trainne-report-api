package br.com.flexpag.traineereportapi.service.fileservice;

import br.com.flexpag.traineereportapi.service.database.DataService;
import br.com.flexpag.traineereportapi.service.excel.ExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Classe responsavel por gerar o relatório do tipo client
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ClientFileService implements FileService {

    private final DataService dataService;

    @Override
    public ByteArrayOutputStream getFileReport(String reportType,
                                               Long clientId,
                                               String paymentType,
                                               String status) throws IOException {
        List<Map<String, Object>> data = dataService.fetchClientFromDatabase(clientId);
        return ExcelService.generateExcel(data);
    }

}
