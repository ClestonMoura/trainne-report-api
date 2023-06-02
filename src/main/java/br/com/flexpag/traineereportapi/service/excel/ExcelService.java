package br.com.flexpag.traineereportapi.service.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExcelService {

    private ExcelService() {}

    public static ByteArrayOutputStream generateExcel(List<Map<String, Object>> data) throws IOException {

        if (data == null) {
            throw new NullPointerException("Data is empty!");
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("client-report");

            Row headerRow = sheet.createRow(0);
            int colNum = 0;
            for (String columnName : data.get(0).keySet()) {
                Cell cell = headerRow.createCell(colNum++);
                cell.setCellValue(columnName);
            }

            int rowNum = 1;
            for (Map<String, Object> rowData : data) {
                Row row = sheet.createRow(rowNum++);
                colNum = 0;
                for (Object value: rowData.values()) {
                    Cell cell = row.createCell(colNum++);
                    cell.setCellValue(value != null ? value.toString() : "");
                    sheet.autoSizeColumn(cell.getColumnIndex());
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream;
        } catch (IOException e) {
            throw new IOException("Error generating Excel file", e);
        }
    }

}
