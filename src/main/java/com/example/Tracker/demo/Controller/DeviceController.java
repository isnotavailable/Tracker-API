    package com.example.Tracker.demo.Controller;

    import com.example.Tracker.demo.Model.Device;
    import com.example.Tracker.demo.Service.DeviceService;
    import net.sf.jsqlparser.util.validation.ValidationException;
    import org.springframework.data.crossstore.ChangeSetPersister;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @CrossOrigin(origins = "http://localhost:3000")
    @RestController
    @RequestMapping("api/devices")
    public class DeviceController {

        private final DeviceService deviceService;

        public DeviceController(DeviceService deviceService) {
            this.deviceService = deviceService;
        }

        @GetMapping
        public ResponseEntity<List<Device>> getAllDevices() {
            List<Device> devices = deviceService.getAllDevices();
            return new ResponseEntity<>(devices, HttpStatus.OK);
        }

        @GetMapping("/{deviceId}")
        public ResponseEntity<Device> getDeviceById(@PathVariable Long deviceId) {
            Device device = deviceService.getDeviceById(deviceId);
            return (device != null)
                    ? new ResponseEntity<>(device, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @PostMapping
        public ResponseEntity<?> createDevice(@RequestBody Device device) {
            try {
                Device createdDevice = deviceService.createDevice(device);
                return new ResponseEntity<>(createdDevice, HttpStatus.CREATED);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("An unexpected error occurred: " + e.getMessage());
            }

        }

        @PutMapping("/{deviceId}")
        public ResponseEntity<Device> updateDevice(@PathVariable Long deviceId, @RequestBody Device updatedDevice) {
            try {
                System.out.println("Received updated device: " + updatedDevice.toString());  // Add this line for logging

                Device savedDevice = deviceService.updateDevice(deviceId, updatedDevice);
                return new ResponseEntity<>(savedDevice, HttpStatus.OK);
            } catch (ValidationException ex) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            }

        @DeleteMapping("/{deviceId}")
        public ResponseEntity<Void> deleteDevice(@PathVariable Long deviceId) {
            deviceService.deleteDevice(deviceId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @GetMapping("/search")
        public ResponseEntity<List<Device>> searchDevices(@RequestParam(required = false) String keyword) {
            System.out.println("Received search keyword: " + keyword); // Add this line for logging

            try {
                List<Device> searchResults = deviceService.searchDevices(keyword);
                return new ResponseEntity<>(searchResults, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
