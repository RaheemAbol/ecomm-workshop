package org.abol.springstarter.repositories;

import org.abol.springstarter.models.BaseUser;
import org.abol.springstarter.models.CartItem;
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
public class CartItemRepositoryTest {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BaseUserRepository baseUserRepository;

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
    public void testSaveCartItem() {
        // Given
        BaseUser user = new BaseUser();
        user.setEmail("user@example.com");
        user.setPassword("password");
        baseUserRepository.save(user);

        CartItem item = new CartItem();
        item.setName("Test Item");
        item.setDescription("Test Description");
        item.setPrice(10.0);
        item.setUser(user);

        // When
        cartItemRepository.save(item);

        // Then
        CartItem foundItem = cartItemRepository.findById(item.getId()).orElse(null);
        assertThat(foundItem).isNotNull();
        assertThat(foundItem.getName()).isEqualTo("Test Item");
    }
}
