package br.com.flexpag.traineereportapi.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enums para o Funcionamento do Factory
 */
@RequiredArgsConstructor
@Getter
public enum ReportTypeEnum {

    CLIENT("clientFileService"),
    TRANSACTION("transactionFileService");

    private final String type;

}
