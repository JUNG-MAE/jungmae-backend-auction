package jungmae.auction.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")    // 외래키 이름 설정.
    private Auction auction;

    public Image(String imageUrl, Auction auction) {
        this.imageUrl = imageUrl;
        this.auction = auction;
    }
}
