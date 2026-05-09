package in.aj.main.controller;


import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
            @Valid @RequestBody CreateBookingRequest request) {

        return bookingService.createBooking(request);
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
            @PathVariable Long id) {

        return bookingService.cancelBooking(id);
    }
}
