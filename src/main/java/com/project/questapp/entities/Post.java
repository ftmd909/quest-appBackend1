package com.project.questapp.entities;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="post")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch=FetchType.LAZY)  //user objesini dbden hemen çekme,postu çektiğimde ilgili user objesi gelmesin
    @JoinColumn(name="user_id", nullable=false)  //dbdeki user_id ile joinledim
    @OnDelete(action= OnDeleteAction.CASCADE)  //bir user silinirse tüm postlarını sil
    @JsonIgnore //bu alanı ignore et yani user alanını
    User user;


    String title;
    @Lob
    @Column(columnDefinition="text")
    String text;

}
