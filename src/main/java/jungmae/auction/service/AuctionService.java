package jungmae.auction.service;

import jakarta.transaction.Transactional;
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
    @Transactional
    public AuctionNonImageDto createAuction(AuctionByteImageDto auctionByteImageDto) {

        Auction auction = Auction.builder()
                .title(auctionByteImageDto.getTitle())
                .name(auctionByteImageDto.getName())
                .description(auctionByteImageDto.getDescription())
                .quantity(auctionByteImageDto.getQuantity())
                .price(auctionByteImageDto.getPrice())
                .createDate(LocalDateTime.now().toString())
                .endDate(auctionByteImageDto.getEndDate())
                .resisteredUserId(auctionByteImageDto.getResisteredUserId())
                .closedAuction("NO")
                .build();

        Auction savedAuction = auctionRepository.save(auction);

        System.out.println("경매 생성 디비 적용 완료");

        return new AuctionNonImageDto(savedAuction);
    }
    @Transactional
    public AuctionDetailDto findAuction(Long id) {
        System.out.println("findAuction 메서드 진입.");
        Auction auction = auctionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 경매 정보가 존재하지 않거나 id값이 잘못되었습니다.."));
        System.out.println("findAuction 메서드에서 경매 조회 성공.");
        return new AuctionDetailDto(auction);
    }

    // 경매 마감 처리
    @Transactional
    public AuctionDetailDto closeUpdateAuction(Long id) {
        Auction auction = auctionRepository.findById(id).orElseThrow(()-> new NoSuchElementException("해당 경매 정보가 존재하지 않거나 id값이 잘못되었습니다.."));
        auction.updateClosedAuction("YES");
        Auction modifyAuction = auctionRepository.save(auction);
        return new AuctionDetailDto(modifyAuction);
    }

    // 진행중인 경매 리스트 조회
    @Transactional
    public List<AuctionListDto> findAllOpenAuctions() {
        List<Auction> auctions = auctionRepository.findByClosedAuction("NO");

        System.out.println("fetch join TEST");
        for (Auction auction : auctions) {
            System.out.println("auction.name= "+auction.getName()+ "  images count= " + auction.getImages().size());
        }

        return auctions.stream()
                .map(AuctionListDto::new)
                .collect(Collectors.toList());
    }

    // 종료된 경매 리스트 조회
    @Transactional
    public List<AuctionListDto> findAllClosedAuctions() {
        List<Auction> auctions = auctionRepository.findByClosedAuction("YES");

        return auctions.stream()
                .map(AuctionListDto::new)
                .collect(Collectors.toList());
    }

    // 경매 입찰 처리
    @Transactional
    public AuctionDetailDto bidUpdateAuctions(Long id, BidDto bidDto) {
        Auction auction = auctionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 경매 정보가 존재하지 않거나 id값이 잘못되었습니다."));
        auction.updateWinningAuction(bidDto);
        Auction modifyAuction = auctionRepository.save(auction);
        return new AuctionDetailDto(modifyAuction);
    }
}