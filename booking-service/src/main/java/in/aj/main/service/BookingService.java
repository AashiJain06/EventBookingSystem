package in.aj.main.service;



import java.util.List;

import org.springframework.data.domain.Page;

import in.aj.main.dto.BookingNotificationDetails;
import in.aj.main.dto.BookingResponse;
import in.aj.main.dto.CreateBookingRequest;

public interface BookingService {

    BookingResponse createBooking(CreateBookingRequest request,Long userId,String email);

    BookingResponse getBookingById(Long id);

    Page<BookingResponse> getAllBookings(int page, int size);

    BookingResponse cancelBooking(Long id , Long userId);
    
    List<BookingResponse> getMyBooking(Long userId );
    
    BookingNotificationDetails confirmBooking(Long bookingId);
}
