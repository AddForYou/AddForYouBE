package com.example.addforyou.dto;


import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    private String token;

    private long expiredTime;

}
