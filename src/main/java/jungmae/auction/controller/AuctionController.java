package jungmae.auction.controller;


import jakarta.servlet.http.HttpServletRequest;
import jungmae.auction.domain.dto.AuctionDto;
import jungmae.auction.repository.AuctionRepository;
import jungmae.auction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;
    private final AuctionRepository auctionRepository;

    @PostMapping("/api/auction")
    public ResponseEntity<?> createAuction(@RequestBody AuctionDto auctionDto) {
        System.out.println("경매 생성 컨트롤러 진앱");

        auctionService.createAuction(auctionDto);

        return new ResponseEntity<>("경매 등록 성공!", HttpStatus.OK);
    }

    @GetMapping("api/auction/{id}")
    public ResponseEntity<?> deleteAuction(@PathVariable Long id) {

        try {
            AuctionDto auctionDto = new AuctionDto(auctionRepository.findById(id).get());

            return new ResponseEntity<>(auctionDto, HttpStatus.OK);
        } catch (NullPointerException e) {
            System.out.println("id값이 유효하지 않음");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}
