package edu.neu.cs4500.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="service_specific_question")
public class ServiceSpecificQuestion {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String type;
    private String chioce;
    @ManyToOne
    @JsonIgnore
    private Service service;
    @OneToMany(mappedBy="question")
    @JsonIgnore
    private List<ServiceSpecificAnswer> answers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChioce() {
        return chioce;
    }

    public void setChioce(String chioce) {
        this.chioce = chioce;
    }

    public List<ServiceSpecificAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<ServiceSpecificAnswer> answers) {
        this.answers = answers;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
