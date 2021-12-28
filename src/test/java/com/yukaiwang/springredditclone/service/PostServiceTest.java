package com.yukaiwang.springredditclone.service;

import com.yukaiwang.springredditclone.dto.PostRequest;
import com.yukaiwang.springredditclone.dto.PostResponse;
import com.yukaiwang.springredditclone.mapper.PostMapper;
import com.yukaiwang.springredditclone.model.Post;
import com.yukaiwang.springredditclone.model.Subreddit;
import com.yukaiwang.springredditclone.model.User;
import com.yukaiwang.springredditclone.repository.PostRepository;
import com.yukaiwang.springredditclone.repository.SubredditRepository;
import com.yukaiwang.springredditclone.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    PostRepository postRepository;
    @Mock
    SubredditRepository subredditRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PostMapper postMapper;
    @Mock
    AuthService authService;

    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

    private PostService postService;

    @BeforeEach
    public void setup() {
        postService = new PostService(postRepository, subredditRepository, userRepository, postMapper, authService);
    }

    @Test
    void shouldFindPostById() {
        Post post = new Post(123L, "first post", "http://google.com", "test", 0, null, Instant.now(), null);
        PostResponse expectedResponse = new PostResponse(123L, "first post", "http://google.com", "test", "test user", "test subreddit", 0, 0, null, false, false);
        Mockito.when(postRepository.findById(123L))
                .thenReturn(Optional.of(post));
        Mockito.when(postMapper.toDto(Mockito.any(Post.class)))
                .thenReturn(expectedResponse);

        PostResponse actualResponse = postService.getPost(123L);
        Assertions.assertThat(actualResponse.getId()).isEqualTo(expectedResponse.getId());
        Assertions.assertThat(actualResponse.getPostName()).isEqualTo(expectedResponse.getPostName());
    }

    @Test
    void shouldSavePost() {
        PostRequest postRequest = new PostRequest(null, "first subreddit", "first post", "http://google.com", "test");
        User user = new User(123L, "test user", "password", "user@test.com", Instant.now(), true);
        Subreddit subreddit = new Subreddit(123L, "test subreddit", "test", null, Instant.now(), user);
        Post post = new Post(123L, "first post", "http://google.com", "test", 0, null, Instant.now(), null);

        Mockito.when(subredditRepository.findByName(postRequest.getSubredditName())).thenReturn(Optional.of(subreddit));
        Mockito.when(authService.getCurrentUser()).thenReturn(user);
        Mockito.when(postMapper.toPost(postRequest, subreddit, user)).thenReturn(post);
        postService.save(postRequest);
        Mockito.verify(postRepository, Mockito.times(1)).save(postArgumentCaptor.capture());

        Assertions.assertThat(postArgumentCaptor.getValue().getName()).isEqualTo(post.getName());
    }
}