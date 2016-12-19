package com.github.serezhka.vkdump.service;

import com.github.serezhka.vkdump.dao.MessageRepository;
import com.github.serezhka.vkdump.dao.entity.MessageEntity;
import com.github.serezhka.vkdump.dto.DialogDTO;
import com.github.serezhka.vkdump.dto.MessageDTO;
import com.github.serezhka.vkdump.util.converter.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public Page<DialogDTO> getDialogs(Pageable pageable) {
        Page<MessageEntity> messageEntities = messageRepository.findLastMessagesInDialogs(pageable);
        return messageEntities.map(messageEntity -> {
            DialogDTO dialogDTO = new DialogDTO();
            dialogDTO.message = EntityConverter.messageEntityToDto(messageEntity);
            return dialogDTO;
        });
    }

    public Page<DialogDTO> getDialogs(String search, Pageable pageable) {
        if (search == null || search.isEmpty()) return getDialogs(pageable);
        Page<MessageEntity> messageEntities = messageRepository.findInDialogs(search, pageable);
        return messageEntities.map(messageEntity -> {
            DialogDTO dialogDTO = new DialogDTO();
            dialogDTO.message = EntityConverter.messageEntityToDto(messageEntity);
            return dialogDTO;
        });
    }

    public Page<MessageDTO> getMessages(long dialogId, Pageable pageable) {
        Page<MessageEntity> messageEntities = messageRepository.findByDialogIdAndMessageIdIsNotNullOrderByDateDesc(dialogId, pageable);
        return messageEntities.map(EntityConverter::messageEntityToDto);
    }

    public Page<MessageDTO> getMessages(long dialogId, String search, Pageable pageable) {
        if (search == null || search.isEmpty()) return getMessages(dialogId, pageable);
        Page<MessageEntity> messageEntities = messageRepository.findByDialogIdAndBodyContainingIgnoreCaseAndMessageIdIsNotNullOrderByDateDesc(dialogId, search, pageable);
        return messageEntities.map(EntityConverter::messageEntityToDto);
    }

    public void saveMessages(List<MessageDTO> messages) {
        messageRepository.save(messages.stream()
                .map(EntityConverter::messageDtoToEntity)
                .collect(Collectors.toList()));
    }

    public long getLastMessageDate() {
        Long lastMessageDate = messageRepository.findLastMessageDate();
        return lastMessageDate == null ? -1 : lastMessageDate;
    }

    public long getLastMessageDateInDialog(long dialogId) {
        Long lastMessageDate = messageRepository.findLastMessageDate(dialogId);
        return lastMessageDate == null ? -1 : lastMessageDate;
    }
}
