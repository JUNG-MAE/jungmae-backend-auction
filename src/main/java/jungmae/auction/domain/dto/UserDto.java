package jungmae.auction.domain.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDto {
    private String username;
    private String name;
    private String password;
    private String email;
    private String role;

    private String provider;
    private Timestamp createDate;

}