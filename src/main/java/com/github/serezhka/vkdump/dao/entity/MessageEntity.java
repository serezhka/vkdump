package com.github.serezhka.vkdump.dao.entity;

import javax.persistence.*;
import java.util.List;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Entity
@Table(name = "message", schema = "vkdump")
public class MessageEntity extends AbstractEntity {

    private Long messageId;
    private Long fromId;
    private Long dialogId;
    private Long date;
    private Integer readState;
    private Integer out;
    private String body;

    private MessageEntity parentMessage;
    private List<MessageEntity> fwdMessages;

    private List<AttachmentEntity> attachments;

    @Column(name = "message_id")
    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    @Column(name = "from_id")
    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    @Column(name = "dialog_id")
    public Long getDialogId() {
        return dialogId;
    }

    public void setDialogId(Long dialogId) {
        this.dialogId = dialogId;
    }

    @Column(name = "date")
    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Column(name = "read_state")
    public Integer getReadState() {
        return readState;
    }

    public void setReadState(Integer readState) {
        this.readState = readState;
    }

    @Column(name = "out")
    public Integer getOut() {
        return out;
    }

    public void setOut(Integer out) {
        this.out = out;
    }

    @Lob
    @Column(name = "body")
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