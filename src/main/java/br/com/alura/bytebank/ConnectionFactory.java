package br.com.alura.bytebank;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionFactory {
    public Connection recuperarConexao() {
        try {
            return createDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private HikariDataSource createDataSource() {
        String host = System.getenv("PGHOST");
        String port = System.getenv("PGPORT");
        String database = System.getenv("PGDATABASE");
        String user = System.getenv("PGUSER");
        String password = System.getenv("PGPASSWORD");

        String url;

        if (host != null && !host.isEmpty()) {
            url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
        } else {
            try {
                Properties props = loadProperties();
                url = props.getProperty("db.url");
                user = props.getProperty("db.user");
                password = props.getProperty("db.password");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        return new HikariDataSource(config);
    }




    private Properties loadProperties() throws IOException {
        try (FileInputStream fs = new FileInputStream("config.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        }
    }
}