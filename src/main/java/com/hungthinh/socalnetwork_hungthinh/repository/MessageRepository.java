package com.hungthinh.socalnetwork_hungthinh.repository;

import com.hungthinh.socalnetwork_hungthinh.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    @Query(value = "SELECT m FROM Message m WHERE (m.recipientUsername = ?1 AND m.senderUsername = ?2) OR (m.recipientUsername = ?2 AND m.senderUsername = ?1)")
    List<Message> getMessages(String senderUsername, String recipientUsername);
}
