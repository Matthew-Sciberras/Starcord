package com.starcord.main.repositories;

import com.starcord.main.enums.ChannelType;
import com.starcord.main.models.Channel;
import com.starcord.main.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    @Query("SELECT cm.channel FROM ChannelMember cm " +
            "WHERE cm.user = :user AND cm.channel.channelType IN :types " +
            "ORDER BY COALESCE(cm.channel.lastMessageAt, cm.channel.createdAt) DESC")
    List<Channel> findUserChannelsSorted(
            @Param("user") User user,
            @Param("types") List<ChannelType> types
    );

    @Query("SELECT COUNT(cm) > 0 FROM ChannelMember cm WHERE cm.channel.id = :channelId AND cm.user.id = :userId")
    boolean isUserInChannel(@Param("channelId") Long channelId, @Param("userId") Long userId);

    @Query("SELECT c FROM Channel c LEFT JOIN FETCH c.members WHERE c.id = :id")
    Optional<Channel> findByIdWithMembers(@Param("id") Long id);
}
