package com.starcord.main.repositories;

import com.starcord.main.models.Channel;
import com.starcord.main.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> getAllByChannel(Channel channel);
}
