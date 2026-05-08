package in.aj.main.controller;


import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import in.aj.main.dto.CreateEventRequest;
import in.aj.main.dto.EventResponse;
import in.aj.main.dto.UpdateEventRequest;
import in.aj.main.service.EventService;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse createEvent(
            @Valid @RequestBody CreateEventRequest request) {

        return eventService.createEvent(request);
    }

    @GetMapping("/{id}")
    public EventResponse getEventById(@PathVariable Long id) {

        return eventService.getEventById(id);
    }

    @GetMapping
    public Page<EventResponse> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return eventService.getAllEvents(page, size);
    }

    @PutMapping("/{id}")
    public EventResponse updateEvent(
            @PathVariable Long id,
            @RequestBody UpdateEventRequest request) {

        return eventService.updateEvent(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable Long id) {

        eventService.deleteEvent(id);
    }
}
