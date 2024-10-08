package jungmae.auction.domain.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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