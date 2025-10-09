package org.avihai.generated.entities;

import java.lang.Override;
import java.lang.String;

public class User {
  private int id;

  private String email;

  private String password;

  private String name;

  private String role;

  public User() {
  }

  public User(int id, String email, String password, String name, String role) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.name = name;
    this.role = role;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRole() {
    return this.role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "User(" +
                        "id='" + id + '\'' + ", " +
                        "email='" + email + '\'' + ", " +
                        "password='" + password + '\'' + ", " +
                        "name='" + name + '\'' + ", " +
                        "role='" + role + '\'' +
                        ')';
  }
}
