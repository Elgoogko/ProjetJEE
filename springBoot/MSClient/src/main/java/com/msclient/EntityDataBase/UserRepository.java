package com.msclient.EntityDataBase;

import org.springframework.data.jpa.repository.JpaRepository;
import com.msclient.EntityDataBase.User;

public interface UserRepository extends JpaRepository<User, Long> {
}