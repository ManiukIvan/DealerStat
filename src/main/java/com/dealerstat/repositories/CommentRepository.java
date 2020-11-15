package com.dealerstat.repositories;

import com.dealerstat.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByApproved(boolean approved);

    List<Comment> findAllByUserIdAndApproved(long userId, boolean approved);
}
