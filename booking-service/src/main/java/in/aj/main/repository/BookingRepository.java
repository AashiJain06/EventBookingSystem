package in.aj.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.aj.main.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
