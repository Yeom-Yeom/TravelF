package com.sku.TravelF.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
@Table
public class Tour {

    @Id
    @Column
    private Long contentid;

    @Column
    private String addr1;
    @Column
    private String addr2;
    @Column
    private String areacode;
    @Column
    private String booktour;
    @Column
    private String cat1;
    @Column
    private String cat2;
    @Column
    private String cat3;
    @Column
    private String contenttypeid;
    @Column
    private String createdtime;
    @Column
    private String firstimage;
    @Column
    private String firstimage2;
    @Column
    private String homepage;
    @Column
    private String mapx;
    @Column
    private String mapy;
    @Column
    private String mlevel;
    @Column
    private String modifiedtime;
    @Column
    private String readcount;
    @Column
    private String overview;
    @Column
    private String sigungucode;
    @Column
    private String title;
    @Column
    private String zipcode;

    @Column
    private String type;

    @Builder
    public Tour(Long contentid, String addr1, String addr2, String areacode, String booktour, String cat1, String cat2, String cat3, String contenttypeid, String createdtime, String firstimage, String firstimage2, String homepage, String mapx, String mapy, String mlevel, String modifiedtime, String readcount, String overview, String sigungucode, String title, String zipcode, String type) {
        this.contentid = contentid;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.areacode = areacode;
        this.booktour = booktour;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.cat3 = cat3;
        this.contenttypeid = contenttypeid;
        this.createdtime = createdtime;
        this.firstimage = firstimage;
        this.firstimage2 = firstimage2;
        this.homepage = homepage;
        this.mapx = mapx;
        this.mapy = mapy;
        this.mlevel = mlevel;
        this.modifiedtime = modifiedtime;
        this.readcount = readcount;
        this.overview = overview;
        this.sigungucode = sigungucode;
        this.title = title;
        this.zipcode = zipcode;
        this.type = type;
    }
    public void clear(){
        this.addr1 = "";
        this.booktour = "";
        this.areacode = "";
        this.addr2 = "";
        this.cat1 = "";
        this.cat2 = "";
        this.cat3 = "";
        this.contentid = 0L;
        this.contenttypeid = "";
        this.createdtime = "";
        this.firstimage = "";
        this.firstimage2 = "";
        this.homepage = "";
        this.mapx = "";
        this.mapy = "";
        this.mlevel = "";
        this.modifiedtime = "";
        this.overview = "";
        this.readcount = "";
        this.sigungucode = "";
        this.title = "";
        this.zipcode = "";
    }
}
/*@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
@Table
public class Tour {

    @Column
    private String addr1;
    @Column
    private String addr2;
    @Column
    private String areacode;
    @Column
    private String cat1;
    @Column
    private String cat2;
    @Column
    private String cat3;

    @Id
    @Column
    private String contentid;

    @Column
    private String contenttypeid;
    @Column
    private String createdtime;
    @Column
    private String firstimage;
    @Column
    private String firstimage2;
    @Column
    private String mapx;
    @Column
    private String mapy;
    @Column
    private String mlevel;
    @Column
    private String modifiedtime;
    @Column
    private String readcount;
    @Column
    private String sigungucode;
    @Column
    private String title;
    @Column
    private String zipcode;

    @Builder
    public Tour(String addr1, String addr2, String areacode, String cat1, String cat2, String cat3, String contentid, String contenttypeid, String createdtime,
                String firstimage, String firstimage2, String mapx, String mapy, String mlevel, String modifiedtime, String readcount, String sigungucode, String title, String zipcode){
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.areacode = areacode;
        this.cat1 = cat1;
        this.cat2 = cat2;
        this.cat3 = cat3;
        this.contentid = contentid;
        this.contenttypeid = contenttypeid;
        this.createdtime = createdtime;
        this.firstimage = firstimage;
        this.firstimage2 = firstimage2;
        this.mapx = mapx;
        this.mapy = mapy;
        this.mlevel = mlevel;
        this.modifiedtime = modifiedtime;
        this.readcount = readcount;
        this.sigungucode = sigungucode;
        this.title = title;
        this.zipcode = zipcode;
    }
    public void clear(){
        this.addr1 = "";
        this.addr2 = "";
        this.areacode = "";
        this.cat1 = "";
        this.cat2 = "";
        this.cat3 = "";
        this.contenttypeid = "";
        this.createdtime = "";
        this.firstimage = "";
        this.firstimage2 = "";
        this.mapx = "";
        this.mapy = "";
        this.mlevel = "";
        this.modifiedtime = "";
        this.readcount = "";
        this.sigungucode = "";
        this.title = "";
        this.zipcode = "";
    }
}*/
