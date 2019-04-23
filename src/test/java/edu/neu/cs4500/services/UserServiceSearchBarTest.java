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
import edu.neu.cs4500.repositories.UserRepository;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(UserService.class)
public class UserServiceSearchBarTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserRepository userService;

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
    u1.setEmail("u1@gmail.com");
    u2.setRole("provider");
    u2.setFirstName("u2");
    u2.setLastName("U2");
    u2.setZipCode("02110");
    u2.setEmail("u2@gmail.com");
    u3.setFirstName("u3");
    u3.setLastName("U3");
    u3.setRole("provider");
    u3.setZipCode("30010");
    u3.setEmail("u3@gmail.com");
    u4.setRole("admin");
    u4.setFirstName("I am an admin");
    u4.setEmail("u4@gmail.com");
    u5.setRole("User");
    u5.setFirstName("Normal user");
    u5.setEmail("u5@gmail.com");
  }

  //  When only given the name, search providers by name that match or are similar to the given
  // name.
  @Test
  public void findProvidersByName() throws Exception {
    List<User> providers = Arrays.asList(u1);
    when(userService
            .findAllProvidersNameMatch("u1")).thenReturn(providers);
    this.mockMvc.perform(get("/api/users/providers/name/u1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].zipCode",
                    is("02115")))
            .andExpect(jsonPath("$[0].firstName",
                    is("u1")))
            .andExpect(jsonPath("$",
                    hasSize(1)));
    verify(userService, times(1)).findAllProvidersNameMatch("u1");
    verifyNoMoreInteractions(userService);
  }

  @Test
  public void findProvidersByName2() throws Exception {
    List<User> providers = Arrays.asList(u1, u2, u3);
    when(userService
            .findAllProvidersNameMatch("u")).thenReturn(providers);
    this.mockMvc.perform(get("/api/users/providers/name/u"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[*].zipCode",
                    containsInAnyOrder("02115", "02110", "30010")))
            .andExpect(jsonPath("$[*].firstName",
                    containsInAnyOrder("u1", "u2", "u3")))
            .andExpect(jsonPath("$",
                    hasSize(3)));
    verify(userService, times(1)).findAllProvidersNameMatch("u");
    verifyNoMoreInteractions(userService);
  }

  // When given the name and the zip code, return a list of providers that match or are similar to
  // the given number and sorted by the closest distance between the given zip code.
  @Test
  public void findProvidersByNameAndZip() throws Exception {
    List<User> providers = Arrays.asList(u1);
    when(userService
            .findAllProvidersNameMatch("u1")).thenReturn(providers);
    this.mockMvc.perform(get("/api/users/providers/u1/02115"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].zipCode",
                    is("02115")))
            .andExpect(jsonPath("$[0].firstName",
                    is("u1")))
            .andExpect(jsonPath("$",
                    hasSize(1)));
    verify(userService, times(1)).findAllProvidersNameMatch("u1");
    verifyNoMoreInteractions(userService);
  }

  @Test
  public void findProvidersByNameAndZip2() throws Exception {
    List<User> providers = Arrays.asList(u1, u2, u3);
    when(userService
            .findAllProvidersNameMatch("u")).thenReturn(providers);
    this.mockMvc.perform(get("/api/users/providers/u/02115"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[*].zipCode",
                    containsInAnyOrder("02115", "02110", "30010")))
            .andExpect(jsonPath("$[*].firstName",
                    containsInAnyOrder("u1", "u2", "u3")))
            .andExpect(jsonPath("$",
                    hasSize(3)));
    verify(userService, times(1)).findAllProvidersNameMatch("u");
    verifyNoMoreInteractions(userService);
  }


  //  When only given the zip code, return all users sorted by the closest distance between the
  // given zip code.
  @Test
  public void findProvidersByZip() throws Exception {
    List<User> providers = Arrays.asList(u1, u2, u3);
    when(userService
            .findAllProviders()).thenReturn(providers);
    this.mockMvc.perform(get("/api/users/providers/zip/02115"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[*].zipCode",
                    containsInAnyOrder("02115", "02110", "30010")))
            .andExpect(jsonPath("$[*].firstName",
                    containsInAnyOrder("u1", "u2", "u3")))
            .andExpect(jsonPath("$",
                    hasSize(3)));
    verify(userService, times(1)).findAllProviders();
    verifyNoMoreInteractions(userService);
  }

  @Test
  public void findProvidersByZip2() throws Exception {
    List<User> providers = Arrays.asList(u2, u1, u3);
    when(userService
            .findAllProviders()).thenReturn(providers);
    this.mockMvc.perform(get("/api/users/providers/zip/02110"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[*].zipCode",
                    containsInAnyOrder( "02110", "02115", "30010")))
            .andExpect(jsonPath("$[0].firstName",
                    is("u2")))
            .andExpect(jsonPath("$",
                    hasSize(3)));
    verify(userService, times(1)).findAllProviders();
    verifyNoMoreInteractions(userService);
  }



}

