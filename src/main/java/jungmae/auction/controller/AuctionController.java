package jungmae.auction.controller;



import jakarta.servlet.http.HttpServletRequest;
import jungmae.auction.domain.dto.*;
import jungmae.auction.service.AuctionService;
import jungmae.auction.service.ImageService;
import jungmae.auction.service.NaverCloudS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;
    private final NaverCloudS3Service naverCloudS3Service;
    private final ImageService imageService;

    @GetMapping("/api/image")
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

    @PostMapping("/api/auction")
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
            // NCP에 이미지 업로드 후 이미지의 저정 주소 URL값들을 받아옴.
            urls = naverCloudS3Service.uploadImages(auctionByteImageDto.getImages());
            System.out.println("urls 첫번째 = " + urls.get(0));
            // 이미지 테이블에 이미지 URL 저장
            imageService.saveAllImages(auctionNonImageDto, urls);

            return new ResponseEntity<>("경매 등록 성공!", HttpStatus.OK);
        } catch (IOException e) {
            System.out.println("경매 등록 실패");
            return new ResponseEntity<>("경매 등록 실패!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("api/auction/{id}")
    public ResponseEntity<?> findAuction(@PathVariable Long id) {

        try {
            AuctionByteImageDto auctionDto = auctionService.findAuction(id);
            return new ResponseEntity<>(auctionDto, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("id값이 유효하지 않거나 해당 경매 정보가 없습니다.");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    // 스케줄링? 시간 타이머를 사용? 해결법 모색해봐야 할 것 같다.
    @PostMapping("api/auction/{id}/close")
    public ResponseEntity<?> closeAuction(@PathVariable Long id) {

        try {
            AuctionByteImageDto auctionDto = auctionService.closeUpdateAuction(id);
            return new ResponseEntity<>("해당 경매 종료", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("id값이 유효하지 않음");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
