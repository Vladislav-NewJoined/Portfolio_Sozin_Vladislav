package org.example.zadaniye8.models;

public class PostModel { // Этот продублирован в папке/package models
    private int userId;
    private int id;
    private String title;
    private String body;
    private int page;
    private int pageSize;
    private int radiusKm;
    private float latitude;
    private float longitude;

    public PostModel(int userId, String title, String body, int page, int pageSize, int radiusKm, float latitude, float longitude) {
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.page = page;
        this.pageSize = pageSize;
        this.radiusKm = radiusKm;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "PostModel{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", page='" + page + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", radiusKm='" + radiusKm + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
