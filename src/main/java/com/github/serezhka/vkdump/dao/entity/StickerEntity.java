package com.github.serezhka.vkdump.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Entity
@Table(name = "sticker", schema = "vkdump")
public class StickerEntity extends AbstractEntity {

    private Long stickerId;
    private Long productId;
    private String photo64;
    private String photo128;
    private String photo256;
    private String photo352;
    private Integer width;
    private Integer height;

    @Column(name = "sticker_id")
    public Long getStickerId() {
        return stickerId;
    }

    public void setStickerId(Long stickerId) {
        this.stickerId = stickerId;
    }

    @Column(name = "product_id")
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Column(name = "photo64")
    public String getPhoto64() {
        return photo64;
    }

    public void setPhoto64(String photo64) {
        this.photo64 = photo64;
    }

    @Column(name = "photo126")
    public String getPhoto128() {
        return photo128;
    }

    public void setPhoto128(String photo128) {
        this.photo128 = photo128;
    }

    @Column(name = "photo256")
    public String getPhoto256() {
        return photo256;
    }

    public void setPhoto256(String photo256) {
        this.photo256 = photo256;
    }

    @Column(name = "photo352")
    public String getPhoto352() {
        return photo352;
    }

    public void setPhoto352(String photo352) {
        this.photo352 = photo352;
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
