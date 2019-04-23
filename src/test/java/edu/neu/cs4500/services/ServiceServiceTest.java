package edu.neu.cs4500.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.models.ServiceCategory;
import edu.neu.cs4500.repositories.ServiceRepository;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ServiceService.class)
public class ServiceServiceTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ServiceRepository serviceService;

  ServiceCategory sc = new ServiceCategory();
  Service s1 = new Service();
  Service s2 = new Service();

  @Before
  public void setUpInitialTestCases() {
    sc.setId(1);
    sc.setServiceCategoryName("Tutoring");
    s1.setServiceName("Computer Science");
    s2.setServiceName("Math");
    s1.setId(1);
    s2.setId(2);
    s1.setPopularity(10);
    s2.setPopularity(9);
    sc.setServices(Arrays.asList(s1, s2));
    s1.setServiceCategories(Arrays.asList(sc));
  }

  @Test
  public void testFindPopularServicesByCategoryLimit1() throws Exception {
    List<Service> popularServices = Arrays.asList(s1);
    when(serviceService
            .findPopularServicesByCategory(1, 1))
            .thenReturn(popularServices);
    this.mockMvc.perform(get("/api/services/category/1/limit/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].id",
                    containsInAnyOrder(1)))
            .andExpect(jsonPath("$.[*].serviceName",
                    containsInAnyOrder("Computer Science")))
            .andExpect(jsonPath("$",
                    hasSize(1)));
  }

  @Test
  public void testFindPopularServicesByCategoryLimit2() throws Exception {
    List<Service> popularServices = Arrays.asList(s1, s2);
    when(serviceService
            .findPopularServicesByCategory(1, 2))
            .thenReturn(popularServices);
    this.mockMvc.perform(get("/api/services/category/1/limit/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].id",
                    containsInAnyOrder(1, 2)))
            .andExpect(jsonPath("$.[*].serviceName",
                    containsInAnyOrder("Computer Science", "Math")))
            .andExpect(jsonPath("$",
                    hasSize(2)));
  }

  @Test
  public void testFindPopularServicesByCategoryLimit6() throws Exception {
    List<Service> popularServices = Arrays.asList(s1, s2);
    when(serviceService
            .findPopularServicesByCategory(1, 6))
            .thenReturn(popularServices);
    this.mockMvc.perform(get("/api/services/category/1/limit/6"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[*].id",
                    containsInAnyOrder(1, 2)))
            .andExpect(jsonPath("$.[*].serviceName",
                    containsInAnyOrder("Computer Science", "Math")))
            .andExpect(jsonPath("$",
                    hasSize(2)));
  }
}
