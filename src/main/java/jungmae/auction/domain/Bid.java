package jungmae.auction.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성 디비에 위임 -> auto increment
    private Long id;
    private Long bidUserId;
    private Long bidPrice;
    private String comment;
    private Timestamp createDate;

    @ManyToOne
    @JoinColumn(name = "auction_id")    // 외래키 이름 설정.
    private Auction auction;
}
