package com.github.serezhka.vkdump.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class VideoDTO implements Serializable {

    private Long id;
    private Long ownerId;
    private String title;
    private String description;
    private Integer duration;
    private String photo130;
    private String photo320;
    private String photo640;
    private String photo800;
    private Long date;
    private Long addingDate;
    private Integer views;
    private Integer comments;
    private String player;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("owner_id")
    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("duration")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @JsonProperty("photo_130")
    public String getPhoto130() {
        return photo130;
    }

    public void setPhoto130(String photo130) {
        this.photo130 = photo130;
    }

    @JsonProperty("photo_320")
    public String getPhoto320() {
        return photo320;
    }

    public void setPhoto320(String photo320) {
        this.photo320 = photo320;
    }

    @JsonProperty("photo_640")
    public String getPhoto640() {
        return photo640;
    }

    public void setPhoto640(String photo640) {
        this.photo640 = photo640;
    }

    @JsonProperty("photo_800")
    public String getPhoto800() {
        return photo800;
    }

    public void setPhoto800(String photo800) {
        this.photo800 = photo800;
    }

    @JsonProperty("date")
    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @JsonProperty("adding_date")
    public Long getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(Long addingDate) {
        this.addingDate = addingDate;
    }

    @JsonProperty("views")
    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    @JsonProperty("comments")
    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    @JsonProperty("player")
    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}