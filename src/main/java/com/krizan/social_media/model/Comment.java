package com.krizan.social_media.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser author;
    @ManyToOne
    private Comment parentComment;
    private String body;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "parentComment")
    private List<Comment> childComments;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "comment")
    private List<Like> likes;
}
