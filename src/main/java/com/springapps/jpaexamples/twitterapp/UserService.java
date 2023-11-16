package com.springapps.jpaexamples.twitterapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    UserRepository userRepository;

    TweetRepository tweetRepository;

    CommentRepository commentRepository;

    @Autowired
    public UserService(UserRepository userRepository, TweetRepository tweetRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.tweetRepository=tweetRepository;
        this.commentRepository = commentRepository;
    }

    public User saveUser (User user){
        return userRepository.save(user);

    }


    @Transactional
    public Tweet addTweetToUser2 (Long userId, Tweet tweet) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("user not found"));
        tweet.setUser(user);
        return tweetRepository.save(tweet);
    }

    @Transactional
    public User addTweetToUser1 (Long userId, Tweet tweet) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("user not found"));
        tweet.setUser(user);
        user.getTweets().add(tweet);
        return userRepository.save(user);

    }

    @Transactional
    public void deleteAllTweetsFromUser(Long userId) throws Exception {
       tweetRepository.deleteAllByUser_Id(userId);
    }
    @Transactional
    public void deleteAllTweetsFromUser1(Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("user not found"));
        for (Tweet tweet: user.getTweets()){
            tweetRepository.delete(tweet);
        }
        //tweetRepository.deleteAll(user.getTweets());
    }

    @Transactional
    public User deleteAllTweetsFromUser2(Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("user not found"));
        user.getTweets().clear();
        return userRepository.save(user);
    }


    public void deleteUser(Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("user not found"));
        userRepository.delete(user);

    }

    public List<Tweet> findAllTweetsByUser(Long userId){
        return tweetRepository.findAllByUser_Id(userId);
    }

    @Transactional
    public List<Comment> findAlCommentsByUser2(Long userId) throws Exception {
        //pasul 1- obtinem lista de tweet-uri a utilizatorului cu userId

        //var 1 - apelam metoda de mai sus, care foloseste un derived query declarat de noi in repository
        List<Tweet> tweets = findAllTweetsByUser(userId);

        //var 2 - cautam userul si prin lazy loading ii accesam tweet-urile
        //User user = userRepository.findById(userId).orElseThrow(() -> new Exception("user not found"));
        //user.getTweets();



        //pasul 2 - procesam tweet-urile gasite astfel incat sa obtinem o lista de comentarii adunata de la fiecare tweet

        //var1 - java 8 - stream pe tweets si flatMap
       return tweets.stream()
                .flatMap(t -> t.getComments().stream())
                .collect(Collectors.toList());

       //var 2 - parcurgere clasica si adaugarea elementelor intr-un array result
//        List<Comment> result = new ArrayList<>();
//        for (Tweet tweet: tweets) {
//            result.addAll(tweet.getComments());
//        }
//        return result;
    }

    @Transactional
    public List<Comment> findAlCommentsByUser(Long userId) throws Exception {
        return commentRepository.findAllByTweet_User_Id(userId);
    }

    @Transactional
    public List<Comment> findAlCommentsByUser3(Long userId) throws Exception {
        List<Comment> comments = commentRepository.findAll();
//        List<Comment> result = new ArrayList<>();
//        for (Comment comment: comments){
//            if (comment.getTweet().getUser().getId().equals(userId) ){
//                result.add(comment);
//            }
//        }
//        return result;

        return comments.stream()
                .filter(c -> c.getTweet().getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }
}
