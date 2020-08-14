package com.canace.jndi;

public class userMedul {
    int id;
    String name;
    String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "userMedul{" +
                "id=" + id +
                ", username='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
