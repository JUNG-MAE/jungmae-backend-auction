package jungmae.auction.domain;

import jakarta.persistence.*;
import jungmae.auction.domain.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Array;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성 디비에 위임 -> auto increment
    private long id;
    private String title;
    private String name;
    private String description;
    private long quantity;
    private String startPrice;
    private String createDate;
    private String endDate;
    private long resisteredUserId;
    private long winningUserId;
    private String winningUserComment;
    private String closedAuction;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    public void updateClosedAuction(String str) {
        closedAuction = str;
    }
}
