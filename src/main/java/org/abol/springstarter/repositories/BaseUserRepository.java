package org.abol.springstarter.repositories;

import org.abol.springstarter.models.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseUserRepository extends JpaRepository<BaseUser, Integer> {
    BaseUser findByEmail(String email);
}