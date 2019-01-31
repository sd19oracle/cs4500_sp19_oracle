package edu.neu.cs4500.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs4500.models.Service;

public interface ServiceRepository extends CrudRepository<Service, Integer> {
	@Query(value="SELECT service FROM Service service")
	public List<Service> findAllServices();
	@Query(value="SELECT service FROM Service service WHERE service.id=:id")
	public Service findServiceById(@Param("id") Integer id);
}