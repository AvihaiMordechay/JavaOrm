package org.avihai.generated.entities;

import java.lang.Integer;
import java.lang.Override;
import java.lang.String;

public class Table {
  private int id;

  private int tableNumber;

  private int seatsCount;

  private String notes;

  private Integer eventId;

  public Table() {
  }

  public Table(int id, int tableNumber, int seatsCount, String notes, Integer eventId) {
    this.id = id;
    this.tableNumber = tableNumber;
    this.seatsCount = seatsCount;
    this.notes = notes;
    this.eventId = eventId;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getTableNumber() {
    return this.tableNumber;
  }

  public void setTableNumber(int tableNumber) {
    this.tableNumber = tableNumber;
  }

  public int getSeatsCount() {
    return this.seatsCount;
  }

  public void setSeatsCount(int seatsCount) {
    this.seatsCount = seatsCount;
  }

  public String getNotes() {
    return this.notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Integer getEventId() {
    return this.eventId;
  }

  public void setEventId(Integer eventId) {
    this.eventId = eventId;
  }

  @Override
  public String toString() {
    return "Table(" +
                        "id='" + id + '\'' + ", " +
                        "tableNumber='" + tableNumber + '\'' + ", " +
                        "seatsCount='" + seatsCount + '\'' + ", " +
                        "notes='" + notes + '\'' + ", " +
                        "eventId='" + eventId + '\'' +
                        ')';
  }
}
