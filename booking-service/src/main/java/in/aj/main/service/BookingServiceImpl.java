package in.aj.main.service;




import java.math.BigDecimal;



import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import in.aj.main.dto.BookingResponse;
import in.aj.main.dto.CreateBookingRequest;
import in.aj.main.dto.EventResponseDTO;
import in.aj.main.dto.UserResponseDTO;
import in.aj.main.entity.Booking;
import in.aj.main.entity.BookingStatus;
import in.aj.main.exception.ResourceNotFoundException;
import in.aj.main.feign.client.EventServiceClient;
import in.aj.main.feign.client.UserServiceClient;
import in.aj.main.repository.BookingRepository;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserServiceClient userServiceClient;
    private final EventServiceClient eventServiceClient;

    @Override
    public BookingResponse createBooking(
            CreateBookingRequest request) {
    	UserResponseDTO user =
    	        userServiceClient.getUserById(request.getUserId());
    	if(user==null)
    	{
    		throw new ResourceNotFoundException(
					"User not found with id: " + request.getUserId());
    	}

    	EventResponseDTO event =
    	        eventServiceClient.getEventById(request.getEventId());
         		if(event==null)
         		{
         			throw new ResourceNotFoundException(
        					"Event not found with id: " + request.getEventId());
         		}
        BigDecimal totalAmount =
                request.getTicketPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        request.getNumberOfTickets()
                                )
                        );

        Booking booking = Booking.builder()
                .userId(request.getUserId())
                .eventId(request.getEventId())
                .numberOfTickets(request.getNumberOfTickets())
                .ticketPrice(request.getTicketPrice())
                .totalAmount(totalAmount)
                .bookingStatus(BookingStatus.CONFIRMED)
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        return mapToResponse(savedBooking);
    }

    @Override
    public BookingResponse getBookingById(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found with id: " + id));

        return mapToResponse(booking);
    }

    @Override
    public Page<BookingResponse> getAllBookings(
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        return bookingRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public BookingResponse cancelBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found with id: " + id));

        booking.setBookingStatus(BookingStatus.CANCELLED);

        Booking updatedBooking =
                bookingRepository.save(booking);

        return mapToResponse(updatedBooking);
    }

    private BookingResponse mapToResponse(
            Booking booking) {

        return BookingResponse.builder()
                .id(booking.getId())
                .userId(booking.getUserId())
                .eventId(booking.getEventId())
                .numberOfTickets(booking.getNumberOfTickets())
                .ticketPrice(booking.getTicketPrice())
                .totalAmount(booking.getTotalAmount())
                .bookingStatus(booking.getBookingStatus())
                .bookingDate(booking.getBookingDate())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .build();
    }
}
