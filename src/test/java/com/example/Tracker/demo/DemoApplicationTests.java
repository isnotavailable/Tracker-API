package com.example.Tracker.demo;
import com.example.Tracker.demo.Model.*;
import com.example.Tracker.demo.Repository.ActivityRepository;
import com.example.Tracker.demo.Repository.CategoryRepository;
import com.example.Tracker.demo.Repository.LocationRepository;
import com.example.Tracker.demo.Repository.UserRepository;
import com.example.Tracker.demo.Service.DeviceServiceImpl;

import com.example.Tracker.demo.Controller.DeviceController;
import com.example.Tracker.demo.Service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private DeviceServiceImpl deviceService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private CategoryRepository categoryRepository;


	@Test
	void testCreateDevice() {
		// Create a user
		User user = new User();
		user.setUsername("testUser");
		User savedUser = userRepository.save(user);

		// Create a location
		Location location = new Location();
		location.setLocation("Test Location");

		// Create an activity with a category
		Category category = new Category();
		category.setCategoryName("Test Category");
		Category savedCategory = categoryRepository.save(category);

		Activity activity = new Activity();
		activity.setStartTime("2022-01-01T12:00:00");
		activity.setEndTime("2022-01-01T14:00:00");
		activity.setCategory(savedCategory);

		// Create a device with the user, location, and activity
		Device device = new Device();
		device.setDeviceName("Test Device");
		device.setUser(savedUser);

		List<Location> locations = new ArrayList<>();
		locations.add(location);
		device.setLocations(locations);

		List<Activity> activities = new ArrayList<>();
		activities.add(activity);
		device.setActivities(activities);

		// Save the device using the service
		Device createdDevice = deviceService.createDevice(device);

		// Check if the device is created with associated data
		assertNotNull(createdDevice.getId());
		assertEquals("Test Device", createdDevice.getDeviceName());
		assertNotNull(createdDevice.getUser());
		assertNotNull(createdDevice.getLocations());
		assertFalse(createdDevice.getLocations().isEmpty());
		assertNotNull(createdDevice.getActivities());
		assertFalse(createdDevice.getActivities().isEmpty());
	}

	@Test
	void testGetAllDevices() {
		// Assuming there are some devices in the database
		List<Device> devices = deviceService.getAllDevices();
		assertNotNull(devices);
		assertFalse(devices.isEmpty());
	}

	@Test
	void testGetDeviceById() {
		// Assuming there is at least one device in the database
		List<Device> devices = deviceService.getAllDevices();
		assertFalse(devices.isEmpty());

		// Get the first device
		Device firstDevice = devices.get(0);

		// Retrieve the device by ID using the service
		Device retrievedDevice = deviceService.getDeviceById(firstDevice.getId());

		assertNotNull(retrievedDevice);
		assertEquals(firstDevice.getId(), retrievedDevice.getId());
	}

	@Test
	void testUpdateDevice() {
		// Assuming there is at least one device in the database
		List<Device> devices = deviceService.getAllDevices();
		assertFalse(devices.isEmpty());

		// Get the first device
		Device firstDevice = devices.get(0);

		// Modify the device's name
		String updatedDeviceName = "Updated Device Name";
		firstDevice.setDeviceName(updatedDeviceName);

		// Update the device using the service
		Device updatedDevice = deviceService.updateDevice(firstDevice.getId(), firstDevice);

		assertNotNull(updatedDevice);
		assertEquals(updatedDeviceName, updatedDevice.getDeviceName());
	}

	@Test
	void testDeleteDevice() {
		// Assuming there is at least one device in the database
		List<Device> devices = deviceService.getAllDevices();
		assertFalse(devices.isEmpty());

		// Get the first device
		Device firstDevice = devices.get(0);

		// Delete the device using the service
		deviceService.deleteDevice(firstDevice.getId());

		// Try to retrieve the deleted device
		Device deletedDevice = deviceService.getDeviceById(firstDevice.getId());

		assertNull(deletedDevice);
	}


}