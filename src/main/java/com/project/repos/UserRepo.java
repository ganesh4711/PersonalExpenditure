package com.project.repos;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.Users;

public interface UserRepo extends JpaRepository<Users, String> {

}
