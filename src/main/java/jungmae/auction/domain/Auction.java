package jungmae.auction.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jungmae.auction.domain.dto.UserDto;
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
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성 디비에 위임 -> auto increment
    private long id;
    private String title;
    private String name;
    private String description;
    private long quantity;
    private String startPrice;
    private Timestamp createDate;
    private Timestamp endDate;
    private long resisteredUserId;
    private long winningUserId;
    private String winningUserComment;


}
