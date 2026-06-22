package in.aj.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.aj.main.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	boolean existsByEventIdAndSelectedSeatsContaining(Long eventId, String seat);

	// Return Booking entities; mapping to DTO happens in the service layer
	List<Booking> findByUserId(Long userId);
}
