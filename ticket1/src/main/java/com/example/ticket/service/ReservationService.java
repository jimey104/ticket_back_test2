package com.example.ticket.service;

import com.example.ticket.dto.ReservationDTO;
import com.example.ticket.dto.Ticket;
import com.example.ticket.entity.Reservation;
import com.example.ticket.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;

    /**
     * 여러 좌석을 선택하고 예약 생성
     */
    public List<ReservationDTO> selectSeats(Long pId, Long uId, List<String> rSpots) {
        List<ReservationDTO> reservations = new ArrayList<>();

        for (String rSpot : rSpots) {
            Reservation reservation = Reservation.builder()
                    .uId(uId)
                    .pId(pId)
                    .rSpot(rSpot)
                    .rSpotStatus("pending")  // 선택 상태
                    .build();
            Reservation savedReservation = reservationRepository.save(reservation);

            reservations.add(ReservationDTO.builder()
                    .rId(savedReservation.getRId())
                    .uId(savedReservation.getUId())
                    .pId(savedReservation.getPId())
                    .rSpot(savedReservation.getRSpot())
                    .rSpotStatus(savedReservation.getRSpotStatus())
                    .build());
        }
        return reservations;
    }


    /**
     * 예약 생성 (좌석 선택 창에서 불러올 때 사용)
     */
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = Reservation.builder()
                .uName(reservationDTO.getUName())
                .uId(reservationDTO.getUId())
                .pTitle(reservationDTO.getPTitle())
                .pPlace(reservationDTO.getPPlace())
                .pDate(reservationDTO.getPDate())
                .pPrice(reservationDTO.getPPrice())
                .pAllSpot(reservationDTO.getPAllSpot())
                .pId(reservationDTO.getPId())
                .build();
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationDTO.builder()
                .uName(savedReservation.getUName())
                .uId(savedReservation.getUId())
                .pTitle(savedReservation.getPTitle())
                .pPlace(savedReservation.getPPlace())
                .pDate(savedReservation.getPDate())
                .pPrice(savedReservation.getPPrice())
                .pAllSpot(savedReservation.getPAllSpot())
                .pId(savedReservation.getPId())
                .build();
    }

    /**
     * 예약 정보 조회
     */
    public ReservationDTO getReservation(Long rId) {
        Optional<Reservation> reservation = reservationRepository.findById(rId);
        return reservation.map(res -> ReservationDTO.builder()
                .uName(res.getUName())
                .uId(res.getUId())
                .pDate(res.getPDate())
                .pTitle(res.getPTitle())
                .pPlace(res.getPPlace())
                .pPrice(res.getPPrice())
                .pId(res.getPId())
                .pAllSpot(res.getPAllSpot())
                .build()).orElse(null);
    }

    /**
     * 모든 예약 조회
     */
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    /**
     * 티켓 정보 조회
     */
    public Ticket getTicket(Long rId) {
        Optional<Reservation> reservation = reservationRepository.findById(rId);
        return reservation.map(res -> Ticket.builder()
                .tId(res.getRId())
                .rSpot(res.getRSpot())
                .uName(res.getUName())
                .pTitle(res.getPTitle())
                .pPlace(res.getPPlace())
                .pDate(res.getPDate())
                .uId(res.getUId())
                .build()).orElse(null);
    }

    /**
     * 좌석 선택 시 rSpot 업데이트
     */
    public ReservationDTO updateRSpot(Long rId, String rSpot) {
        Reservation reservation = reservationRepository.findById(rId).orElseThrow(() -> new RuntimeException("예약을 찾을 수 없음"));
        reservation.setRSpot(rSpot);
        Reservation updatedReservation = reservationRepository.save(reservation);
        return ReservationDTO.builder()
                .rSpot(updatedReservation.getRSpot())
                .build();
    }

    /**
     * 예매 완료 시 rSpotStatus, rTime, rPhone, rEmail 업데이트
     */
    public ReservationDTO confirmReservation(Long rId, String rPhone, String rEmail) {
        Reservation reservation = reservationRepository.findById(rId).orElseThrow(() -> new RuntimeException("예약을 찾을 수 없음"));
        reservation.setRSpotStatus("true");
        reservation.setRTime(LocalDateTime.now());
        reservation.setRPhone(rPhone);
        reservation.setREmail(rEmail);
        Reservation updatedReservation = reservationRepository.save(reservation);
        return ReservationDTO.builder()
                .rSpotStatus(updatedReservation.getRSpotStatus())
                .rTime(updatedReservation.getRTime())
                .rPhone(updatedReservation.getRPhone())
                .rEmail(updatedReservation.getREmail())
                .build();
    }
    /**
     * 특정 공연(pId)과 사용자(uId) 기준으로 예매 목록 조회
     */
    public List<ReservationDTO> getUserReservations(Long pId, Long uId) {
        List<Reservation> reservations = reservationRepository.findByPIdAndUId(pId, uId);

        if (reservations.isEmpty()) {
            return Collections.emptyList();
        }

        List<ReservationDTO> result = new ArrayList<>();
        for (Reservation res : reservations) {
            result.add(ReservationDTO.builder()
                    .rId(res.getRId())
                    .uId(res.getUId())
                    .pId(res.getPId())
                    .pTitle(res.getPTitle())
                    .pPlace(res.getPPlace())
                    .pDate(res.getPDate())
                    .pPrice(res.getPPrice())
                    .rSpot(res.getRSpot())
                    .rSpotStatus(res.getRSpotStatus())
                    .build());
        }
        return result;
    }
}
