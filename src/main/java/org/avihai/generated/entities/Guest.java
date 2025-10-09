package org.avihai.generated.entities;

import java.lang.Integer;
import java.lang.Override;
import java.lang.String;

public class Guest {
  private int id;

  private String firstName;

  private String lastName;

  private String phone;

  private String status;

  private Integer eventId;

  private Integer tableId;

  public Guest() {
  }

  public Guest(int id, String firstName, String lastName, String phone, String status,
      Integer eventId, Integer tableId) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.status = status;
    this.eventId = eventId;
    this.tableId = tableId;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Integer getEventId() {
    return this.eventId;
  }

  public void setEventId(Integer eventId) {
    this.eventId = eventId;
  }

  public Integer getTableId() {
    return this.tableId;
  }

  public void setTableId(Integer tableId) {
    this.tableId = tableId;
  }

  @Override
  public String toString() {
    return "Guest(" +
                        "id='" + id + '\'' + ", " +
                        "firstName='" + firstName + '\'' + ", " +
                        "lastName='" + lastName + '\'' + ", " +
                        "phone='" + phone + '\'' + ", " +
                        "status='" + status + '\'' + ", " +
                        "eventId='" + eventId + '\'' + ", " +
                        "tableId='" + tableId + '\'' +
                        ')';
  }
}
