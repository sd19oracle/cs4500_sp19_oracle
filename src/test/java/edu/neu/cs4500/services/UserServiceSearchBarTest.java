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

import edu.neu.cs4500.models.User;
import edu.neu.cs4500.repositories.ServiceRepository;
import edu.neu.cs4500.repositories.UserRepository;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;package edu.neu.cs4500.services;


@RunWith(SpringRunner.class)
@WebMvcTest(ServiceService.class)
public class UserServiceSearchBarTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserRepository userRepository;

  User u1 = new User();
  User u2 = new User();
  User u3 = new User();
  User u4 = new User();
  User u5 = new User();

  @Before
  public void setUpInitialTestCases() {
    u1.setRole("provider");
    u1.setFirstName("u1");
    u1.setLastName("U1");
    u1.setZipCode("02115");
    u2.setRole("provider");
    u2.setFirstName("u2");
    u2.setLastName("U2");
    u2.setZipCode("02110");
    u3.setFirstName("u3");
    u3.setLastName("U3");
    u3.setRole("provider");
    u3.setZipCode("30010");
    u4.setRole("admin");
    u4.setFirstName("I am an admin");
    u5.setRole("User");
    u5.setFirstName("Normal user");
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

