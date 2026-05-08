package in.aj.main.repository;





import org.springframework.data.jpa.repository.JpaRepository;

import in.aj.main.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
