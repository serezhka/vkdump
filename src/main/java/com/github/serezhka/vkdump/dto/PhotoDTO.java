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
public class PhotoDTO implements Serializable {

    private Long id;
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

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("album_id")
    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    @JsonProperty("owner_id")
    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @JsonProperty("user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("date")
    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @JsonProperty("photo_75")
    public String getPhoto75() {
        return photo75;
    }

    public void setPhoto75(String photo75) {
        this.photo75 = photo75;
    }

    @JsonProperty("photo_130")
    public String getPhoto130() {
        return photo130;
    }

    public void setPhoto130(String photo130) {
        this.photo130 = photo130;
    }

    @JsonProperty("photo_604")
    public String getPhoto604() {
        return photo604;
    }

    public void setPhoto604(String photo604) {
        this.photo604 = photo604;
    }

    @JsonProperty("photo_807")
    public String getPhoto807() {
        return photo807;
    }

    public void setPhoto807(String photo807) {
        this.photo807 = photo807;
    }

    @JsonProperty("photo_1280")
    public String getPhoto1280() {
        return photo1280;
    }

    public void setPhoto1280(String photo1280) {
        this.photo1280 = photo1280;
    }

    @JsonProperty("photo_2560")
    public String getPhoto2560() {
        return photo2560;
    }

    public void setPhoto2560(String photo2560) {
        this.photo2560 = photo2560;
    }

    @JsonProperty("width")
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @JsonProperty("height")
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
