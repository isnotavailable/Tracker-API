package com.example.Tracker.demo.Repository;

import com.example.Tracker.demo.Model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
}
