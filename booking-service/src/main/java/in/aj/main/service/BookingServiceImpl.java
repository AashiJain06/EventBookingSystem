package in.aj.main.service;




import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import in.aj.main.dto.BookingNotificationDetails;
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
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserServiceClient userServiceClient;
    private final EventServiceClient eventServiceClient;
    private final SeatLockService seatLockService;

    @Override
    @Transactional
    public BookingResponse createBooking(
            CreateBookingRequest request,Long userId ,String email) {
    	

    	EventResponseDTO event =
    	        eventServiceClient.getEventById(request.getEventId());
         		if(event==null)
         		{
         			throw new ResourceNotFoundException(
        					"Event not found with id: " + request.getEventId());
         		}
         		for (String seat : request.getSelectedSeats()) {

                    boolean alreadyBooked =
                            bookingRepository.existsByEventIdAndSelectedSeatsContaining(
                                    request.getEventId(),
                                    seat
                            );

                    if (alreadyBooked) {
                        throw new RuntimeException(
                                "Seat already booked : " + seat
                        );
                    }
                }

                // ===============================
                // STEP 2 -> LOCK SEATS IN REDIS
                // ===============================

                for (String seat : request.getSelectedSeats()) {

                    boolean locked = seatLockService.lockSeat(seat , request.getEventId());

                    if (!locked) {
                        throw new RuntimeException(
                                "Seat temporarily locked : " + seat
                        );
                    }
                }
                eventServiceClient.reduceSeats(

                        request.getEventId(),

                        request.getSelectedSeats()
                                .size()
                );
        BigDecimal totalAmount =
                event.getTicketPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        request.getSelectedSeats().size()
                                )
                        );

        Booking booking = Booking.builder()
                .userId(userId)
                .eventId(request.getEventId())
                .numberOfTickets(request.getSelectedSeats().size())
                .selectedSeats(
                        String.join(",", request.getSelectedSeats())
                )
                .ticketPrice(event.getTicketPrice())
                .totalAmount(totalAmount)
                .bookingStatus(BookingStatus.PENDING)
                .build();

        try
        {
        Booking savedBooking = bookingRepository.save(booking);
        return mapToResponse(savedBooking);
        }
		catch(Exception e)
		{
			eventServiceClient.increaseSeats(

					request.getEventId(),

					request.getSelectedSeats()
							.size()
			);
			throw new RuntimeException(
					"Booking failed: " + e.getMessage()
			);
		}

  
    }
    @Override
    @Transactional
    public BookingNotificationDetails confirmBooking(
            Long bookingId) {

        Booking booking =
                bookingRepository.findById(bookingId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Booking not found"));

        booking.setBookingStatus(
                BookingStatus.CONFIRMED
        );

        Booking savedBooking =
				bookingRepository.save(booking);
        
		UserResponseDTO user =
				userServiceClient.getUserById(
						savedBooking.getUserId());

		EventResponseDTO event =
				eventServiceClient.getEventById(
						savedBooking.getEventId());

		return BookingNotificationDetails.builder()
				.bookingId(savedBooking.getId())
				.userName(user.getName())
				.userEmail(user.getEmail())
				.eventName(event.getEventName())
				.venue(event.getVenue())
				.eventDate(event.getEventDate())
				.seats(Arrays.asList(
						savedBooking.getSelectedSeats()
								.split(",")))
				.amount(savedBooking.getTotalAmount())
				.build();
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
    @Transactional
    public BookingResponse cancelBooking(Long bookingId , Long userId) {

    	Booking booking =
                bookingRepository
                        .findById(bookingId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Booking not found"));

        if(!booking.getUserId().equals(userId)) {

            throw new RuntimeException(
                    "Access Denied");
        }

        if(booking.getBookingStatus()
                == BookingStatus.CANCELLED) {

            throw new RuntimeException(
                    "Booking already cancelled");
        }

        booking.setBookingStatus(
                BookingStatus.CANCELLED);

        List<String> seats =
                Arrays.asList(
                        booking.getSelectedSeats()
                                .split(","));

        for(String seat : seats) {

            seatLockService.unlockSeat(
                    booking.getEventId(),
                    seat);
        }
        eventServiceClient.increaseSeats(

                booking.getEventId(),

                booking.getNumberOfTickets()
        );
        Booking saved =
                bookingRepository.save(
                        booking);

        return mapToResponse(saved);
    }

    private BookingResponse mapToResponse(
            Booking booking) {

        return BookingResponse.builder()
                .id(booking.getId())
                .userId(booking.getUserId())
                .eventId(booking.getEventId())
                .numberOfTickets(booking.getNumberOfTickets())
                .selectedSeats(booking.getSelectedSeats())
                .ticketPrice(booking.getTicketPrice())
                .totalAmount(booking.getTotalAmount())
                .bookingStatus(booking.getBookingStatus())
                .bookingDate(booking.getBookingDate())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .build();
    }

	@Override
	public List<BookingResponse> getMyBooking(Long userId) {
        // bookingRepository.findByUserId(...) returns a list of Booking entities
        // so keep the variable type as List<Booking> and then map to BookingResponse
        List<Booking> bookings = bookingRepository.findByUserId(userId);

        return bookings.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
	}
}
