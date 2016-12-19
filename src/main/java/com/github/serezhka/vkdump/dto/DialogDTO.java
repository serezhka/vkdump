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
public class DialogDTO implements Serializable {

    public Integer unread;
    public MessageDTO message;
    public Long inRead;
    public Long outRead;

    @JsonProperty("unread")
    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }

    @JsonProperty("message")
    public MessageDTO getMessage() {
        return message;
    }

    public void setMessage(MessageDTO message) {
        this.message = message;
    }

    @JsonProperty("in_read")
    public Long getInRead() {
        return inRead;
    }

    public void setInRead(Long inRead) {
        this.inRead = inRead;
    }

    @JsonProperty("out_read")
    public Long getOutRead() {
        return outRead;
    }

    public void setOutRead(Long outRead) {
        this.outRead = outRead;
    }
}
