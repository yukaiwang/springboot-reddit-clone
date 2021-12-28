package com.yukaiwang.springredditclone.mapper;

import com.yukaiwang.springredditclone.dto.SubredditDto;
import com.yukaiwang.springredditclone.model.Subreddit;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class SubredditMapper {

    public Subreddit toSubreddit(SubredditDto subredditDto) {
        return Subreddit.builder()
                .name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .createdDate(Instant.now())
                .build();
    }

    public SubredditDto toDto(Subreddit subreddit) {
        return SubredditDto.builder()
                .id(subreddit.getId())
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .numberOfPosts(subreddit.getPosts().size())
                .build();
    }
}
