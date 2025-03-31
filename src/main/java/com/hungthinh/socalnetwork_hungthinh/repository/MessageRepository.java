package com.hungthinh.socalnetwork_hungthinh.repository;

import com.hungthinh.socalnetwork_hungthinh.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    @Query("SELECT m FROM Message m WHERE (m.senderUsername = :sender AND m.recipientUsername = :recipient)")
    List<Message> getMessages(@Param("sender") String sender, @Param("recipient") String recipient);

}
