package com.example.Tracker.demo.Repository;

import com.example.Tracker.demo.Model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity,Long> {
}
