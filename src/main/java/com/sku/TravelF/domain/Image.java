package com.sku.TravelF.domain;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
@Table
public class Image extends BaseTimeEntity{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // BLOB는 바이트 저장
    //@Column
    //private byte[] img;

    // 경로
    @Column
    private String uploadPath;
    // 파일 이름
    @Column
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Journal journal;

    @Builder
    public Image(Long id, String uploadPath, String fileName){
        this.id = id;
        this.uploadPath = uploadPath;
        this.fileName = fileName;
    }

}
