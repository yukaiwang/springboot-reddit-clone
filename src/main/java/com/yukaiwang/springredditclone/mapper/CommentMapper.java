package com.yukaiwang.springredditclone.mapper;

import com.yukaiwang.springredditclone.dto.CommentsDto;
import com.yukaiwang.springredditclone.model.Comment;
import com.yukaiwang.springredditclone.model.Post;
import com.yukaiwang.springredditclone.model.User;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CommentMapper {

    public Comment toComment(CommentsDto commentsDto, Post post, User user) {
        return Comment.builder()
                .text(commentsDto.getText())
                .post(post)
                .createdDate(Instant.now())
                .user(user)
                .build();
    }

    public CommentsDto toDto(Comment comment) {
        return CommentsDto.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .createdDate(comment.getCreatedDate())
                .text(comment.getText())
                .userName(comment.getUser().getUsername())
                .build();
    }
}
