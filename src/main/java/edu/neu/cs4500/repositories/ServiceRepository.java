package edu.neu.cs4500.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import edu.neu.cs4500.models.Service;

public interface ServiceRepository extends CrudRepository<Service, Integer> {
  @Query(value = "SELECT service FROM Service service")
  public List<Service> findAllServices();

  @Query(value = "SELECT service FROM Service service WHERE id=:serviceid")
  public Service findServiceById(@Param("serviceid") Integer id);

  @Query(value = "select * "
          + "from services "
          + "join categories_services cs on (services.id = cs.service_id) "
          + "where category_id = :category_id "
          + "order by popularity desc limit :size ;", nativeQuery = true)
  public List<Service> findPopularServicesByCategory(@Param("category_id")
                                                             Integer categoryId,
                                                     @Param("size")
                                                             Integer size);
}