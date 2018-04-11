package com.epam.labproject.entity;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User extends AbstractIdentifiableEntity{
    @Column(unique = true)
    private String login;
    private String password;
    @ManyToOne
    @Column(name="role_id")
    private Role roleId;

    public User(){

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
    public Role getRoleId() {
        return roleId;
    }
    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }
}
