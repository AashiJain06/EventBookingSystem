package in.aj.main.controller;


import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.aj.main.dto.BookingResponse;
import in.aj.main.dto.CreateBookingRequest;
import in.aj.main.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse createBooking(
            @Valid @RequestBody CreateBookingRequest request, @RequestHeader("X-User-Id")
            Long userId,

            @RequestHeader("X-User-Email")
            String email) {

        return bookingService.createBooking(request , userId , email);
    }

    @GetMapping("/{id}")
    public BookingResponse getBookingById(
            @PathVariable Long id) {

        return bookingService.getBookingById(id);
    }

    @GetMapping
    public Page<BookingResponse> getAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return bookingService.getAllBookings(page, size);
    }

    @PutMapping("/{id}/cancel")
    public BookingResponse cancelBooking(
            @PathVariable Long id , @RequestHeader("X-User-Id") Long userId) {

        return bookingService.cancelBooking(id , userId);
    }
    @GetMapping("/my-bookings")
    public ResponseEntity<List<BookingResponse>>
    getMyBookings(

            @RequestHeader("X-User-Id")
            Long userId) {

        return ResponseEntity.ok(

                bookingService
                        .getMyBooking(userId)
        );
    }
}
