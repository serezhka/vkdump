package com.github.serezhka.vkdump.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Entity
@Table(name = "photo", schema = "vkdump")
public class PhotoEntity extends AbstractEntity {

    private Long photoId;
    private Long albumId;
    private Long ownerId;
    private Long userId;
    private String text;
    private Long date;
    private String photo75;
    private String photo130;
    private String photo604;
    private String photo807;
    private String photo1280;
    private String photo2560;
    private Integer width;
    private Integer height;

    @Column(name = "photo_id")
    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    @Column(name = "album_id")
    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    @Column(name = "owner_id")
    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Lob
    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "date")
    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Column(name = "photo_75")
    public String getPhoto75() {
        return photo75;
    }

    public void setPhoto75(String photo75) {
        this.photo75 = photo75;
    }

    @Column(name = "photo_130")
    public String getPhoto130() {
        return photo130;
    }

    public void setPhoto130(String photo130) {
        this.photo130 = photo130;
    }

    @Column(name = "photo_604")
    public String getPhoto604() {
        return photo604;
    }

    public void setPhoto604(String photo604) {
        this.photo604 = photo604;
    }

    @Column(name = "photo_807")
    public String getPhoto807() {
        return photo807;
    }

    public void setPhoto807(String photo807) {
        this.photo807 = photo807;
    }

    @Column(name = "photo_1280")
    public String getPhoto1280() {
        return photo1280;
    }

    public void setPhoto1280(String photo1280) {
        this.photo1280 = photo1280;
    }

    @Column(name = "photo_2560")
    public String getPhoto2560() {
        return photo2560;
    }

    public void setPhoto2560(String photo2560) {
        this.photo2560 = photo2560;
    }

    @Column(name = "width")
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Column(name = "height")
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
