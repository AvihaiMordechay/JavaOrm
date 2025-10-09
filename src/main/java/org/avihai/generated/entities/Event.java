package org.avihai.generated.entities;

import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.sql.Timestamp;

public class Event {
  private int id;

  private String name;

  private String eventLocation;

  private Timestamp eventDate;

  private Integer userId;

  public Event() {
  }

  public Event(int id, String name, String eventLocation, Timestamp eventDate, Integer userId) {
    this.id = id;
    this.name = name;
    this.eventLocation = eventLocation;
    this.eventDate = eventDate;
    this.userId = userId;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEventLocation() {
    return this.eventLocation;
  }

  public void setEventLocation(String eventLocation) {
    this.eventLocation = eventLocation;
  }

  public Timestamp getEventDate() {
    return this.eventDate;
  }

  public void setEventDate(Timestamp eventDate) {
    this.eventDate = eventDate;
  }

  public Integer getUserId() {
    return this.userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "Event(" +
                        "id='" + id + '\'' + ", " +
                        "name='" + name + '\'' + ", " +
                        "eventLocation='" + eventLocation + '\'' + ", " +
                        "eventDate='" + eventDate + '\'' + ", " +
                        "userId='" + userId + '\'' +
                        ')';
  }
}
