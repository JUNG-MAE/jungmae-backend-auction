package jungmae.auction.domain.dto;

import jungmae.auction.domain.Image;
import lombok.Data;

@Data
public class ImageDto {

    private Long id;

    private String imageUrl;

    public ImageDto(Image image) {
        this.id = image.getId();
        this.imageUrl = image.getImageUrl();
    }
}
