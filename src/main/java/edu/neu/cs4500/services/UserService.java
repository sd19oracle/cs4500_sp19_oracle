package edu.neu.cs4500.services;

import edu.neu.cs4500.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.neu.cs4500.repositories.UserRepository;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Michael Goodnow on 2019-01-23.
 */

@RestController
@CrossOrigin(origins="*")
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
  public List<User> findProvidersByZipCode(@PathVariable("providerZip") Integer providerZip) {
    List<User> allProviders = userRepository.findAllProviders();

    List<User> result = new ArrayList<>();
    // sorting the address
    Map<Integer, Integer> map_distance = new HashMap<>();
    Map<Integer, User> map_users = new HashMap<>();
    for (User user: allProviders) {
      Integer zip_code = user.getZipCode();
      if (zip_code != null) {
        Integer distance = Math.abs(zip_code - providerZip);
        map_distance.put(zip_code, distance);
      }
      map_users.put(zip_code, user);
    }

    // Create a list from elements of HashMap
    List<Map.Entry<Integer, Integer> > list =
            new LinkedList<>(map_distance.entrySet());

    // Sort the list
    Collections.sort(list, new Comparator<Map.Entry<Integer, Integer> >() {
      public int compare(Map.Entry<Integer, Integer> o1,
                         Map.Entry<Integer, Integer> o2)
      {
        return (o1.getValue()).compareTo(o2.getValue());
      }
    });

    // put data from sorted list to a new list in reversed order
    // cause we want to higher grade providers
    List<Integer> sorted_zipcode = new ArrayList<>();
    for (Map.Entry<Integer, Integer> aa : list) {
      sorted_zipcode.add(0, aa.getKey());
    }
    for (Integer zip: sorted_zipcode) {
      result.add(map_users.get(zip));
    }
    return result;
  }

  @GetMapping("/api/users/providers/{providerName}/{providerZip}")
  public List<User> findProvidersByNameAndZipCode(
          @PathVariable("providerName") String providerName,
          @PathVariable("providerZip") Integer providerZip
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
      Map<Integer, Integer> map_distance = new HashMap<>();
      Map<Integer, User> map_users = new HashMap<>();
      for (User user: temp) {
        Integer zip_code = user.getZipCode();
        if (zip_code != null) {
          Integer distance = Math.abs(zip_code - providerZip);
          map_distance.put(zip_code, distance);
        }
        map_users.put(zip_code, user);
      }
      // Create a list from elements of HashMap
      List<Map.Entry<Integer, Integer> > list =
              new LinkedList<>(map_distance.entrySet());

      // Sort the list
      Collections.sort(list, new Comparator<Map.Entry<Integer, Integer> >() {
        public int compare(Map.Entry<Integer, Integer> o1,
                           Map.Entry<Integer, Integer> o2)
        {
          return (o1.getValue()).compareTo(o2.getValue());
        }
      });

      // put data from sorted list to a new list in reversed order
      // cause we want to higher grade providers
      List<Integer> sorted_zipcode = new ArrayList<>();
      for (Map.Entry<Integer, Integer> aa : list) {
        sorted_zipcode.add(0, aa.getKey());
      }
      for (Integer zip: sorted_zipcode) {
        result.add(map_users.get(zip));
      }
    } else {
      return temp;
    }

    return result;
  }
}
