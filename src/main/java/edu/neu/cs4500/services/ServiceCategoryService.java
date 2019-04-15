package edu.neu.cs4500.services;

import java.util.List;

import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.repositories.PagedServiceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import edu.neu.cs4500.models.ServiceCategory;
import edu.neu.cs4500.repositories.ServiceCategoryRepository;

@RestController
public class ServiceCategoryService {
    @Autowired
    ServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    PagedServiceCategoryRepository pagedServiceCategoryRepository;

    // Returns all service categories by popularity, descending
    @GetMapping("/api/categories")
    public List<ServiceCategory> findAllServiceCategories() {
        return serviceCategoryRepository.findAllServiceCategories();
    }

    // Returns all service categories alphabetically
    @GetMapping("/api/categories/alphabetically")
    public List<ServiceCategory> findAllServiceCategoriesAlphabetically() {
	    return serviceCategoryRepository.findAllServiceCategoriesAlphabetically();
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

    @GetMapping("/api/categories/paged")
    public Page<ServiceCategory> findAllServiceCategoriesPaged(
            @RequestParam(name="pageNum", required = false) Integer pageNum,
            @RequestParam(name="ipp", required = false) Integer itemsPerPage) {
     if (pageNum == null) {
         pageNum = 0;
     }
     if (itemsPerPage == null) {
         itemsPerPage = 10;
     }
     Pageable p = PageRequest.of(pageNum, itemsPerPage);
     return pagedServiceCategoryRepository.findAll(p);
    }

    @GetMapping("/api/categories/filtered")
    public List<ServiceCategory> filterServiceCategories(
	    @RequestParam(name="serviceCategoryName", required=false) String serviceCategoryName) {
        if (serviceCategoryName == null) serviceCategoryName = "";
	serviceCategoryName = "%" + serviceCategoryName + "%";
	return serviceCategoryRepository.filterServiceCategories(serviceCategoryName);	
    }
}
