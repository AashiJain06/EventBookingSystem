package in.aj.main.service;


import in.aj.main.dto.CreateEventRequest;
import in.aj.main.dto.EventResponse;
import in.aj.main.dto.UpdateEventRequest;

import org.springframework.data.domain.Page;

public interface EventService {

    EventResponse createEvent(CreateEventRequest request);

    EventResponse getEventById(Long id);

    Page<EventResponse> getAllEvents(int page, int size);

    EventResponse updateEvent(Long id, UpdateEventRequest request);

    void deleteEvent(Long id);
}
