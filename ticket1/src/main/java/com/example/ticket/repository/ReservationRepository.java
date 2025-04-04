package com.example.ticket.repository;

import com.example.ticket.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByPIdAndUId(Long pId, Long uId);
}
