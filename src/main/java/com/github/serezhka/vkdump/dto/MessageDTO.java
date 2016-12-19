package com.github.serezhka.vkdump.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class MessageDTO implements Serializable {

    private Long id;
    private Long userId;
    private Long fromId;
    private Long date;
    private Integer readState;
    private Integer out;
    private String title;
    private String body;
    private Long chatId;
    private Collection<AttachmentDTO> attachments;
    private Collection<MessageDTO> fwdMessages;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @JsonProperty("from_id")
    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    @JsonProperty("date")
    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @JsonProperty("read_state")
    public Integer getReadState() {
        return readState;
    }

    public void setReadState(Integer readState) {
        this.readState = readState;
    }

    @JsonProperty("out")
    public Integer getOut() {
        return out;
    }

    public void setOut(Integer out) {
        this.out = out;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("body")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @JsonProperty("chat_id")
    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    @JsonProperty("attachments")
    public Collection<AttachmentDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(Collection<AttachmentDTO> attachments) {
        this.attachments = attachments;
    }

    @JsonProperty("fwd_messages")
    public Collection<MessageDTO> getFwdMessages() {
        return fwdMessages;
    }

    public void setFwdMessages(Collection<MessageDTO> fwdMessages) {
        this.fwdMessages = fwdMessages;
    }
}
