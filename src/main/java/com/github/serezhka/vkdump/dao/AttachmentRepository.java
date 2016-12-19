package com.github.serezhka.vkdump.dao;

import com.github.serezhka.vkdump.dao.entity.AttachmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Long> {

    Page<AttachmentEntity> findByType(String type, Pageable pageable);

    Page<AttachmentEntity> findByTypeOrderByMessage_DateDesc(String type, Pageable pageable);

    Page<AttachmentEntity> findByMessage_DialogIdAndType(Long dialogId, String type, Pageable pageable);
}
