package edu.neu.cs4500.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs4500.models.ServiceCategory;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Integer> {
	@Query(value="SELECT serviceCategory FROM ServiceCategory serviceCategory ORDER BY popularity DESC")
	public List<ServiceCategory> findAllServiceCategories();

	@Query(value="SELECT serviceCategory FROM ServiceCategory serviceCategory WHERE id=:servicecategoryid")
	public ServiceCategory findServiceCategoryById(@Param("servicecategoryid") Integer id);

	@Query(value="SELECT serviceCategory FROM ServiceCategory serviceCategory ORDER BY serviceCategory")
	public List<ServiceCategory> findAllServiceCategoriesAlphabetically();

	@Query(value=
			"SELECT serviceCategory " +
			"FROM ServiceCategory serviceCategory " +
			"WHERE serviceCategoryName LIKE :serviceCategoryName " +
			"ORDER BY serviceCategory.serviceCategoryName")
	public List<ServiceCategory> filterServiceCategories(@Param("serviceCategoryName") String serviceCategoryName);
}
