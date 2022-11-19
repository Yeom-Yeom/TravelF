package com.sku.TravelF.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Table
public class Comment extends BaseTimeEntity{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String reply;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private Journal journal;

    @Builder
    public Comment(Long id, String reply, User user, Board board, Journal journal ){
        this.id = id;
        this.reply = reply;
        this.user = user;
        this.board = board;
        this.journal = journal;
    }

    public void update(Comment comment){
        this.reply = comment.getReply ();
    }
}
