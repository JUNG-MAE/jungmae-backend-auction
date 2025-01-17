package jungmae.auction.domain.dto;

import jungmae.auction.domain.Auction;
import jungmae.auction.domain.Image;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class AuctionImageUrlDto {

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
    private List<String> images;

    public AuctionImageUrlDto(Auction auction) {
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
        this.images = auction.getImages()   // auction 객체에 연결된 image객체의 리스트 반환
                .stream()   // 리스트를 스트림으로 변환하여 데이터 소스(배열, 리스트)에서 요소를 추출해 일련의 연산을 수행할 수 있도록 함.
                .map(Image::getImageUrl)    // map을 통해 auction 객체의 image 객체 리스트에서 값을 추출해 스트림 형태로 변환.
                .collect(Collectors.toList());  // collection으로 변환-> 그 중 List형태로 변환.
    }
    public AuctionImageUrlDto(AuctionNonImageDto auctionNonImageDto, List<String> urls) {
        this.title = auctionNonImageDto.getTitle();
        this.name = auctionNonImageDto.getName();
        this.description = auctionNonImageDto.getDescription();
        this.quantity = auctionNonImageDto.getQuantity();
        this.price = auctionNonImageDto.getPrice();
        this.createDate = auctionNonImageDto.getCreateDate();
        this.endDate = auctionNonImageDto.getEndDate();
        this.resisteredUserId = auctionNonImageDto.getResisteredUserId();
        this.closedAuction = auctionNonImageDto.getClosedAuction();
        this.images = urls;  // auction 객체에 연결된 image객체의 리스트 반환

    }

}
