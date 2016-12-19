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
public class StickerDTO implements Serializable {

    private Long id;
    private Long productId;
    private String photo64;
    private String photo128;
    private String photo256;
    private String photo352;
    private Integer width;
    private Integer height;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("product_id")
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @JsonProperty("photo_64")
    public String getPhoto64() {
        return photo64;
    }

    public void setPhoto64(String photo64) {
        this.photo64 = photo64;
    }

    @JsonProperty("photo_128")
    public String getPhoto128() {
        return photo128;
    }

    public void setPhoto128(String photo128) {
        this.photo128 = photo128;
    }

    @JsonProperty("photo_256")
    public String getPhoto256() {
        return photo256;
    }

    public void setPhoto256(String photo256) {
        this.photo256 = photo256;
    }

    @JsonProperty("photo_352")
    public String getPhoto352() {
        return photo352;
    }

    public void setPhoto352(String photo352) {
        this.photo352 = photo352;
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
