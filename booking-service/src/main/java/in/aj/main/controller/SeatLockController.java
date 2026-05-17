package in.aj.main.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import in.aj.main.service.SeatLockService;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class SeatLockController {

    private final SeatLockService seatLockService;

    @PostMapping("/lock/{seatId}")
    public String lockSeat(@PathVariable String seatId) {

        boolean locked = seatLockService.lockSeat(seatId);

        if (locked) {
            return "Seat locked successfully";
        }

        return "Seat already locked";
    }
}
