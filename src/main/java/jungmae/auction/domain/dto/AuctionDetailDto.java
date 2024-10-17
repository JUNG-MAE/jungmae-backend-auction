package jungmae.auction.domain.dto;

import jungmae.auction.domain.Auction;
import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AuctionDetailDto {

    private Long id;
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
    private List<ImageDto> images;

    public AuctionDetailDto(Auction auction) {
        this.id = auction.getId();
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
        this.images = auction.getImages()
                .stream()
                .map(ImageDto::new)
                .collect(Collectors.toList());
    }
}
