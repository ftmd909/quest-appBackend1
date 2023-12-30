package com.project.questapp.controllers;

import com.project.questapp.entities.Comment;
import com.project.questapp.request.CommentCreateRequest;
import com.project.questapp.request.CommentUpdateRequest;
import com.project.questapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;  //commentserivisine bağlandım.

    @Autowired
    public CommentController(CommentService commentService){   //commentservisi çağrıp ona bağlantı sağladım

        this.commentService=commentService;
    }

    @GetMapping
    public List<Comment> getAllComments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        return commentService.getAllCommentsWithParam(userId,postId);  //servise bu iki parametre gönderilecek
    }

    @PostMapping
    public  Comment createOneComment(@RequestBody CommentCreateRequest request){
        return commentService.createOneComment(request);
    }

    @GetMapping("/{commentId}")
    public  Comment getOneComment(@PathVariable Long commentId){
        return commentService.getOneCommentById(commentId);
    }

    @PutMapping("/{commentId}")  //spesifik bir commenti uptade etmek için
    public  Comment updateOneComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request){
        return commentService.updateOneCommentById(commentId,request);
    }
    @DeleteMapping("/{commentId}")  //spesifik bir commenti silmek etmek için
    public void deleteOneComment(@PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);

    }
}
