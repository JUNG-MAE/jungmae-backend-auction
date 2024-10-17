package jungmae.auction.domain.dto;

import jungmae.auction.domain.Image;
import lombok.Data;

@Data
public class ImageDto {

    String imageUrl;

    public ImageDto(Image image) {
        this.imageUrl = image.getImageUrl();
    }
}
