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
    private Long quantity;
    private Long price;
    private String createDate;
    private String endDate;
    private Long resisteredUserId;
    private Long winningUserId;
    private String winningUserComment;
    private String closedAuction;
    private List<ImageDto> images;

    public AuctionDetailDto(Auction auction) {
        System.out.println("AuctionDetailDto로 치환하는 메서드 입장.");
        this.id = auction.getId();
        this.title = auction.getTitle();
        this.name = auction.getName();
        this.description = auction.getDescription();
        this.quantity = auction.getQuantity();
        this.price = auction.getPrice();
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
        System.out.println("auction 조회 이후 추가쿼리로 image 조회.");
    }
}
