package org.abol.springstarter.services;

import org.abol.springstarter.models.BaseUser;
import org.abol.springstarter.models.Role;
import org.abol.springstarter.repositories.BaseUserRepository;
import org.abol.springstarter.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class UserServiceImplTest {

    @Mock
    private BaseUserRepository baseUserRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUser() {
        // Given
        BaseUser user = new BaseUser();
        user.setEmail("test@example.com");
        user.setPassword("password");

        Role role = new Role();
        role.setName("ROLE_USER");

        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(role);

        // When
        userService.saveUser(user);

        // Then
        assertThat(user.getPassword()).isEqualTo("encodedPassword");
        assertThat(user.getRoles()).contains(role);
    }

    @Test
    public void testGetUserById() {
        // Given
        BaseUser user = new BaseUser();
        user.setId(1);
        user.setEmail("test@example.com");

        when(baseUserRepository.findById(1)).thenReturn(Optional.of(user));

        // When
        BaseUser foundUser = userService.getUserById(1);

        // Then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(1);
        assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
    }
}
