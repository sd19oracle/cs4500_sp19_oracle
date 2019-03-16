package edu.neu.cs4500.models;

import java.util.List;

public class PageInfo {
  private int page_num;
  private List<ServiceSpecificQuestion> list_questions;
  private int total_questions;

  public PageInfo() {}

  public PageInfo(int page_num, List<ServiceSpecificQuestion> list_questions, int total_questions) {
    this.page_num = page_num;
    this.list_questions = list_questions;
    this.total_questions = total_questions;
  }

  public void setPage_num(int page_num) {
    this.page_num = page_num;
  }

  public void setList_questions(List<ServiceSpecificQuestion> list_questions) {
    this.list_questions = list_questions;
  }

  public void setTotal_questions(int total_questions) {
    this.total_questions = total_questions;
  }

  public int getPage_num(){
    return page_num;
  }

  public List<ServiceSpecificQuestion> getList_questions() {
    return list_questions;
  }

  public int getTotal_questions() {
    return this.total_questions;
  }
}
