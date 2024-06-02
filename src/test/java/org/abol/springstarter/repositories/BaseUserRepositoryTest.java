package org.abol.springstarter.repositories;

import org.abol.springstarter.models.BaseUser;
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
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class BaseUserRepositoryTest {

    @Autowired
    private BaseUserRepository baseUserRepository;

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
    public void testFindByEmail() {
        // Given
        BaseUser user = new BaseUser();
        user.setEmail("test@example.com");
        user.setPassword("password");

        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);

        user.setRoles(Collections.singletonList(role));
        baseUserRepository.save(user);

        // When
        BaseUser foundUser = baseUserRepository.findByEmail("test@example.com");

        // Then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
    }
}
