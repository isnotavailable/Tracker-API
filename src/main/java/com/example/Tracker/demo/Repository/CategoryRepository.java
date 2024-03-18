package com.example.Tracker.demo.Repository;

import com.example.Tracker.demo.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
