package edu.neu.cs4500.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.repositories.ServiceRepository;

@RestController
@CrossOrigin(origins = "*")
public class ServiceService {
  @Autowired
  ServiceRepository serviceRepository;

  @GetMapping("/api/services")
  public List<Service> findAllService() {
    return serviceRepository.findAllServices();
  }

  @GetMapping("/api/services/{serviceId}")
  public Service findServiceById(
          @PathVariable("serviceId") Integer id) {
    return serviceRepository.findServiceById(id);
  }

  @GetMapping("api/services/category/{categoryId}/limit/{size}")
  public List<Service> findPopularServicesByCategory(
          @PathVariable("categoryId") Integer categoryId,
          @PathVariable("size") Integer size) {
    return serviceRepository.findPopularServicesByCategory(categoryId, size);
  }

  @PostMapping("/api/services")
  public Service createService(@RequestBody Service service) {
    return serviceRepository.save(service);
  }

  @PutMapping("/api/services/{serviceId}")
  public Service updateService(
          @PathVariable("serviceId") Integer id,
          @RequestBody Service serviceUpdates) {
    Service service = serviceRepository.findServiceById(id);
    service.setServiceName(serviceUpdates.getServiceName());
    return serviceRepository.save(service);
  }

  @DeleteMapping("/api/services/{serviceId}")
  public void deleteService(
          @PathVariable("serviceId") Integer id) {
    serviceRepository.deleteById(id);
  }
=======
	@Autowired
	ServiceRepository serviceRepository;
	// Returns all services in list order
	@GetMapping("/api/services")
	public List<Service> findAllService() {
		return serviceRepository.findAllServices();
	}

	// Returns all services in alphabetical order
	@GetMapping("/api/services/alphabetically")
	public List<Service> findAllServicesAlphabetically() {
		return serviceRepository.findAllServicesAlphabetically();
	}

	@GetMapping("/api/services/{serviceId}")
	public Service findServiceById(
			@PathVariable("serviceId") Integer id) {
		return serviceRepository.findServiceById(id);
	}

	@PostMapping("/api/services")
	public Service createService(@RequestBody Service service) {
		return serviceRepository.save(service);
	}

	@PutMapping("/api/services/{serviceId}")
	public Service updateService(
			@PathVariable("serviceId") Integer id,
			@RequestBody Service serviceUpdates) {
		Service service = serviceRepository.findServiceById(id);
		service.setServiceName(serviceUpdates.getServiceName());
		return serviceRepository.save(service);
	}

	@DeleteMapping("/api/services/{serviceId}")
	public void deleteService(
			@PathVariable("serviceId") Integer id) {
		serviceRepository.deleteById(id);
	}

	@GetMapping("api/services/category/{categoryId}/limit/{size}")
	public List<Service> findPopularServicesByCategory(
					@PathVariable("categoryId") Integer categoryId,
					@PathVariable("size") Integer size) {
		return serviceRepository.findPopularServicesByCategory(categoryId, size);
	}

	@GetMapping("api/services/filtered")
	public List<Service> filterServices(@RequestParam(name="serviceName", required =false) String serviceName) {
		if (serviceName == null) serviceName = "";
		serviceName = "%" + serviceName + "%";
		return serviceRepository.filterServices(serviceName);
	}
}
