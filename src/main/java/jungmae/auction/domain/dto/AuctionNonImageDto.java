package jungmae.auction.domain.dto;

import jungmae.auction.domain.Auction;
import lombok.Data;

@Data
public class AuctionNonImageDto {

    private Long id;
    private String title;
    private String name;
    private String description;
    private Long quantity;
    private Long price;
    private String createDate;
    private String endDate;
    private Long resisteredUserId;
    private String closedAuction;

    public AuctionNonImageDto(Auction auction) {
        this.id = auction.getId();
        this.title = auction.getTitle();
        this.name = auction.getName();
        this.description = auction.getDescription();
        this.quantity = auction.getQuantity();
        this.price = auction.getPrice();
        this.createDate = auction.getCreateDate();
        this.endDate = auction.getEndDate();
        this.resisteredUserId = auction.getResisteredUserId();
        this.closedAuction = auction.getClosedAuction();
    }

    public Auction toEntity() {
        return Auction.builder()
                .id(id)
                .title(title)
                .name(name)
                .description(description)
                .quantity(quantity)
                .price(price)
                .createDate(createDate)
                .endDate(endDate)
                .resisteredUserId(resisteredUserId)
                .closedAuction(closedAuction)
                .build();
    }
}
