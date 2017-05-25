package com.github.serezhka.vkdump.dao;

import com.github.serezhka.vkdump.dao.entity.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

    @Query("SELECT o FROM MessageEntity o WHERE o.messageId IN (SELECT MAX(e.messageId) FROM MessageEntity e GROUP BY e.dialogId) ORDER BY o.messageId DESC")
    Stream<MessageEntity> findLastMessagesInDialogs();

    @Query("SELECT o FROM MessageEntity o WHERE o.messageId IN (SELECT MAX(e.messageId) FROM MessageEntity e GROUP BY e.dialogId) ORDER BY o.messageId DESC")
    Page<MessageEntity> findLastMessagesInDialogs(Pageable pageable);

    @Query("SELECT o FROM MessageEntity o WHERE o.messageId IN (SELECT MAX(e.messageId) FROM MessageEntity e WHERE UPPER(e.body) LIKE UPPER(CONCAT('%',:search,'%')) GROUP BY e.dialogId) ORDER BY o.messageId DESC")
    Page<MessageEntity> findInDialogs(@Param("search") String search, Pageable pageable);

    Stream<MessageEntity> findByDialogIdAndMessageIdIsNotNullOrderByDateDesc(int dialogId);

    Page<MessageEntity> findByDialogIdAndMessageIdIsNotNullOrderByDateDesc(int dialogId, Pageable pageable);

    Page<MessageEntity> findByDialogIdAndBodyContainingIgnoreCaseAndMessageIdIsNotNullOrderByDateDesc(Integer userId, String body, Pageable pageable);

    @Query("SELECT COALESCE(MAX(o.messageId), 0) FROM MessageEntity o")
    int findLastMessageId();

    @Query("SELECT COALESCE(MAX(o.messageId), 0) FROM MessageEntity o WHERE o.dialogId = :dialogId")
    int findLastMessageId(@Param("dialogId") int dialogId);
}
