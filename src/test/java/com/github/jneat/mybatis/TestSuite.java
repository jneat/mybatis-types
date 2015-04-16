package com.github.jneat.mybatis;

import static org.assertj.core.api.Assertions.*;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import javax.sql.DataSource;

public class TestSuite {

    private static SqlSessionFactory ssf;

    static SqlSessionFactory getSessionFactory() {
        return ssf;
    }

    static synchronized void setupSessionFactoryBuilder(DataSource ds) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("jneat", transactionFactory, ds);

        Configuration configuration = new Configuration(environment);
        configuration.getTypeHandlerRegistry().register("com.github.jneat.mybatis");
        configuration.setMapUnderscoreToCamelCase(true);

        // Add Mappers
        configuration.addMapper(TypesMapper.class);
        configuration.addMapper(ArraysMapper.class);
        configuration.addMapper(TimeMapper.class);

        ssf = new SqlSessionFactoryBuilder().build(configuration);
    }

    static String getResourceAsString(String resource) throws IOException {
        String query;
        try (final InputStream is = TestSuite.class.getResourceAsStream(resource)) {

            assertThat(is).isNotNull();

            StringWriter stringWriter = new StringWriter();
            int b;
            while ((b = is.read()) != -1) {
                stringWriter.write(b);
            }
            query = stringWriter.toString();
        }
        return query;
    }
}
