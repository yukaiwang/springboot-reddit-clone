package com.yukaiwang.springredditclone.repository;

import com.yukaiwang.springredditclone.model.Post;
import com.yukaiwang.springredditclone.model.User;
import com.yukaiwang.springredditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findTopByPostAndUserOrderByIdDesc(Post post, User user);
}
