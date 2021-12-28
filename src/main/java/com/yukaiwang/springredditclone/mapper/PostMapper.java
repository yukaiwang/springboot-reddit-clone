package com.yukaiwang.springredditclone.mapper;

import com.yukaiwang.springredditclone.dto.PostRequest;
import com.yukaiwang.springredditclone.dto.PostResponse;
import com.yukaiwang.springredditclone.model.Post;
import com.yukaiwang.springredditclone.model.Subreddit;
import com.yukaiwang.springredditclone.model.User;
import com.yukaiwang.springredditclone.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class PostMapper {

    private CommentRepository commentRepository;

    public Post toPost(PostRequest postRequest, Subreddit subreddit, User user) {
        return Post.builder()
                .name(postRequest.getPostName())
                .url(postRequest.getUrl())
                .description(postRequest.getDescription())
                .voteCount(0)
                .user(user)
                .createdDate(Instant.now())
                .subreddit(subreddit)
                .build();
    }

    public PostResponse toDto(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .postName(post.getName())
                .url(post.getUrl())
                .description(post.getDescription())
                .userName(post.getUser().getUsername())
                .subredditName(post.getSubreddit().getName())
                .voteCount(post.getVoteCount())
                .commentCount(commentCount(post))
                .build();
    }

    private int commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

}
