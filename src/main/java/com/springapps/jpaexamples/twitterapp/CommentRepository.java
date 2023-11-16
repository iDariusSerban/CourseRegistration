package com.springapps.jpaexamples.twitterapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    public void deleteAllByTweet_Id(Long tweetId);

    public List<Comment> findAllByTweet_User_Id(Long userId);


    @Query("SELECT c FROM Comment c WHERE c.tweet.user.id = :userId")
    public List<Comment> findAllByUserId(@Param("userId") Long userId);
}
