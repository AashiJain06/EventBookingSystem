package in.aj.main.cleint;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import in.aj.main.dto.UserDto;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users/email/{email}")
    UserDto getUserByEmail(
            @PathVariable String email
    );
}
