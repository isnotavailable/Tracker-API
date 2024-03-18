package com.example.Tracker.demo.Repository;

import com.example.Tracker.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
