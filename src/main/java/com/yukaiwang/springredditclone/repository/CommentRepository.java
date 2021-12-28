package com.yukaiwang.springredditclone.repository;

import com.yukaiwang.springredditclone.model.Comment;
import com.yukaiwang.springredditclone.model.Post;
import com.yukaiwang.springredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);

    List<Comment> findByUser(User user);
}
