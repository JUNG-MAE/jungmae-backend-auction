package jungmae.auction.domain.dto;

import jungmae.auction.domain.Auction;
import jungmae.auction.domain.Image;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AuctionListDto {

    private Long id;
    private String title;
    private String name;
    private Long quantity;
    private Long price;
    private String createDate;
    private String endDate;
    private String closedAuction;
    List<ImageDto> images;

    public AuctionListDto(Auction auction) {
        this.id = auction.getId();
        this.title = auction.getTitle();
        this.name = auction.getName();
        this.quantity = auction.getQuantity();
        this.price = auction.getPrice();
        this.createDate = auction.getCreateDate();
        this.endDate = auction.getEndDate();
        this.closedAuction = auction.getClosedAuction();
        this.images = auction.getImages()
                .stream()
                .map(ImageDto::new)
                .collect(Collectors.toList());
    }
}
