package jungmae.auction.domain.dto;

import jungmae.auction.domain.Auction;
import lombok.Data;

@Data
public class AuctionNonImageDto {

    private Long id;
    private String title;
    private String name;
    private String description;
    private long quantity;
    private String startPrice;
    private String createDate;
    private String endDate;
    private long resisteredUserId;
    private String closedAuction;

    public AuctionNonImageDto(Auction auction) {
        this.id = auction.getId();
        this.title = auction.getTitle();
        this.name = auction.getName();
        this.description = auction.getDescription();
        this.quantity = auction.getQuantity();
        this.startPrice = auction.getStartPrice();
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
                .startPrice(startPrice)
                .createDate(createDate)
                .endDate(endDate)
                .resisteredUserId(resisteredUserId)
                .closedAuction(closedAuction)
                .build();
    }
}
