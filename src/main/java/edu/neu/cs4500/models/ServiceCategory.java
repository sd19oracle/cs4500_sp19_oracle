package edu.neu.cs4500.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="service_categories")
public class ServiceCategory {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  public Integer getId() {
    return id;
  }
}
