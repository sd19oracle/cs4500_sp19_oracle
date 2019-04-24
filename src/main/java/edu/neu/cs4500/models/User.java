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
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private String role;
  private String zipCode;
  private String dob;
  private String street;
  private String city;
  private String state;
  @OneToMany(mappedBy = "user")
  private List<ServiceSpecificAnswer> answers;

  @ManyToMany(mappedBy = "providers")
  private List<Service> services;

  public User() {
  }

  public User(Integer id, String email, String password, String firstName, String lastName,
              String zipCode) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.zipCode = zipCode;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getDob() {
    return dob;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  @Override
  public boolean equals(Object user) {
    if (user == this) {
      return true;
    }
    if (!(user instanceof User)) {
      return false;
    }
    User tempUser = (User) user;
    return tempUser.getId().equals(this.id);
  }

  @Override
  public int hashCode() {
    return this.id;
  }
}
