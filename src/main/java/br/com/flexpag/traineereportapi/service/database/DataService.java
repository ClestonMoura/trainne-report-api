package br.com.flexpag.traineereportapi.service.database;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataService {

    private final JdbcTemplate jdbcTemplate;

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
