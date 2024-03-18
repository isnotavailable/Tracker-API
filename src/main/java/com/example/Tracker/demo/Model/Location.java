package com.example.Tracker.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    private String timestamp;

    @ManyToOne
    @JsonBackReference
    private Device device;

    public Location(){}

    public Location(String location) {
        this.location = location;
    }


    public Location(String location, String timestamp) {
        this.location = location;
        this.timestamp = timestamp;
    }

    public Location(String location, String timestamp, Device device) {
        this.location = location;
        this.timestamp = timestamp;
        this.device = device;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
