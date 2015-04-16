package com.github.jneat.mybatis;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Data
public class TimeRow {

    private long id;

    private Instant instant;

    private LocalDate locald;

    private LocalTime localt;

    private LocalDateTime localdt;

    private OffsetDateTime offsetdt;

    private ZonedDateTime zoneddt;
}
