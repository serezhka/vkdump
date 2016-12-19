package com.github.serezhka.vkdump.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Entity
@Table(name = "video", schema = "vkdump")
public class VideoEntity extends AbstractEntity {

    private Long videoId;
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

    @Column(name = "video_id")
    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    @Column(name = "owner_id")
    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Lob
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Lob
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "duration")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Column(name = "photo_130")
    public String getPhoto130() {
        return photo130;
    }

    public void setPhoto130(String photo130) {
        this.photo130 = photo130;
    }

    @Column(name = "photo_320")
    public String getPhoto320() {
        return photo320;
    }

    public void setPhoto320(String photo320) {
        this.photo320 = photo320;
    }

    @Column(name = "photo_640")
    public String getPhoto640() {
        return photo640;
    }

    public void setPhoto640(String photo640) {
        this.photo640 = photo640;
    }

    @Column(name = "photo_800")
    public String getPhoto800() {
        return photo800;
    }

    public void setPhoto800(String photo800) {
        this.photo800 = photo800;
    }

    @Column(name = "date")
    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Column(name = "adding_date")
    public Long getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(Long addingDate) {
        this.addingDate = addingDate;
    }

    @Column(name = "views")
    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    @Column(name = "comments")
    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    @Column(name = "player")
    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
