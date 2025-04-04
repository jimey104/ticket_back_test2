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

        @PostMapping
        public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
            return ResponseEntity.ok(reservationService.createReservation(reservationDTO));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id) {
            return ResponseEntity.ok(reservationService.getReservation(id));
        }

        @GetMapping
        public ResponseEntity<List<Reservation>> getAllReservations() {
            return ResponseEntity.ok(reservationService.getAllReservations());
        }

        @GetMapping("/{id}/ticket")
        public ResponseEntity<Ticket> getTicket(@PathVariable Long id) {
            return ResponseEntity.ok(reservationService.getTicket(id));
        }
    }
