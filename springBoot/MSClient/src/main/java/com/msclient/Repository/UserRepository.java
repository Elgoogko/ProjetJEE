package com.msclient.Repository;

import com.msclient.Entity.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository :
 * -> Permet de communiquer avec la BDD
 * -> Exécuter les opérations CRUD (Create, Read, Update, Delete)
 */

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

}
