package jungmae.auction.service;

import jungmae.auction.domain.Auction;
import jungmae.auction.domain.dto.*;
import jungmae.auction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
                .price(auctionByteImageDto.getStartPrice())
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

    // 경매 마감 처리
    public AuctionDetailDto closeUpdateAuction(Long id) {
        Auction auction = auctionRepository.findById(id).orElseThrow(()-> new NoSuchElementException("해당 경매 정보가 존재하지 않거나 id값이 잘못되었습니다.."));
        auction.updateClosedAuction("YES");
        Auction modifyAuction = auctionRepository.save(auction);
        return new AuctionDetailDto(modifyAuction);
    }

    // 진행중인 경매 리스트 조회
    public List<AuctionListDto> findAllOpenAuctions() {
        List<Auction> auctions = auctionRepository.findByClosedAuction("NO");

        return auctions.stream()
                .map(AuctionListDto::new)
                .collect(Collectors.toList());
    }

    // 종료된 경매 리스트 조회
    public List<AuctionListDto> findAllClosedAuctions() {
        List<Auction> auctions = auctionRepository.findByClosedAuction("YES");

        return auctions.stream()
                .map(AuctionListDto::new)
                .collect(Collectors.toList());
    }
}