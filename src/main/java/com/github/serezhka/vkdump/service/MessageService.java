package com.github.serezhka.vkdump.service;

import com.github.serezhka.vkdump.dao.MessageRepository;
import com.github.serezhka.vkdump.dao.entity.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Stream<MessageEntity> getDialogs(){
        return messageRepository.findLastMessagesInDialogs();
    }

    public Page<MessageEntity> getDialogs(Pageable pageable) {
        return messageRepository.findLastMessagesInDialogs(pageable);
    }

    public Page<MessageEntity> getDialogs(String search, Pageable pageable) {
        if (search == null || search.isEmpty()) return getDialogs(pageable);
        return messageRepository.findInDialogs(search, pageable);
    }

    public Stream<MessageEntity> getMessages(int dialogId) {
        return messageRepository.findByDialogIdAndMessageIdIsNotNullOrderByDateDesc(dialogId);
    }

    public Page<MessageEntity> getMessages(int dialogId, Pageable pageable) {
        return messageRepository.findByDialogIdAndMessageIdIsNotNullOrderByDateDesc(dialogId, pageable);
    }

    public Page<MessageEntity> getMessages(int dialogId, String search, Pageable pageable) {
        if (search == null || search.isEmpty()) return getMessages(dialogId, pageable);
        return messageRepository.findByDialogIdAndBodyContainingIgnoreCaseAndMessageIdIsNotNullOrderByDateDesc(dialogId, search, pageable);
    }

    public void saveMessages(List<MessageEntity> messages) {
        messageRepository.save(messages);
    }

    public int getLastMessageId() {
        return messageRepository.findLastMessageId();
    }

    public int getLastMessageIdInDialog(int dialogId) {
        return messageRepository.findLastMessageId(dialogId);
    }
}
