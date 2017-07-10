package com.example.mahfuz.todolist.Models;

import java.util.Date;

/**
 * Created by mahfuz on 7/10/17.
 */

public class Task {

    private String name;
    private Integer id;
    private String description;
    private Date  createdAt;
    private Date updatedAt;

    public Task(String name, Integer id, String description, Date createdAt, Date updatedAt) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }




}
