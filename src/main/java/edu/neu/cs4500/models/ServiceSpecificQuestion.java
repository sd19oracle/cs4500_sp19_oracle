package edu.neu.cs4500.models;


import java.util.List;

public class ServiceSpecificQuestion {
    private Integer id;
    private String title;
    private String type;
    private String chioce;
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
}
