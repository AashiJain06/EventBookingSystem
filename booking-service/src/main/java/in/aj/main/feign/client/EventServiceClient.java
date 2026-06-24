package in.aj.main.feign.client;





import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.aj.main.dto.EventResponseDTO;



@FeignClient(name = "event-service")
public interface EventServiceClient {

    @GetMapping("/api/events/{id}")
    EventResponseDTO getEventById(@PathVariable Long id);

    @PutMapping(
            "/api/events/{eventId}/reduce-seats"
    )
    void reduceSeats(

            @PathVariable
            Long eventId,

            @RequestParam
            Integer count
    );
    @PutMapping(
            "/api/events/{eventId}/increase-seats"
    )
    void increaseSeats(

            @PathVariable
            Long eventId,

            @RequestParam
            Integer count
    );

}

