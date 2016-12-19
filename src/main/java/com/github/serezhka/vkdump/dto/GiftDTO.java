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
public class GiftDTO implements Serializable {

    private Long id;
    private String thumb48;
    private String thumb96;
    private String thumb256;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("thumb_48")
    public String getThumb48() {
        return thumb48;
    }

    public void setThumb48(String thumb48) {
        this.thumb48 = thumb48;
    }

    @JsonProperty("thumb_96")
    public String getThumb96() {
        return thumb96;
    }

    public void setThumb96(String thumb96) {
        this.thumb96 = thumb96;
    }

    @JsonProperty("thumb_256")
    public String getThumb256() {
        return thumb256;
    }

    public void setThumb256(String thumb256) {
        this.thumb256 = thumb256;
    }
}
