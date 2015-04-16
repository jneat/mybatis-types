package com.github.jneat.mybatis;

import static org.assertj.core.api.Assertions.*;

import com.github.jneat.MapTools;

import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TypesTest {

    SqlSession session;

    TypesMapper mapper;

    @BeforeClass
    public void before() {
        //PostgresqlSuite.INIT();
        session = TestSuite.getSessionFactory().openSession();
        mapper = session.getMapper(TypesMapper.class);
    }

    @AfterClass
    public void after() {
        session.commit();
        session.close();
    }

    @Test(priority = 0, dataProvider = "properties")
    public void propertiesPut(long id, Map<String, String> kv, Properties props) {
        assertThat(mapper.set(new TypesRow(id, kv, props))).isEqualTo(1);
    }

    @Test(priority = 1, dataProvider = "properties")
    public void propertiesGet(long id, Map<String, String> kv, Properties props) {
        TypesRow row = mapper.get(id);

        if (id == 0) {
            assertThat(row.getProperties()).isNotNull();
            assertThat(row.getProperties().isEmpty()).isTrue();
            assertThat(row.getKeyvalue()).isNotNull();
            assertThat(row.getKeyvalue().isEmpty()).isTrue();
        } else {
            assertThat(row.getId()).isEqualTo(id);
            assertThat(row.getProperties()).isEqualTo(props);
            for (Map.Entry<String, String> e : kv.entrySet()) {
                assertThat(row.getKeyvalue().get(e.getKey())).isEqualTo(e.getValue());
            }
        }
    }

    @DataProvider(name = "properties")
    public Object[][] propertiesData() {
        return new Object[][]{
            new Object[]{0L, null, null},
            new Object[]{1L,
                MapTools.createStr("p1", "foo", "p2", "baz", "p3", "luke"),
                MapTools.createProperties("p1", "foo", "p2", "baz", "p3", "luke")
            },
            new Object[]{2L, new HashMap<String, String>(), new Properties()}
        };
    }
}
