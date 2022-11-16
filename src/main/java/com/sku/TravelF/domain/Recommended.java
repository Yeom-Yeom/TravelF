package com.sku.TravelF.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
@Table
public class Recommended {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String file_name;
    @Column
    private String hashtag;
    @Column
    private String area;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getHashtag() {
        return hashtag;
    }

    public String getArea() {
        return area;
    }
}
