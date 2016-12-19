package com.github.serezhka.vkdump.dao.entity;

import javax.persistence.*;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Entity
@Table(name = "attachment", schema = "vkdump")
public class AttachmentEntity extends AbstractEntity {

    private MessageEntity message;

    private String type;

    private PhotoEntity photo;
    private VideoEntity video;
    private AudioEntity audio;
    private DocEntity doc;
    private StickerEntity sticker;
    private LinkEntity link;
    private GiftEntity gift;

    @ManyToOne
    public MessageEntity getMessage() {
        return message;
    }

    public void setMessage(MessageEntity message) {
        this.message = message;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public PhotoEntity getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoEntity photo) {
        this.photo = photo;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public VideoEntity getVideo() {
        return video;
    }

    public void setVideo(VideoEntity video) {
        this.video = video;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public AudioEntity getAudio() {
        return audio;
    }

    public void setAudio(AudioEntity audio) {
        this.audio = audio;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public DocEntity getDoc() {
        return doc;
    }

    public void setDoc(DocEntity doc) {
        this.doc = doc;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public StickerEntity getSticker() {
        return sticker;
    }

    public void setSticker(StickerEntity sticker) {
        this.sticker = sticker;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public LinkEntity getLink() {
        return link;
    }

    public void setLink(LinkEntity link) {
        this.link = link;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public GiftEntity getGift() {
        return gift;
    }

    public void setGift(GiftEntity gift) {
        this.gift = gift;
    }
}
