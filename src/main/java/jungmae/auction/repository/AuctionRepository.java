package jungmae.auction.repository;

import jungmae.auction.domain.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findByClosedAuction(String closedAuction);
}
