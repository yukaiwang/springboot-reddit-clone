package com.yukaiwang.springredditclone.service;

import com.yukaiwang.springredditclone.dto.CommentsDto;
import com.yukaiwang.springredditclone.exception.PostNotFoundException;
import com.yukaiwang.springredditclone.exception.SpringRedditException;
import com.yukaiwang.springredditclone.mapper.CommentMapper;
import com.yukaiwang.springredditclone.model.Post;
import com.yukaiwang.springredditclone.model.User;
import com.yukaiwang.springredditclone.repository.CommentRepository;
import com.yukaiwang.springredditclone.repository.PostRepository;
import com.yukaiwang.springredditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final AuthService authService;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        commentRepository.save(commentMapper.toComment(commentsDto, post, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findByUser(user)
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    public boolean containsSwearWords(String comment) {
        if (comment.contains("shit")) {
            throw new SpringRedditException("Comment contains unacceptable language");
        }
        return false;
    }
}
