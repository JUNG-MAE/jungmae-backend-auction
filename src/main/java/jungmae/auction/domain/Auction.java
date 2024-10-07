package jungmae.auction.domain;

import jungmae.auction.domain.dto.UserDto;

import java.sql.Timestamp;

public class Auction {

    private long id;
    private String title;
    private String name;
    private String description;
    private long quantity;
    private String startPrice;
    private Timestamp createDate;
    private Timestamp endDate;
    private long resisteredUserId;
    private long winningUserId;
    private String winningUserComment;


}
