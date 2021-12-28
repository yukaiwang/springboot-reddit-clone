package com.yukaiwang.springredditclone.service;

import com.yukaiwang.springredditclone.exception.SpringRedditException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommentServiceTest {

    @Test
    void shouldNotContainsSwearWordsInsideComment() {
        CommentService commentService = new CommentService(null, null, null, null, null);
//        assertFalse(commentService.containsSwearWords("This is a clean comment"));
        assertThat(commentService.containsSwearWords("This is a clean comment")).isFalse();
    }

    @Test
    void shouldFailsWhenCommentContainsSwearWords() {
        CommentService commentService = new CommentService(null, null, null, null, null);
//        SpringRedditException exception = assertThrows(SpringRedditException.class, () -> commentService.containsSwearWords("This is shit"));
//        assertTrue(exception.getMessage().contains("Comment contains unacceptable language"));
        assertThatThrownBy(() -> commentService.containsSwearWords("This is shit"))
                .isInstanceOf(SpringRedditException.class)
                .hasMessage("Comment contains unacceptable language");
    }
}