package com.example.demo;

import com.example.ticket.dto.ReservationDTO;
import com.example.ticket.entity.Reservation;
import com.example.ticket.repository.ReservationRepository;
import com.example.ticket.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("✅ 예약 생성 시 pId 추적")
    void testCreateReservation_pId_추적() {
        // Given
        ReservationDTO testDTO = ReservationDTO.builder()
                .uName("홍길동")
                .uId("user123")
                .rSpot("A1")
                .rSpotStatus("reserved")
                .rPhone("010-1234-5678")
                .rEmail("hong@example.com")
                .pDate(LocalDateTime.parse("2025-05-01T19:30:00"))
                .rTime(LocalDateTime.now())
                .pTitle("뮤지컬 영웅")
                .pPlace("세종문화회관")
                .pPrice(80000)
                .pAllSpot(100)
                .pId("play123") // ✅ pId 설정
                .build();

        System.out.println("🔍 [Step 1] DTO 생성됨, pId = " + testDTO.getPId());

        // Mock: save()가 호출될 때 동작 정의
        when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(invocation -> {
                    Reservation reservation = invocation.getArgument(0);
                    System.out.println("💾 [Step 2] save() 호출 직전, 엔티티의 pId = " + reservation.getPId());
                    return reservation;
                });

        // When
        ReservationDTO savedDTO = reservationService.createReservation(testDTO);

        // Then
        System.out.println("✅ [Step 3] 서비스 리턴 값: DTO의 pId = " + savedDTO.getPId());

        System.out.println(testDTO);
        // Assertions
        assertNotNull(savedDTO, "저장된 DTO는 null이 아니어야 함");
        assertNotNull(savedDTO.getPId(), "pId는 null이 아니어야 함");
        assertEquals(testDTO.getPId(), savedDTO.getPId(), "DTO의 pId가 일치해야 함");

        // Verify
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }
}
