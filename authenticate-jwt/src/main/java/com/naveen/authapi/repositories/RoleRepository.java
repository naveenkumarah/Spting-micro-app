package com.naveen.authapi.repositories;

import com.naveen.authapi.entities.Role;
import com.naveen.authapi.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(Role.roles name);

    Set<Role> findAllByName(Role.roles name);
}
