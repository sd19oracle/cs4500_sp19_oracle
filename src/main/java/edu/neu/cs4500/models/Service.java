/*
 * Created by Michael Goodnow on 2019-01-30.
 */

package edu.neu.cs4500.models;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="services")
public class Service {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String serviceName;
	@OneToMany(mappedBy="service")
	private List<ServiceSpecificQuestion> questions;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public List<ServiceSpecificQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<ServiceSpecificQuestion> questions) {
		this.questions = questions;
	}

  public void addQuestion(ServiceSpecificQuestion oneQuestion) {
		if (!questions.contains(oneQuestion)) {
		  questions.add(oneQuestion);
    }
  }
}
