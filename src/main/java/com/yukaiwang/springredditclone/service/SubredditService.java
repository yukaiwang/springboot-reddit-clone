package com.yukaiwang.springredditclone.service;

import com.yukaiwang.springredditclone.dto.SubredditDto;
import com.yukaiwang.springredditclone.exception.SpringRedditException;
import com.yukaiwang.springredditclone.mapper.SubredditMapper;
import com.yukaiwang.springredditclone.model.Subreddit;
import com.yukaiwang.springredditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit = subredditRepository.save(subredditMapper.toSubreddit(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }


    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubredditDto getSubredditBy(Long id) {
         Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));
         return subredditMapper.toDto(subreddit);
    }
}
