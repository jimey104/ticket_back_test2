package com.example.ticket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationDTO {

    private Long rId;        // 예매 ID
    private String uName;    // 유저 이름
    private String uId;      // 유저 ID
    private String rSpot;    // 좌석 번호
    private String rSpotStatus; // 좌석 상태
    private String rPhone;   // 예매자의 전화번호
    private String rEmail;   // 예매자의 이메일
    private LocalDateTime rTime; // 예매 시간 (현재 시간 자동 저장)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime pDate; // 공연 날짜 및 시간
    private String pTitle;   // 공연 제목
    private String pPlace;   // 공연 장소
    private int pPrice;      // 좌석 가격
    private int pAllSpot;    // 전체 좌석

    @NonNull
    private String pId;      // 공연 ID

}
