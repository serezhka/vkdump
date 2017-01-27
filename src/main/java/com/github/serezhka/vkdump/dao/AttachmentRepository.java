package com.github.serezhka.vkdump.dao;

import com.github.serezhka.vkdump.dao.entity.AttachmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Integer> {

    Page<AttachmentEntity> findByType(String type, Pageable pageable);

    Page<AttachmentEntity> findByTypeOrderByMessage_DateDesc(String type, Pageable pageable);

    Page<AttachmentEntity> findByMessage_DialogIdAndType(int dialogId, String type, Pageable pageable);
}
