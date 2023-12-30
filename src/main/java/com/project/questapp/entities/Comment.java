package com.project.questapp.entities;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table(name="comment")
@Data
public class Comment {
    @Id
    Long id;

    //bir postta birden fazla comment olabilir
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="post_id", nullable=false)
    @OnDelete(action= OnDeleteAction.CASCADE)  //bir user silinirse tüm postlarını sil
    @JsonIgnore //bu alanı ignore et yani user alanını
            Post post;


    //bir postta birden fazla user olabilir
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    @OnDelete(action=OnDeleteAction.CASCADE)  //bir user silinirse tüm commentlerini sil
    @JsonIgnore //bu alanı ignore et yani user alanını
            User user;

    @Lob
    @Column(columnDefinition="text")
    String text;



}
