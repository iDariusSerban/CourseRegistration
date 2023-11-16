package com.springapps.jpaexamples.twitterapp;

import com.springapps.jpaexamples.orderapp.Order;
import com.springapps.jpaexamples.orderapp.OrderRepository;
import com.springapps.jpaexamples.orderapp.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Runner implements CommandLineRunner {

    UserService userService;

    TweetService tweetService;

    @Autowired
    public Runner(UserService userService, TweetService tweetService) {
        this.userService = userService;
        this.tweetService = tweetService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Costel");
        userService.saveUser(user);

        Tweet tweet = new Tweet("bitcoin is up");
        userService.addTweetToUser1(1L,tweet);

        Tweet foundTweet = tweetService.findByText("bitcoin is up");
        Comment comment = new Comment("ce misto");
        Comment comment1 = new Comment("cine esti tu, ma?");
        tweetService.addCommentToTweet2(foundTweet.getId(), comment);
        tweetService.addCommentToTweet2(foundTweet.getId(), comment1);
        System.out.println(userService.findAlCommentsByUser3(1L));

        //userService.deleteAllTweetsFromUser(1L);
        //userService.deleteAllTweetsFromUser2(1L);
        //userService.deleteUser(1L);


    }
}
