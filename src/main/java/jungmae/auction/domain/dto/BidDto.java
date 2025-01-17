package jungmae.auction.domain.dto;

import lombok.Data;

@Data
public class BidDto {
    private long bidUserId;
    private long bidPrice;
    private String comment;
}
