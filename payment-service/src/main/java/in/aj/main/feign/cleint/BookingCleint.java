package in.aj.main.feign.cleint;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import in.aj.main.dto.BookingResponse;

@FeignClient(name = "booking-service")
public interface BookingCleint {
	
	@GetMapping(
            "/api/bookings/{id}"
    )
    BookingResponse getBooking(
            @PathVariable Long id
    );

    @PutMapping(
            "/api/bookings/{id}/confirm"
    )
    BookingResponse confirmBooking(
            @PathVariable Long id
    );

    @PutMapping(
            "/api/bookings/{id}/cancel"
    )
    BookingResponse cancelBooking(
            @PathVariable Long id
    );
}
