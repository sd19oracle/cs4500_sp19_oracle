package edu.neu.cs4500.services;

import edu.neu.cs4500.exceptions.DuplicateEmailException;
import edu.neu.cs4500.exceptions.NoUserFoundException;
import edu.neu.cs4500.models.User;
import edu.neu.cs4500.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Michael Goodnow on 2019-01-23.
 */

@RestController
@CrossOrigin(origins = {"https://cs4500-sp19-client-oracle.herokuapp.com", "http://localhost:3000", "*"}, allowCredentials = "true")
public class UserService {
  @Autowired
  UserRepository userRepository;

  // Find all Users
  @GetMapping("/api/users")
  public List<User> findAllUser() {
    return (List<User>) userRepository.findAll();
  }

  // Find User by ID
  @GetMapping("/api/users/{userId}")
  public User findUserById(
      @PathVariable("userId") Integer userId) {
    return userRepository.findById(userId).get();
  }

  // Find a Provider by name
  @GetMapping("/api/users/providers/name/{providerName}")
  public List<User> findProvidersByName(@PathVariable("providerName") String providerName) {
    return userRepository.findAllProvidersNameMatch(providerName);
  }

  // Find a provider by zipcode
  @GetMapping("/api/users/providers/zip/{providerZip}")
  public List<User> findProvidersByZipCode(@PathVariable("providerZip") String providerZip) {
    List<User> allProviders = userRepository.findAllProviders();

    List<User> result = new ArrayList<>();
    // sorting the address
    Map<String, Integer> map_distance = new HashMap<>();
    Map<String, User> map_users = new HashMap<>();
    for (User user : allProviders) {
      String get_zip_code = user.getZipCode();
      if (get_zip_code != null) {
        Integer zip_code = Integer.parseInt(user.getZipCode());
        Integer distance = Math.abs(zip_code - Integer.parseInt(providerZip));
        map_distance.put(get_zip_code, distance);
      }
      map_users.put(get_zip_code, user);
    }

    // Create a list from elements of HashMap
    List<Map.Entry<String, Integer>> list =
        new LinkedList<>(map_distance.entrySet());

    // Sort the list
    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
      public int compare(Map.Entry<String, Integer> o1,
                         Map.Entry<String, Integer> o2) {
        return (o1.getValue()).compareTo(o2.getValue());
      }
    });

    // put data from sorted list to a new list in reversed order
    // cause we want to higher grade providers
    List<String> sorted_zipCode = new ArrayList<>();
    for (Map.Entry<String, Integer> aa : list) {
      result.add(map_users.get(aa.getKey()));
    }
    return result;
  }

  // Find Porvider by name and zipcode
  @GetMapping("/api/users/providers/{providerName}/{providerZip}")
  public List<User> findProvidersByNameAndZipCode(
      @PathVariable("providerName") String providerName,
      @PathVariable("providerZip") String providerZip
  ) {
    List<User> temp;
    if (providerName == null) {
      temp = userRepository.findAllProviders();
    } else {
      temp = userRepository.findAllProvidersNameMatch(providerName);
    }

    List<User> result = new ArrayList<>();
    // sorting the address
    if (providerZip != null) {
      Map<String, Integer> map_distance = new HashMap<>();
      Map<String, User> map_users = new HashMap<>();
      for (User user : temp) {
        String zip_code = user.getZipCode();
        if (zip_code != null) {
          Integer distance = Math.abs(Integer.parseInt(zip_code) - Integer.parseInt(providerZip));
          map_distance.put(zip_code, distance);
        }
        map_users.put(zip_code, user);
      }
      // Create a list from elements of HashMap
      List<Map.Entry<String, Integer>> list =
          new LinkedList<>(map_distance.entrySet());

      // Sort the list
      Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
        public int compare(Map.Entry<String, Integer> o1,
                           Map.Entry<String, Integer> o2) {
          return (o1.getValue()).compareTo(o2.getValue());
        }
      });

      // put data from sorted list to a new list in reversed order
      // cause we want to higher grade providers
      List<String> sorted_zipcode = new ArrayList<>();
      for (Map.Entry<String, Integer> aa : list) {
        result.add(map_users.get(aa.getKey()));
      }
    } else {
      return temp;
    }

    return result;
  }

  // Update a User
  @PutMapping("api/users/{id}")
  public User updateUser(@PathVariable("id") Integer id, @RequestBody User updatedUser) {
    User findUser = userRepository.findUserById(id);
    if (updatedUser.getId() != null) {
      findUser.setId(updatedUser.getId());
    }
    if (updatedUser.getEmail() != null) {
      findUser.setEmail(updatedUser.getEmail());
    }
    if (updatedUser.getPassword() != null) {
      findUser.setPassword(updatedUser.getPassword());
    }
    if (updatedUser.getFirstName() != null) {
      findUser.setFirstName(updatedUser.getFirstName());
    }
    if (updatedUser.getLastName() != null) {
      findUser.setLastName(updatedUser.getLastName());
    }
    if (updatedUser.getRole() != null) {
      findUser.setRole(updatedUser.getRole());
    }
    if (updatedUser.getAnswers() != null) {
      findUser.setAnswers(updatedUser.getAnswers());
    }
    if (updatedUser.getServices() != null) {
      findUser.setServices(updatedUser.getServices());
    }
    if (updatedUser.getZipCode() != null) {
      findUser.setZipCode(updatedUser.getZipCode());
    }
    return userRepository.save(findUser);
  }

  // Delete a User
  @DeleteMapping("api/users/{id}")
  public void deleteOneAnswer(@PathVariable("id") Integer id) {
    userRepository.deleteById(id);
  }

  // Create a User
  @PostMapping("api/users")
  public User createUser(@RequestBody User newUser) {
    User checkEmail = userRepository.findByEmail(newUser.getEmail());
    if (checkEmail != null) {
      throw new DuplicateEmailException();
    } else {
      return userRepository.save(newUser);
    }
  }

  @PostMapping("/api/login")
  public User login(@RequestBody User creds, HttpSession session) {
    Optional<User> findUser = userRepository.matchCredentials(creds.getEmail(), creds.getPassword());
    User user = findUser.orElseThrow(NoUserFoundException::new);
    session.setAttribute("currentUser", user);
    return user;
  }

  @PostMapping("/api/logout")
  public void logout(HttpSession session) {
    session.invalidate();
  }

  @GetMapping("/api/currentUser")
  public User getCurrentUser(HttpSession session) {
    User currentUser = (User) session.getAttribute("currentUser");
    return currentUser;
  }

}










