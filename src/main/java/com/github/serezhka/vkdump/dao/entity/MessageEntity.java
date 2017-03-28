package com.github.serezhka.vkdump.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Entity
@Table(name = "message", schema = "vkdump")
public class MessageEntity extends AbstractEntity {

    private Integer messageId;
    private Integer fromId;
    private Integer dialogId;
    private Integer date;
    private Boolean readState;
    private String tittle;
    private Boolean out;
    private String body;

    @JsonIgnore
    private MessageEntity parentMessage;

    private List<MessageEntity> fwdMessages;

    private List<AttachmentEntity> attachments;

    @Column(name = "message_id")
    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Column(name = "from_id")
    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    @Column(name = "dialog_id")
    public Integer getDialogId() {
        return dialogId;
    }

    public void setDialogId(Integer dialogId) {
        this.dialogId = dialogId;
    }

    @Column(name = "date")
    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    @Column(name = "read_state")
    public Boolean getReadState() {
        return readState;
    }

    public void setReadState(Boolean readState) {
        this.readState = readState;
    }

    @Column(name = "tittle")
    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    @Column(name = "out")
    public Boolean getOut() {
        return out;
    }

    public void setOut(Boolean out) {
        this.out = out;
    }

    @Column(name = "body", length = 4096)
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @ManyToOne
    public MessageEntity getParentMessage() {
        return parentMessage;
    }

    public void setParentMessage(MessageEntity parentMessage) {
        this.parentMessage = parentMessage;
    }

    @OneToMany(mappedBy = "parentMessage", cascade = CascadeType.ALL)
    public List<MessageEntity> getFwdMessages() {
        return fwdMessages;
    }

    public void setFwdMessages(List<MessageEntity> fwdMessages) {
        this.fwdMessages = fwdMessages;
    }

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    public List<AttachmentEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentEntity> attachments) {
        this.attachments = attachments;
    }
}
