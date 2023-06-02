package br.com.flexpag.traineereportapi.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ReportTypeEnum {

    CLIENT("clientFileService"),
    TRANSACTION("transactionFileService");

    private final String type;

}
