package com.example.Tracker.demo.Service;

import com.example.Tracker.demo.Model.*;
import com.example.Tracker.demo.Repository.*;
import net.sf.jsqlparser.util.validation.ValidationException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {


    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final LocationRepository locationRepository;

    @Autowired
    private final ActivityRepository activityRepository;

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    public DeviceServiceImpl(
            DeviceRepository deviceRepository,
            UserRepository userRepository,
            LocationRepository locationRepository,
            ActivityRepository activityRepository,
            CategoryRepository categoryRepository) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.activityRepository = activityRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Device createDevice(Device device) {
        try {
            // Create or update the user
            User userRequest = device.getUser();
            User newUser = null;

            if (userRequest != null) {
                if (userRequest.getId() != null) {
                    // If the user has an ID, check if it exists in the database
                    if (userRepository.existsById(userRequest.getId())) {
                        newUser = userRepository.findById(userRequest.getId()).orElse(null);
                    }
                }

                // If the user is still null, it's a new user or the existing user was not found
                if (newUser == null) {
                    newUser = userRepository.save(userRequest);
                }
            }


            // Create the device
            Device newDevice = new Device();
            newDevice.setDeviceName(device.getDeviceName());
            newDevice.setUser(newUser);
            newDevice.setTaskStatus(device.getTaskStatus());
            newDevice.setDescription(device.getDescription()); // Set the description

            newDevice = deviceRepository.save(newDevice);
            // Create and associate locations
            List<Location> locationRequests = device.getLocations();
            List<Location> locations = new ArrayList<>();

            for (Location locationRequest : locationRequests) {
                Location location = new Location();
                location.setLocation(locationRequest.getLocation());
                location.setDevice(newDevice);
                locations.add(location);
            }

            locationRepository.saveAll(locations);

            // Create and associate activities with categories
            List<Activity> activityRequests = device.getActivities();
            List<Activity> activities = new ArrayList<>();

            for (Activity activityRequest : activityRequests) {
                Activity activity = new Activity();
                activity.setStartTime(activityRequest.getStartTime());
                activity.setEndTime(activityRequest.getEndTime());

                // Create or update the category
                Category categoryRequest = activityRequest.getCategory();
                Category category = null;

                if (categoryRequest != null) {
                    category = categoryRepository.save(categoryRequest);
                }

                activity.setCategory(category);
                activity.setDevice(newDevice);
                activities.add(activity);
            }

            activityRepository.saveAll(activities);

            // Update the device with associated locations and activities
            newDevice.setLocations(locations);
            newDevice.setActivities(activities);
            deviceRepository.save(newDevice);

            return newDevice;

        } catch (Exception e) {
            throw new ServiceException("Error creating device: " + e.getMessage());
        }
    }

    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public Device getDeviceById(Long deviceId) {
        return deviceRepository.findById(deviceId).orElse(null);
    }

    @Override
    public Device updateDevice(Long deviceId, Device updatedDevice) {

        System.out.println("Received updated device: " + updatedDevice.toString());
        System.out.println("Updated taskStatus: " + updatedDevice.getTaskStatus());
        return deviceRepository.findById(deviceId)
                .map(existingDevice -> {
                    existingDevice.setDeviceName(updatedDevice.getDeviceName());
                    existingDevice.setUser(updatedDevice.getUser());
                    existingDevice.setLocations(updatedDevice.getLocations());
                    existingDevice.setActivities(updatedDevice.getActivities());
                    existingDevice.setTaskStatus(updatedDevice.getTaskStatus());
                    existingDevice.setDescription(updatedDevice.getDescription());
                    // Add other fields as needed

                    return deviceRepository.save(existingDevice);
                })
                .orElseThrow(() -> new ValidationException("Device not found with id " + deviceId));
    }

    @Override
    public void deleteDevice(Long deviceId) {
        deviceRepository.findById(deviceId).ifPresent(deviceRepository::delete);
    }

    @Override
    public List<String> getTaskStatusOptions() {
        List<String> taskStatusOptions = Arrays.stream(TaskStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return taskStatusOptions;
    }
    public List<Device> searchDevices(String keyword) {
        // Implement the logic for searching devices based on the provided keyword
        // For simplicity, let's assume you want to search by device name
        if (keyword != null && !keyword.isEmpty()) {
            return deviceRepository.findByDeviceNameContaining(keyword);
        } else {
            // Return all devices if no keyword is provided
            return getAllDevices();
        }
    }

}
