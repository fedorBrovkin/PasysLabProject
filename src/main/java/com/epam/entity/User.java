package com.epam.entity;

import javax.persistence.*;

@Entity
//@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //unique
    private String login;
    private String password;
    @ManyToOne
    private Role role_id;

    public User(){

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole_id() {
        return role_id;
    }
    public void setRole_id(Role role_id) {
        this.role_id = role_id;
    }
}
