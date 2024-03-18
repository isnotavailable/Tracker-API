package com.example.Tracker.demo.Repository;

import com.example.Tracker.demo.Model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device,Long> {

    List<Device> findByDeviceNameContaining(String keyword);
}
