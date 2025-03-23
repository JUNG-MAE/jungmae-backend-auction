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
    private Long quantity;
    private Long price;
    private String createDate;
    private String endDate;
    private Long resisteredUserId;
    private byte[][] images;

    public AuctionByteImageDto(Auction auction) {
        this.title = auction.getTitle();
        this.name = auction.getName();
        this.description = auction.getDescription();
        this.quantity = auction.getQuantity();
        this.price = auction.getPrice();
        this.createDate = auction.getCreateDate();
        this.endDate = auction.getEndDate();
        this.resisteredUserId = auction.getResisteredUserId();
    }
}
