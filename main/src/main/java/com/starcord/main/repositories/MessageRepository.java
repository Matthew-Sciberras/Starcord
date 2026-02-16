package com.starcord.main.repositories;

import com.starcord.main.models.Channel;
import com.starcord.main.models.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> getAllByChannel(Channel channel);
    List<Message> getAllByChannelOrderByTimestampAsc(Channel channel);
    Page<Message> findByChannel(Channel channel, Pageable pageable);
    Page<Message> findByChannelAndTimestampBefore(Channel channel, Instant timestampBefore, Pageable pageable);
    Page<Message> findByChannelAndTimestampAfter(Channel channel, Instant timestampAfter, Pageable pageable);
    Page<Message> findByChannelAndTimestampBetween(Channel channel, Instant timestampAfter, Instant timestampBefore, Pageable pageable);
}
