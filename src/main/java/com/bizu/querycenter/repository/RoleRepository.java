package com.bizu.querycenter.repository;

import com.bizu.querycenter.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, Integer> {

    Role findByName(String name);
}
