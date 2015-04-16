package com.github.jneat.mybatis;

import static org.assertj.core.api.Assertions.*;

import org.testng.annotations.*;
import org.apache.ibatis.session.SqlSession;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class TimeTest {

    SqlSession session;

    TimeMapper mapper;

    @BeforeClass
    public void before() {
        //PostgresqlSuite.INIT();
        session = TestSuite.getSessionFactory().openSession();
        mapper = session.getMapper(TimeMapper.class);
    }

    @AfterClass
    public void after() {
        session.close();
    }

    @Test(priority = 0, dataProvider = "time")
    public void timePut(long id, Instant instant, LocalTime localt) {
        TimeRow row = new TimeRow();
        row.setId(id);
        row.setInstant(instant);
        row.setLocalt(localt);
        assertThat(mapper.set(row)).isEqualTo(1);
        session.commit();
    }

    @Test(priority = 1, dataProvider = "time")
    public void timeGet(long id, Instant instant, LocalTime localt) {
        TimeRow row = mapper.get(id);
        if (id % 10 == 0) {
            assertThat(row.getInstant()).isNull();
            assertThat(row.getLocalt()).isNull();
        } else {
            assertThat(row.getInstant()).isEqualTo(instant);
            assertThat(row.getLocalt()).isEqualTo(localt);
        }
    }

    Object[][] timeCache;

    @DataProvider(name = "time")
    public synchronized Object[][] timeData() {
        if (timeCache == null) {
            timeCache = new Object[][]{
                new Object[]{10L, null, null},
                new Object[]{11L, Instant.now(), LocalTime.now().truncatedTo(ChronoUnit.SECONDS)},};
        }
        return timeCache;
    }

    @Test(priority = 0, dataProvider = "date")
    public void datePut(long id, LocalDateTime localdt, OffsetDateTime offsetdt, ZonedDateTime zoneddt) {
        TimeRow row = new TimeRow();
        row.setId(id);
        row.setLocaldt(localdt);
        row.setOffsetdt(offsetdt);
        row.setZoneddt(zoneddt);
        assertThat(mapper.set(row)).isEqualTo(1);
        session.commit();
    }

    @Test(priority = 1, dataProvider = "date")
    public void dateGet(long id, LocalDateTime localdt, OffsetDateTime offsetdt, ZonedDateTime zoneddt) {
        TimeRow row = mapper.get(id);
        if (id % 10 == 0) {
            assertThat(row.getLocaldt()).isNull();
            assertThat(row.getOffsetdt()).isNull();
            assertThat(row.getZoneddt()).isNull();
        } else {
            assertThat(row.getLocaldt()).isEqualTo(localdt);
            assertThat(row.getOffsetdt().isEqual(offsetdt)).isTrue();
            assertThat(row.getZoneddt().isEqual(zoneddt)).isTrue();
        }
    }

    Object[][] dateCache;

    @DataProvider(name = "date")
    public synchronized Object[][] dateData() {
        if (dateCache == null) {
            dateCache = new Object[][]{
                new Object[]{20L, null, null, null},
                new Object[]{21L, LocalDateTime.now(), OffsetDateTime.now(), ZonedDateTime.now()},
                new Object[]{22L, LocalDateTime.now(), OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC), ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Z"))},
                new Object[]{23L, LocalDateTime.now(), OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.of("+14:00")), ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Z"))},
                new Object[]{24L, LocalDateTime.now(), OffsetDateTime.now().withOffsetSameLocal(ZoneOffset.MAX), ZonedDateTime.now().withZoneSameLocal(ZoneId.of("GMT+13"))},
                new Object[]{25L, LocalDateTime.now(), OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.MIN), ZonedDateTime.now().withZoneSameInstant(ZoneId.of("GMT-12"))}
            };
        }
        return dateCache;
    }
}
