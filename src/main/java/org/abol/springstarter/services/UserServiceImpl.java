package org.abol.springstarter.services;

import org.abol.springstarter.models.BaseUser;
import org.abol.springstarter.models.Role;
import org.abol.springstarter.repositories.BaseUserRepository;
import org.abol.springstarter.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BaseUserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void saveUser(BaseUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            userRole = checkRoleExist("ROLE_USER");
        }
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);
    }

    private Role checkRoleExist(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }

    @Override
    public List<BaseUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public BaseUser getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        BaseUser user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    @Override
    public BaseUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}