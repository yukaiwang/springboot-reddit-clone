package com.yukaiwang.springredditclone.service;

import com.yukaiwang.springredditclone.dto.PostRequest;
import com.yukaiwang.springredditclone.dto.PostResponse;
import com.yukaiwang.springredditclone.exception.PostNotFoundException;
import com.yukaiwang.springredditclone.exception.SubredditNotFoundException;
import com.yukaiwang.springredditclone.mapper.PostMapper;
import com.yukaiwang.springredditclone.model.Post;
import com.yukaiwang.springredditclone.model.Subreddit;
import com.yukaiwang.springredditclone.model.User;
import com.yukaiwang.springredditclone.repository.PostRepository;
import com.yukaiwang.springredditclone.repository.SubredditRepository;
import com.yukaiwang.springredditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final AuthService authService;

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        postRepository.save(postMapper.toPost(postRequest, subreddit, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.toDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        return postRepository.findAllBySubreddit(subreddit)
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }
}
