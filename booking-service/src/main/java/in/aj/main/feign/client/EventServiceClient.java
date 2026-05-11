package in.aj.main.feign.client;





import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import in.aj.main.dto.EventResponseDTO;



@FeignClient(name = "event-service")
public interface EventServiceClient {

    @GetMapping("/api/events/{id}")
    EventResponseDTO getEventById(@PathVariable Long id);
}