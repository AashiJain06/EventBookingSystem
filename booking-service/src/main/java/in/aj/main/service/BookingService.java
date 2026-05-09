package in.aj.main.service;



import org.springframework.data.domain.Page;

import in.aj.main.dto.BookingResponse;
import in.aj.main.dto.CreateBookingRequest;

public interface BookingService {

    BookingResponse createBooking(CreateBookingRequest request);

    BookingResponse getBookingById(Long id);

    Page<BookingResponse> getAllBookings(int page, int size);

    BookingResponse cancelBooking(Long id);
}
