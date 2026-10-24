package com.library.stockgap.infrastructure.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ActiveDatasetRepository {
    private final JdbcTemplate jdbcTemplate;

    public ActiveDatasetRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UUID> getActiveEpoch() {
        var list = jdbcTemplate.query(
            "SELECT dataset_epoch FROM runtime.active_dataset WHERE singleton = true",
            (rs, rowNum) -> UUID.fromString(rs.getString("dataset_epoch"))
        );
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public UUID requireActiveEpoch() {
        return getActiveEpoch().orElseThrow(() -> new IllegalStateException("Aucune époque de dataset active"));
    }
}
