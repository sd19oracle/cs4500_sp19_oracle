package edu.neu.cs4500.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs4500.models.ServiceCategory;

public interface ServiceCategoryRepository extends CrudRepository<ServiceCategory, Integer> {
	@Query(value="SELECT serviceCategory FROM ServiceCategory serviceCategory ORDER BY popularity DESC")
	public List<ServiceCategory> findAllServiceCategories();
	@Query(value="SELECT serviceCategory FROM ServiceCategory serviceCategory WHERE id=:servicecategoryid")
	public ServiceCategory findServiceCategoryById(@Param("servicecategoryid") Integer id);
}