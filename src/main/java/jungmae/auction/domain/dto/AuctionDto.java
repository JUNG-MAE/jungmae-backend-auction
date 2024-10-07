package jungmae.auction.domain.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AuctionDto {

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
