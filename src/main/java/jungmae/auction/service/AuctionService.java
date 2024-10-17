package jungmae.auction.service;

import jungmae.auction.domain.Auction;
import jungmae.auction.domain.dto.AuctionByteImageDto;
import jungmae.auction.domain.dto.AuctionDetailDto;
import jungmae.auction.domain.dto.AuctionImageUrlDto;
import jungmae.auction.domain.dto.AuctionNonImageDto;
import jungmae.auction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;



    // 경매 등록 데이터 저장
    public AuctionNonImageDto createAuction(AuctionByteImageDto auctionByteImageDto) {

        Auction auction = Auction.builder()
                .title(auctionByteImageDto.getTitle())
                .name(auctionByteImageDto.getName())
                .description(auctionByteImageDto.getDescription())
                .quantity(auctionByteImageDto.getQuantity())
                .startPrice(auctionByteImageDto.getStartPrice())
                .createDate(LocalDateTime.now().toString())
                .endDate(auctionByteImageDto.getEndDate())
                .resisteredUserId(auctionByteImageDto.getResisteredUserId())
                .closedAuction("NO")
                .build();

        Auction savedAuction = auctionRepository.save(auction);

        return new AuctionNonImageDto(savedAuction);
    }

    public AuctionDetailDto findAuction(Long id) {

        Auction auction = auctionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 경매 정보가 존재하지 않거나 id값이 잘못되었습니다.."));
        return new AuctionDetailDto(auction);
    }

    public AuctionByteImageDto closeUpdateAuction(Long id) {
        Auction auction = auctionRepository.findById(id).orElseThrow(()-> new NoSuchElementException("경매 정보가 존재하지 않습니다."));
        auction.updateClosedAuction("YES");
        auctionRepository.save(auction);
        return new AuctionByteImageDto(auction);
    }
}