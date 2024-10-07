package jungmae.auction.repository;

import jungmae.auction.domain.Auction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuctionRepository extends JpaRepository<Auction, Long> {

}
