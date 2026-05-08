package in.aj.main.service;


import java.awt.print.Pageable;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import in.aj.main.dto.CreateEventRequest;
import in.aj.main.dto.EventResponse;
import in.aj.main.dto.UpdateEventRequest;
import in.aj.main.entity.Event;
import in.aj.main.exception.ResourceNotFoundException;
import in.aj.main.repository.EventRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public EventResponse createEvent(CreateEventRequest request) {

        Event event = Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .venue(request.getVenue())
                .city(request.getCity())
                .category(request.getCategory())
                .eventDate(request.getEventDate())
                .totalSeats(request.getTotalSeats())
                .availableSeats(request.getTotalSeats())
                .ticketPrice(request.getTicketPrice())
                .build();

        Event savedEvent = eventRepository.save(event);

        return mapToResponse(savedEvent);
    }

    @Override
    public EventResponse getEventById(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found with id: " + id));

        return mapToResponse(event);
    }

    @Override
    public Page<EventResponse> getAllEvents(int page, int size) {

        PageRequest pageable = PageRequest.of(page, size);

        return eventRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public EventResponse updateEvent(Long id,
                                     UpdateEventRequest request) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found with id: " + id));

        if (request.getTitle() != null)
            event.setTitle(request.getTitle());

        if (request.getDescription() != null)
            event.setDescription(request.getDescription());

        if (request.getVenue() != null)
            event.setVenue(request.getVenue());

        if (request.getCity() != null)
            event.setCity(request.getCity());

        if (request.getCategory() != null)
            event.setCategory(request.getCategory());

        if (request.getEventDate() != null)
            event.setEventDate(request.getEventDate());

        if (request.getTotalSeats() != null)
            event.setTotalSeats(request.getTotalSeats());

        if (request.getAvailableSeats() != null)
            event.setAvailableSeats(request.getAvailableSeats());

        if (request.getTicketPrice() != null)
            event.setTicketPrice(request.getTicketPrice());

        Event updatedEvent = eventRepository.save(event);

        return mapToResponse(updatedEvent);
    }

    @Override
    public void deleteEvent(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found with id: " + id));

        eventRepository.delete(event);
    }

    private EventResponse mapToResponse(Event event) {

        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .venue(event.getVenue())
                .city(event.getCity())
                .category(event.getCategory())
                .eventDate(event.getEventDate())
                .totalSeats(event.getTotalSeats())
                .availableSeats(event.getAvailableSeats())
                .ticketPrice(event.getTicketPrice())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();
    }
}
