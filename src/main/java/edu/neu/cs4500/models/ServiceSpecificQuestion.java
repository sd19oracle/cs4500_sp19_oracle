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
    private String choice;
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

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
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

    public void addServiceSpecificAnswer(ServiceSpecificAnswer answer) {this.answers.add(answer);}

    public void removeServiceSpecificAnswer(Integer id) {
        for(ServiceSpecificAnswer a : answers) {
            if(a.getId().equals(id)) {
                answers.remove(a);
            }
        }
    }
}
