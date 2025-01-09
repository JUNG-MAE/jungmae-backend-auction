package jungmae.auction.controller;



import jakarta.servlet.http.HttpServletRequest;
import jungmae.auction.domain.Auction;
import jungmae.auction.domain.dto.*;
import jungmae.auction.service.AuctionService;
import jungmae.auction.service.AwsS3Service;
import jungmae.auction.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;
    private final AwsS3Service awsS3Service;
    private final ImageService imageService;

    // 이미지 저장 경로를 사용해 image를 byte[]로 변환
    // 백엔드 서버에서 image를 byte화 하여 테스트 하기위해 구현.
    @GetMapping("/image")
    public ResponseEntity<?> transImageToByte(@RequestBody ImagePathDto imagePathDto) {

        ImageByteDto byteImageDto = null;

        try {
            byte[][] imageBytes = new byte[imagePathDto.getImagePaths().length][];
            for (int i = 0; i < imagePathDto.getImagePaths().length; i++) {
                Path path = Paths.get(imagePathDto.getImagePaths()[i]);
                imageBytes[i] = Files.readAllBytes(path);
                System.out.println(i + "번째 이미지의 바이트 길이 : " + imageBytes[i].length);
            }
            byteImageDto = new ImageByteDto(imageBytes);
            return new ResponseEntity<>(byteImageDto, HttpStatus.OK);
        } catch (IOException e1) {
            e1.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 경매 등록 및 경매 물품 사진 NCP에 업로드 후 저장 URL을 Image 디비에 저장
    // byte화 된 이미지 데이터가 Dto에 함께 들어온다.
    @PostMapping("/auction")
    public ResponseEntity<?> createAuction(@RequestBody AuctionByteImageDto auctionByteImageDto) {
        System.out.println("경매 생성 컨트롤러 진입");
        List<String> urls = new ArrayList<>();
        AuctionImageUrlDto auctionImageUrlDto;

        // 이미지가 오지 않았을때 예외처리
        if (auctionByteImageDto.getImages() == null || auctionByteImageDto.getImages().length == 0) {
            return new ResponseEntity<>("이미지 정보가 없습니다.",HttpStatus.BAD_REQUEST);
        }

        // 이미지 업로드 오류 및 서버오류처리
        try {
            // 경매 등록 데이터를 경매 DB에 저장.
            AuctionNonImageDto auctionNonImageDto = auctionService.createAuction(auctionByteImageDto);
            // 경매 Id값 + 물품이름으로 S3에 저장할 폴더이름 설정.
            String folderName = auctionNonImageDto.getId().toString()+"_"+auctionNonImageDto.getName();
            // NCP에 이미지 업로드 후 이미지의 저정 주소 URL값들을 받아옴.
            urls = awsS3Service.uploadImages(auctionByteImageDto.getImages(),folderName);
            System.out.println("urls 첫번째 = " + urls.get(0));
            // 이미지 테이블에 이미지 URL 저장
            imageService.saveAllImages(auctionNonImageDto, urls);

            return new ResponseEntity<>("경매 등록 성공!", HttpStatus.OK);
        } catch (IOException e) {
            System.out.println("경매 등록 실패");
            return new ResponseEntity<>("경매 등록 실패!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // id로 경매 조회
    @GetMapping("/auction/{id}")
    public ResponseEntity<?> findAuction(@PathVariable Long id) {

        System.out.println(id + " 번 경매의 자세한 데이터를 얻기위한 호출");
        try {
            AuctionDetailDto auctionDetailDto = auctionService.findAuction(id);
            return new ResponseEntity<>(auctionDetailDto, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("id값이 유효하지 않거나 해당 경매 정보가 없습니다.");
            return new ResponseEntity<>("실패!", HttpStatus.UNAUTHORIZED);
        }
    }


    // 스케줄링? 시간 타이머를 사용? 해결법 모색해봐야 할 것 같다.
    // -> 프론트 측에서 타이머를 통해 시간이 되면 신호를 주기로 합의함.

    // 경매 시간 마감 -> Auction 필드 closedAuction을 YES로 변경.
    @PatchMapping("/auction/close/{id}")
    public ResponseEntity<?> closeAuction(@PathVariable Long id) {

        try {
            AuctionDetailDto auctionDetailDto = auctionService.closeUpdateAuction(id);
            return new ResponseEntity<>(auctionDetailDto, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("id값이 유효하지 않음");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    // 진행중인 경매 리스트 조회
    @GetMapping("/auction/list/open")
    public ResponseEntity<?> findAllOpenAuction() {
        try {
            List<AuctionListDto> auctions = auctionService.findAllOpenAuctions();
            return new ResponseEntity<>(auctions, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("경매 리스트 조회 오류");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 종료된 경매 리스트 조회
    @GetMapping("/auction/list/closed")
    public ResponseEntity<?> findAllClosedAuction() {
        try {
            List<AuctionListDto> auctions = auctionService.findAllClosedAuctions();
            return new ResponseEntity<>(auctions, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("경매 리스트 조회 오류");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @MessageMapping("/bid")
    @SendTo("/topic/highestBid")
    public ResponseEntity<?> updateBid(@PathVariable Long id, @RequestBody BidDto bidDto){

    }


}
