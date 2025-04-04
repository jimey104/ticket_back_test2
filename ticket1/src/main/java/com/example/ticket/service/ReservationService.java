package com.example.ticket.service;

import com.example.ticket.dto.ReservationDTO;
import com.example.ticket.dto.Ticket;
import com.example.ticket.entity.Reservation;
import com.example.ticket.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;





 /**
 *    좌석 하나 마다 생성 됨
 *    좌석 복수 선택시 선택한 수 만큼 생성
 **/
    // DTO에 저장
    public ReservationDTO getReservation(Long rId) {
        Optional<Reservation> reservation = reservationRepository.findById(rId);
        return reservation.map(res -> ReservationDTO.builder()
                .uName(res.getUName())
                .uId(res.getUId())
                .rSpot(res.getRSpot())
                .rSpotStatus(res.getRSpotStatus())
                .rPhone(res.getRPhone())
                .rEmail(res.getREmail())
                .rTime(res.getRTime())  //
                .pDate(res.getPDate())
                .pTitle(res.getPTitle())
                .pPlace(res.getPPlace())
                .pPrice(res.getPPrice())
                .pId(res.getPId())
                .pAllSpot(res.getPAllSpot())
                .build()).orElse(null);
    }

//    public ReservationDTO updateReservation(String rPhone, String rEmail) {
//
//    }

    // 엔티티에 저장
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = Reservation.builder()
                .uName(reservationDTO.getUName())
                .uId(reservationDTO.getUId())
                .rSpot(reservationDTO.getRSpot())
                .rSpotStatus(reservationDTO.getRSpotStatus())
                .rPhone(reservationDTO.getRPhone())
                .rEmail(reservationDTO.getREmail())
                .rTime(LocalDateTime.now())
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
                .rSpot(savedReservation.getRSpot())
                .rSpotStatus(savedReservation.getRSpotStatus())
                .rPhone(savedReservation.getRPhone())
                .rEmail(savedReservation.getREmail())
                .rTime(savedReservation.getRTime())
                .pTitle(savedReservation.getPTitle())
                .pPlace(savedReservation.getPPlace())
                .pDate(savedReservation.getPDate())
                .pPrice(savedReservation.getPPrice())
                .pAllSpot(savedReservation.getPAllSpot())
                .pId(savedReservation.getPId())
                .build();
    }



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



    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

}