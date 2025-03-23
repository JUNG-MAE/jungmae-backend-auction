package jungmae.auction.repository;

import jungmae.auction.domain.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AuctionRepository extends JpaRepository<Auction, Long> {
    @Query("select a from Auction a join fetch a.images")
    List<Auction> findByClosedAuction(String closedAuction);
}
