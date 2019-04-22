/*
 * Created by Michael Goodnow on 2019-01-30.
 */

package edu.neu.cs4500.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


@Entity
@Table(name = "services")
public class Service {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String serviceName;
	private String thumbnail;
	@OneToMany(mappedBy="service")
	private List<ServiceSpecificQuestion> questions;
	@ManyToMany
	@JsonIgnore
	@JoinTable(
		name="PROVIDERS_SERVICES",
		joinColumns=@JoinColumn(name="SERVICE_ID", referencedColumnName="ID"),
		inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	)
	private List<User> providers;

	@ManyToMany(mappedBy = "services")
	private List<ServiceCategory> serviceCategories;

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
	
	public String getThumbnail() {
		return this.thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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
	
	public List<User> getProviders() {
		return providers;
	}

	public void setProviders(List<User> providers) {
		this.providers = providers;
	}

	public List<ServiceCategory> getServiceCategories() {
		return serviceCategories;
	}

	public void setServiceCategories(List<ServiceCategory> serviceCategories) {
		this.serviceCategories = serviceCategories;
	}
}
