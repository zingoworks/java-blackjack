package com.codesquad.blackjack.domain;

import com.codesquad.blackjack.domain.player.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Lob
    private String contents;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_post_to_writer"))
    private User writer;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public Post(String title, String contents, User writer) {
        checkNotNull(title);
        checkNotNull(writer);
        checkArgument(2 <= title.length() && title.length() <= 20,
                "title length must be between 2 and 20");

        this.title = title;
        this.contents = contents;
        this.writer = writer;
    }

    public void write(Comment comment) {
        comments.add(comment);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("title", title)
                .add("contents", contents)
                .add("writer", writer)
                .toString();
    }

}