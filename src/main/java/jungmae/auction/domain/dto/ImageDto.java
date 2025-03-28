package jungmae.auction.domain.dto;

import jungmae.auction.domain.Image;
import lombok.Data;

@Data
public class ImageDto {

    private String imageUrl;

    public ImageDto(Image image) {
        this.imageUrl = image.getImageUrl();
    }
}
