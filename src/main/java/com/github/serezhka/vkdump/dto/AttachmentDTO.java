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
public class AttachmentDTO implements Serializable {

    private String type;
    private PhotoDTO photo;
    private VideoDTO video;
    private AudioDTO audio;
    private DocDTO doc;
    private StickerDTO sticker;
    private LinkDTO link;
    private GiftDTO gift;

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("photo")
    public PhotoDTO getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoDTO photo) {
        this.photo = photo;
    }

    @JsonProperty("video")
    public VideoDTO getVideo() {
        return video;
    }

    public void setVideo(VideoDTO video) {
        this.video = video;
    }

    @JsonProperty("audio")
    public AudioDTO getAudio() {
        return audio;
    }

    public void setAudio(AudioDTO audio) {
        this.audio = audio;
    }

    @JsonProperty("doc")
    public DocDTO getDoc() {
        return doc;
    }

    public void setDoc(DocDTO doc) {
        this.doc = doc;
    }

    @JsonProperty("sticker")
    public StickerDTO getSticker() {
        return sticker;
    }

    public void setSticker(StickerDTO sticker) {
        this.sticker = sticker;
    }

    @JsonProperty("link")
    public LinkDTO getLink() {
        return link;
    }

    public void setLink(LinkDTO link) {
        this.link = link;
    }

    @JsonProperty("gift")
    public GiftDTO getGift() {
        return gift;
    }

    public void setGift(GiftDTO gift) {
        this.gift = gift;
    }
}
