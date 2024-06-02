package org.abol.springstarter.repositories;

import org.abol.springstarter.models.Role;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @BeforeAll
    public static void createDatabase() {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/?user=root&password=rootPassword1");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS testdb");
        } catch (Exception e) {
            throw new RuntimeException("Failed to create test database", e);
        }
    }

    @AfterAll
    public static void dropDatabase() {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/?user=root&password=rootPassword1");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP DATABASE IF EXISTS testdb");
        } catch (Exception e) {
            throw new RuntimeException("Failed to drop test database", e);
        }
    }

    @Test
    public void testSaveRole() {
        // Given
        Role role = new Role();
        role.setName("ROLE_ADMIN");

        // When
        roleRepository.save(role);

        // Then
        Role foundRole = roleRepository.findById(role.getId()).orElse(null);
        assertThat(foundRole).isNotNull();
        assertThat(foundRole.getName()).isEqualTo("ROLE_ADMIN");
    }
}
