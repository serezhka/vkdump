package com.github.serezhka.vkdump.service;

import com.github.serezhka.vkdump.dao.AttachmentRepository;
import com.github.serezhka.vkdump.dao.entity.AttachmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public Page<AttachmentEntity> getAttachments(String type, Pageable pageable) {
        return attachmentRepository.findByTypeOrderByMessage_DateDesc(type, pageable);
    }

    public Page<AttachmentEntity> getAttachments(int dialogId, String type, Pageable pageable) {
        return attachmentRepository.findByMessage_DialogIdAndType(dialogId, type, pageable);
    }
}
