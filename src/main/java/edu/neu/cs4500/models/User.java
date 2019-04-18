package edu.neu.cs4500.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Created by Michael Goodnow on 2019-01-23.
 */

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private String role;
  private String zipCode;
  @OneToMany(mappedBy = "user")
  private List<ServiceSpecificAnswer> answers;

  @ManyToMany(mappedBy = "providers")
  private List<Service> services;
  public User() {
  }

  public User(Integer id, String username, String password, String firstName, String lastName,
              String zipCode) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.zipCode = zipCode;
  }

  public String getZipCode() {
    return this.zipCode;
  }
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public List<ServiceSpecificAnswer> getAnswers() {
    return answers;
  }

  public void setAnswers(List<ServiceSpecificAnswer> answers) {
    this.answers = answers;
  }

  public void addAnswer(ServiceSpecificAnswer answer) {
    if (!this.answers.contains(answer)) {
      this.answers.add(answer);
    }
  }
  
  public List<Service> getServices() {
    return services;
  }

  public void setServices(List<Service> services) {
    this.services = services;
  }

  @Override
  public boolean equals(Object user) {
    if (user == this) {
      return true;
    }
    if (!(user instanceof User)) {
      return false;
    }
    User tempUser = (User)user;
    return tempUser.getId().equals(this.id);
  }

  @Override
  public int hashCode() {
    return this.id;
  }
}
