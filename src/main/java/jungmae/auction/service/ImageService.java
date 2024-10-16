package jungmae.auction.service;

import jungmae.auction.domain.Auction;
import jungmae.auction.domain.Image;
import jungmae.auction.domain.dto.AuctionByteImageDto;
import jungmae.auction.domain.dto.AuctionImageUrlDto;
import jungmae.auction.domain.dto.AuctionNonImageDto;
import jungmae.auction.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public void saveAllImages(AuctionNonImageDto auctionNonImageDto, List<String> images) {

        for (String img : images) {

            Image image = new Image(img, auctionNonImageDto.toEntity());
            imageRepository.save(image);
            System.out.println("image = " + image + "   저장!!!");
        }
    }
}
