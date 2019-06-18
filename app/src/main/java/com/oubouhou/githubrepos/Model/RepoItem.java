package com.oubouhou.githubrepos.Model;

public class RepoItem {
    private String repo_owner;
    private String repo_name;
    private String repo_desc;
    private String repo_avatarUrl;
    private String repo_rating;

    public RepoItem(String repo_owner, String repo_name, String repo_desc, String repo_avatarUrl, String repo_rating) {
        this.repo_owner = repo_owner;
        this.repo_name = repo_name;
        this.repo_desc = repo_desc;
        this.repo_avatarUrl = repo_avatarUrl;
        this.repo_rating = repo_rating;
    }

    public String getRepo_owner() {
        return repo_owner;
    }

    public void setRepo_owner(String repo_owner) {
        this.repo_owner = repo_owner;
    }

    public String getRepo_name() {
        return repo_name;
    }

    public void setRepo_name(String repo_name) {
        this.repo_name = repo_name;
    }

    public String getRepo_desc() {
        return repo_desc;
    }

    public void setRepo_desc(String repo_desc) {
        this.repo_desc = repo_desc;
    }

    public String getRepo_avatarUrl() {
        return repo_avatarUrl;
    }

    public void setRepo_avatarUrl(String repo_avatarUrl) {
        this.repo_avatarUrl = repo_avatarUrl;
    }

    public String getRepo_rating() {
        return repo_rating;
    }

    public void setRepo_rating(String repo_rating) {
        this.repo_rating = repo_rating;
    }
}
