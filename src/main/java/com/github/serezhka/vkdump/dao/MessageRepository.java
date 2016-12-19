package com.github.serezhka.vkdump.dao;

import com.github.serezhka.vkdump.dao.entity.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    @Query("SELECT o FROM MessageEntity o WHERE o.date IN (SELECT MAX(e.date) FROM MessageEntity e WHERE e.messageId IS NOT NULL GROUP BY e.dialogId) ORDER BY o.date DESC")
    Page<MessageEntity> findLastMessagesInDialogs(Pageable pageable);

    @Query("SELECT o FROM MessageEntity o WHERE o.date IN (SELECT MAX(e.date) FROM MessageEntity e WHERE e.messageId IS NOT NULL AND UPPER(e.body) LIKE UPPER(CONCAT('%',:search,'%')) GROUP BY e.dialogId) ORDER BY o.date DESC")
    Page<MessageEntity> findInDialogs(@Param("search") String search, Pageable pageable);

    Page<MessageEntity> findByDialogIdAndMessageIdIsNotNullOrderByDateDesc(long dialogId, Pageable pageable);

    Page<MessageEntity> findByDialogIdAndBodyContainingIgnoreCaseAndMessageIdIsNotNullOrderByDateDesc(Long userId, String body, Pageable pageable);

    @Query("SELECT MAX(o.date) FROM MessageEntity o")
    Long findLastMessageDate();

    @Query("SELECT MAX(o.date) FROM MessageEntity o WHERE o.dialogId = :dialogId")
    Long findLastMessageDate(@Param("dialogId") long dialogId);
}
