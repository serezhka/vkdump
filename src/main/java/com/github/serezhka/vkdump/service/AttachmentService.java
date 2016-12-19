package com.github.serezhka.vkdump.service;

import com.github.serezhka.vkdump.dao.AttachmentRepository;
import com.github.serezhka.vkdump.dao.entity.AttachmentEntity;
import com.github.serezhka.vkdump.dto.AttachmentDTO;
import com.github.serezhka.vkdump.util.converter.EntityConverter;
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

    public Page<AttachmentDTO> getAttachments(String type, Pageable pageable) {
        return attachmentRepository.findByTypeOrderByMessage_DateDesc(type, pageable).map(EntityConverter::attachmentEntityToDto);
    }

    public Page<AttachmentDTO> getAttachments(Long dialogId, String type, Pageable pageable) {
        Page<AttachmentEntity> attachmentEntities = attachmentRepository.findByMessage_DialogIdAndType(dialogId, type, pageable);
        return attachmentEntities.map(EntityConverter::attachmentEntityToDto);
    }
}
