package jungmae.auction.domain.dto;

import jungmae.auction.domain.Auction;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuctionByteImageDto {

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
    private byte[][] images;

    public AuctionByteImageDto(Auction auction) {
        this.title = auction.getTitle();
        this.name = auction.getName();
        this.description = auction.getDescription();
        this.quantity = auction.getQuantity();
        this.startPrice = auction.getStartPrice();
        this.createDate = auction.getCreateDate();
        this.endDate = auction.getEndDate();
        this.resisteredUserId = auction.getResisteredUserId();
        this.winningUserId = auction.getWinningUserId();
        this.winningUserComment = auction.getWinningUserComment();
        this.closedAuction = auction.getClosedAuction();
    }
}
