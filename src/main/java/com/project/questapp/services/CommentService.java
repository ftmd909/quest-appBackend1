package com.project.questapp.services;

import java.util.List;
import java.util.Optional;

import com.project.questapp.entities.Comment;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repos.CommentRepository;
import com.project.questapp.request.CommentCreateRequest;
import com.project.questapp.request.CommentUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service

public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    @Autowired
    public CommentService(CommentRepository commentRepository,UserService userService,PostService postService) {
        this.commentRepository = commentRepository;
        this.userService=userService;
        this.postService=postService;
    }

    public List<Comment> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
        if(userId.isPresent() && postId.isPresent()) {
            commentRepository.findByUserIdAndPostId(userId.get(),postId.get());
        }else if(userId.isPresent()) {
            commentRepository.findByUserId(userId.get());
        }else if(postId.isPresent()) {
            commentRepository.findByPostId(postId.get());
        }else
            return commentRepository.findAll();

        return null;
    }

    public Comment getOneCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest request) {

        User user=userService.getOneUserById(request.getUserId());  //user objesini oluşturup userservisinden alalım
        Post post=postService.getOnePostById(request.getPostId());
        if(user!=null && post!=null){  //user ve post null değilse yani varsa bu commenti oluşturabiliriz
            Comment commentToSave=new Comment();  //yeni bir comment objesi oluşturdum
            commentToSave.setId(request.getId()); //ıdsini requestten alıp
            commentToSave.setPost(post); //commentin postunu dbden getirdiğimiz posta set ediyorum
            commentToSave.setUser(user); //commentin userını dbden getirdiğimiz usera set ediyorum
            commentToSave.setText(request.getText());  //requestten gelen txti set ettim
            return commentRepository.save(commentToSave);  //commentrepoya save ederek objeyi dönüyor
        }
        else{
            return null;
        }
    }

    public Comment updateOneCommentById(Long commentId, CommentUpdateRequest request) {
        Optional<Comment> comment=commentRepository.findById(commentId); //önce update edilmek istenilen comment için opsiyonel dbde var mı diye repository ile bakıyorum
        if(comment.isPresent()){ //comment dbde varsa
            Comment commentToUpdate=comment.get();
            commentToUpdate.setText(request.getText()); //commentteki texti requestten gelen text ile değiştir.
            return commentRepository.save(commentToUpdate); //repoya yeni halini kaydet.
        }
        else{
            return  null;
        }
    }

    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);

    }
}
