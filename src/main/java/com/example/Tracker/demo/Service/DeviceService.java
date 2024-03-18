package com.example.Tracker.demo.Service;

import com.example.Tracker.demo.Model.Device;

import java.util.List;

public interface DeviceService  {

    Device createDevice(Device device);
    List<Device> getAllDevices();
    Device getDeviceById(Long deviceId);
    Device updateDevice(Long deviceId, Device updatedDevice);
    void deleteDevice(Long deviceId);
     List<String> getTaskStatusOptions();
     List <Device> searchDevices(String keyword);


}
