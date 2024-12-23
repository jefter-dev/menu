package br.edu.ifpb.poo.menu.configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DataSourceConfigurationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testConnection() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection, "A conexão com o banco de dados falhou.");
            System.out.println("Conexão com o banco de dados estabelecida com sucesso.");
        }
    }
}
