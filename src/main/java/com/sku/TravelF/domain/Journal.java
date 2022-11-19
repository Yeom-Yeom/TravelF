package com.sku.TravelF.domain;

import com.sku.TravelF.domain.enums.JournalType;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
@Table
public class Journal extends BaseTimeEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private JournalType journalType;

    @Column
    private String areaCode;

    @Column
    private String content;

    // 파일 이름
    @Column
    private String fileName;
    // 경로
    @Column
    private String uploadPath;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Journal(Long id, String title, JournalType journalType, String areaCode, String content, User user){
        this.id = id;
        this.title = title;
        this.journalType = journalType;
        this.areaCode = areaCode;
        this.content = content;
        this.user = user;
    }

    public void update(Journal journal){
        this.title = journal.getTitle ();
        this.journalType = journal.getJournalType ();
        this.areaCode = journal.getAreaCode ();
        this.content = journal.getContent ();
    }
}
