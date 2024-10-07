package jungmae.auction.controller;


import jakarta.servlet.http.HttpServletRequest;
import jungmae.auction.domain.dto.AuctionDto;
import jungmae.auction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping("/api/auction")
    public ResponseEntity<?> createAuction(@RequestBody AuctionDto auctionDto) {
        System.out.println("경매 생성 컨트롤러 진앱");

        auctionService.createAuction(auctionDto);

        return new ResponseEntity<>("경매 등록 성공!", HttpStatus.OK);
    }

}
