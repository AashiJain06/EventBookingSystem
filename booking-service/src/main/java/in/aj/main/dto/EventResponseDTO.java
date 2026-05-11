package in.aj.main.dto;



import java.time.LocalDateTime;

public class EventResponseDTO {

    private Long id;
    private String eventName;
    private String venue;
    private LocalDateTime eventDate;

    public EventResponseDTO() {
    }

    public EventResponseDTO(Long id, String eventName,
                            String venue,
                            LocalDateTime eventDate) {
        this.id = id;
        this.eventName = eventName;
        this.venue = venue;
        this.eventDate = eventDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }
}
