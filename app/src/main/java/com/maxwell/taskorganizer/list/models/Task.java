package com.maxwell.taskorganizer.list.models;

import java.util.Date;

public class Task {
    private String title;
    private String description;
    private Date creationDate;
    private Date expirationDate;

    public Task(String title, String description, Date creationDate, Date expirationDate) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }
}
