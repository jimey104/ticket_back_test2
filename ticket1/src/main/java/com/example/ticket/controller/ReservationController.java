package com.example.ticket.controller;

import com.example.ticket.dto.ReservationDTO;
import com.example.ticket.dto.Ticket;
import com.example.ticket.entity.Reservation;
import com.example.ticket.service.ReservationService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8787")  // CORS 해결
public class ReservationController {

    private final ReservationService reservationService;


    /**
     * 특정 공연(pId)과 사용자(uId)의 예매 목록 조회
     */
    @GetMapping("/user/{pId}/{uId}")
    public ResponseEntity<List<ReservationDTO>> getUserReservations(@PathVariable Long pId, @PathVariable Long uId) {
        return ResponseEntity.ok(reservationService.getUserReservations(pId, uId));
    }

    /**
     * 예매 생성 (여러 좌석 선택 가능)
     */
    @PostMapping("/select")
    public ResponseEntity<List<ReservationDTO>> selectSeats(@RequestBody List<ReservationDTO> reservationDTOs) {
        return ResponseEntity.ok(reservationService.selectSeats(reservationDTOs));
    }

    /**
     * 좌석 선택 시 rSpot 업데이트 (단일 좌석 선택)
     */
    @PatchMapping("/{rId}/spot")
    public ResponseEntity<ReservationDTO> updateRSpot(@PathVariable Long rId, @RequestParam String rSpot) {
        return ResponseEntity.ok(reservationService.updateRSpot(rId, rSpot));
    }

    /**
     * 예매 완료 시 rSpotStatus, rTime, rPhone, rEmail 업데이트
     */
    @PatchMapping("/{rId}/confirm")
    public ResponseEntity<ReservationDTO> confirmReservation(@PathVariable Long rId, @RequestParam String rPhone, @RequestParam String rEmail) {
        return ResponseEntity.ok(reservationService.confirmReservation(rId, rPhone, rEmail));
    }

    /**
     * 특정 예매 정보 조회
     */
    @GetMapping("/{rId}")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long rId) {
        return ResponseEntity.ok(reservationService.getReservation(rId));
    }

    /**
     * 특정 예매의 티켓 정보 조회
     */
    @GetMapping("/{rId}/ticket")
    public ResponseEntity<Ticket> getTicket(@PathVariable Long rId) {
        return ResponseEntity.ok(reservationService.getTicket(rId));
    }
}
