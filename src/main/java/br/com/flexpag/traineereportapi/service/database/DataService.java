package br.com.flexpag.traineereportapi.service.database;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Classe responsavel por recolher dados do banco de dados.
 */
@Service
@RequiredArgsConstructor
public class DataService {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Consulta de dados para gerar relatório do tipo client
     * @param clientId Id do cliente. Usado como filtro
     * @return Uma lista com os ddados mapeados em String Object
     */
    public List<Map<String, Object>> fetchClientFromDatabase(Long clientId) {

        String sql = """
                SELECT id, first_name, last_name, identity, contract_type, contract_number, address_id
                FROM clients
                """;

        if (clientId != null) {
            return jdbcTemplate.queryForList(
                    "%sWHERE id = %d".formatted(sql, clientId)
            );
        }

        return jdbcTemplate.queryForList(sql);

    }

    /**
     * Consulta de dados para gerar relatório do tipo transaction
     * @param paymentType Tipo de pagamento da transação
     * @param status Status de pagamento da transação
     * @return Uma lista com os ddados mapeados em String Object
     */
    public List<Map<String, Object>> fetchTransactionFromDatabase(
            String paymentType,
            String status) {

        String sql = """
                SELECT id, payment_type, installments, status, authorization_code, purchase_id
                FROM transactions
                """;

        if (paymentType == null) {
            if (status == null) {
                return jdbcTemplate.queryForList(sql);
            } else {
                return jdbcTemplate.queryForList("%sWHERE status = '%s'".formatted(sql, status));
            }
        } else if (status == null) {
            return jdbcTemplate.queryForList(
                    "%sWHERE payment_type = '%s'".formatted(sql, paymentType));
        }

        return jdbcTemplate.queryForList(
                "%sWHERE payment_type = '%s'%nAND status = '%s'".formatted(sql, paymentType, status));
    }

}
