package br.com.flexpag.traineereportapi.service.fileservice;

import br.com.flexpag.traineereportapi.service.enums.ReportTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ServiceFactory {

    private final Map<String, FileService> services;

    public FileService getService(String fileType) {
        var type = ReportTypeEnum.valueOf(fileType);
        return services.get(type.getType());
    }

}
