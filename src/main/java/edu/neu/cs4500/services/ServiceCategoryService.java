package edu.neu.cs4500.services;

import java.util.List;

import edu.neu.cs4500.models.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.neu.cs4500.models.ServiceCategory;
import edu.neu.cs4500.repositories.ServiceCategoryRepository;

@RestController
public class ServiceCategoryService {
    @Autowired
    ServiceCategoryRepository serviceCategoryRepository;

    @GetMapping("/api/categories")
    public List<ServiceCategory> findAllServiceCategories() {
        return serviceCategoryRepository.findAllServiceCategories();
    }

    @GetMapping("/api/categories/{serviceCategoryId}")
    public ServiceCategory findServiceCategoryById(
            @PathVariable("serviceCategoryId") Integer id) {
        return serviceCategoryRepository.findServiceCategoryById(id);
    }

    @PostMapping("/api/categories")
    public ServiceCategory createServiceCategory(@RequestBody ServiceCategory serviceCategory) {
        return serviceCategoryRepository.save(serviceCategory);
    }

    @PutMapping("/api/categories/{serviceCategoryId}")
    public ServiceCategory updateServiceCategory(
            @PathVariable("serviceCategoryId") Integer id,
            @RequestBody ServiceCategory serviceUpdates) {
        ServiceCategory serviceCategory = serviceCategoryRepository.findServiceCategoryById(id);
        serviceCategory.setServiceCategoryName(serviceUpdates.getServiceCategoryName());
        return serviceCategoryRepository.save(serviceCategory);
    }

    @DeleteMapping("/api/categories/{serviceCategoryId}")
    public void deleteServiceCategory(
            @PathVariable("serviceCategoryId") Integer id) {
        serviceCategoryRepository.deleteById(id);
    }

    @GetMapping("/api/categories/{serviceCategoryId}/list")
    public List<Service> findAllServiceByCategoryId(@PathVariable("serviceCategoryId") Integer serviceCategoryId) {
        return serviceCategoryRepository.findServiceCategoryById(serviceCategoryId).getServices();
    }
}