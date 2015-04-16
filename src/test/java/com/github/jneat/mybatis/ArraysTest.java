package com.github.jneat.mybatis;

import static org.assertj.core.api.Assertions.*;

import org.testng.annotations.*;
import org.apache.ibatis.session.SqlSession;

public class ArraysTest {

    SqlSession session;

    ArraysMapper mapper;

    @BeforeClass
    public void before() {
        //PostgresqlSuite.INIT();
        session = TestSuite.getSessionFactory().openSession();
        mapper = session.getMapper(ArraysMapper.class);
    }

    @AfterClass
    public void after() {
        session.close();
    }

    @Test(priority = 0, dataProvider = "shorts")
    public void shortsPut(long id, Boolean[] booleans) {
        ArraysRow row = new ArraysRow();
        row.setId(id);
        row.setBooleans(booleans);
        assertThat(mapper.set(row)).isEqualTo(1);
        session.commit();
    }

    @Test(priority = 1, dataProvider = "shorts")
    public void numbersGet(long id, Boolean[] booleans) {
        ArraysRow row = mapper.get(id);
        if (row.getId() % 10 == 0) {
            assertThat(row.getBooleans()).isNull();
        } else {
            assertThat(row.getBooleans()).isEqualTo(booleans);
        }
    }

    @DataProvider(name = "shorts")
    public Object[][] shortsData() {
        return new Object[][]{
            new Object[]{10L, null},
            new Object[]{11L, new Boolean[0]},
            new Object[]{12L, new Boolean[]{true, false, true}}
        };
    }

    @Test(priority = 0, dataProvider = "numbers")
    public void numbersPut(long id, Integer[] ints, Long[] longs, Double[] doubles) {
        ArraysRow row = new ArraysRow();
        row.setId(id);
        row.setIntegers(ints);
        row.setLongs(longs);
        row.setDoubles(doubles);
        assertThat(mapper.set(row)).isEqualTo(1);
        session.commit();
    }

    @Test(priority = 1, dataProvider = "numbers")
    public void numbersGet(long id, Integer[] ints, Long[] longs, Double[] doubles) {
        ArraysRow row = mapper.get(id);
        if (row.getId() % 10 == 0) {
            assertThat(row.getIntegers()).isNull();
            assertThat(row.getLongs()).isNull();
            assertThat(row.getDoubles()).isNull();
        } else {
            assertThat(row.getIntegers()).isEqualTo(ints);
            assertThat(row.getLongs()).isEqualTo(longs);
            assertThat(row.getDoubles()).isEqualTo(doubles);
        }
    }

    @DataProvider(name = "numbers")
    public Object[][] numbersData() {
        return new Object[][]{
            new Object[]{30L, null, null, null},
            new Object[]{31L, new Integer[0], new Long[0], new Double[0]},
            new Object[]{32L, new Integer[]{1, 33, 12}, new Long[]{3L, 5L, 6L}, new Double[]{3.3, 4.4, 5.5}},};
    }

    @Test(priority = 0, dataProvider = "strings")
    public void stringsPut(long id, String[] strings, String[][] strings2d) {
        ArraysRow row = new ArraysRow();
        row.setId(id);
        row.setStrings(strings);
        row.setStrings2d(strings2d);
        assertThat(mapper.set(row)).isEqualTo(1);
        session.commit();
    }

    @Test(priority = 1, dataProvider = "strings")
    public void stringsGet(long id, String[] strings, String[][] strings2d) {
        ArraysRow row = mapper.get(id);
        if (row.getId() % 10 == 0) {
            assertThat(row.getStrings()).isNull();
            assertThat(row.getStrings2d()).isNull();
        } else {
            assertThat(row.getStrings()).isEqualTo(strings);
            assertThat(row.getStrings2d()).isEqualTo(strings2d);
        }
    }

    @DataProvider(name = "strings")
    public Object[][] stringsData() {
        return new Object[][]{
            new Object[]{60L, null, null},
            new Object[]{61L, new String[]{"foo", "bar", "baz"}, new String[][]{new String[]{"2dfoo", "2dbar", "2dbaz"}}},
            new Object[]{62L, new String[0], new String[][]{new String[]{"2dfoo", "2dbar", "2dbaz"}, new String[]{"2dabra", "2dca", "2ddabra"}}},
            new Object[]{63L, new String[0], new String[0][0]}
        };
    }
}
