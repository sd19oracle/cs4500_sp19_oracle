package edu.neu.cs4500.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import edu.neu.cs4500.exceptions.NoUserFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.neu.cs4500.models.User;
import edu.neu.cs4500.repositories.UserRepository;

/**
 * Created by Michael Goodnow on 2019-01-23.
 */

@RestController
@CrossOrigin(origins={"https://cs4500-sp19-client-oracle.herokuapp.com", "http://localhost:3000"}, allowCredentials = "true")
public class UserService {
  @Autowired
  UserRepository userRepository;

  @GetMapping("/api/users")
  public List<User> findAllUser() {
    return (List<User>) userRepository.findAll();
  }

  @GetMapping("/api/users/{userId}")
  public User findUserById(
          @PathVariable("userId") Integer userId) {
    return userRepository.findById(userId).get();
  }

  @GetMapping("/api/users/providers/name/{providerName}")
  public List<User> findProvidersByName(@PathVariable("providerName") String providerName) {
    return userRepository.findAllProvidersNameMatch(providerName);
  }

  @GetMapping("/api/users/providers/zip/{providerZip}")
  public List<User> findProvidersByZipCode(@PathVariable("providerZip") String providerZip) {
    List<User> allProviders = userRepository.findAllProviders();

    List<User> result = new ArrayList<>();
    // sorting the address
    Map<String, Integer> map_distance = new HashMap<>();
    Map<String, User> map_users = new HashMap<>();
    for (User user: allProviders) {
      String get_zip_code = user.getZipCode();
      if (get_zip_code != null) {
        Integer zip_code = Integer.parseInt(user.getZipCode());
        Integer distance = Math.abs(zip_code - Integer.parseInt(providerZip));
        map_distance.put(get_zip_code, distance);
      }
      map_users.put(get_zip_code, user);
    }

    // Create a list from elements of HashMap
    List<Map.Entry<String, Integer> > list =
            new LinkedList<>(map_distance.entrySet());

    // Sort the list
    Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
      public int compare(Map.Entry<String, Integer> o1,
                         Map.Entry<String, Integer> o2)
      {
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
      for (User user: temp) {
        String zip_code = user.getZipCode();
        if (zip_code != null) {
          Integer distance = Math.abs(Integer.parseInt(zip_code) - Integer.parseInt(providerZip));
          map_distance.put(zip_code, distance);
        }
        map_users.put(zip_code, user);
      }
      // Create a list from elements of HashMap
      List<Map.Entry<String, Integer> > list =
              new LinkedList<>(map_distance.entrySet());

      // Sort the list
      Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
        public int compare(Map.Entry<String, Integer> o1,
                           Map.Entry<String, Integer> o2)
        {
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

  @PostMapping("/api/login")
  public User login(@RequestBody User creds, HttpSession session) {
	  for (User user : userRepository.findAll()) {
		  if (user.getUsername().equals(creds.getUsername())
		      && user.getPassword().equals(creds.getPassword())) {
			  session.setAttribute("currentUser", user);
			  return user;
		  }
	  }
	  throw new NoUserFoundException();
  }

  @PostMapping("/api/logout")
  public void logout(HttpSession session) {
	  session.invalidate();
  }

  @GetMapping("/api/currentUser")
  public User getCurrentUser(HttpSession session) {
	  return (User) session.getAttribute("currentUser");
  }
}










