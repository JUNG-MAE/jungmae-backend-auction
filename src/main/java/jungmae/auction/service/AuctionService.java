package jungmae.auction.service;

import jungmae.auction.domain.Auction;
import jungmae.auction.domain.dto.AuctionDto;
import jungmae.auction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;

    public void createAuction(AuctionDto auctionDto) {

        Auction aution = Auction.builder()
                .title(auctionDto.getTitle())
                .name(auctionDto.getName())
                .description(auctionDto.getDescription())
                .quantity(auctionDto.getQuantity())
                .startPrice(auctionDto.getStartPrice())
                .createDate(auctionDto.getCreateDate())
                .endDate(auctionDto.getEndDate())
                .resisteredUserId(auctionDto.getResisteredUserId())
                .build();

        auctionRepository.save(aution);

    }
}
/*
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
 */