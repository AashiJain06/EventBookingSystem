package in.aj.main.service;



import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SeatLockService {

    private final StringRedisTemplate redisTemplate;

    public boolean lockSeat(String seatId) {

        String key = "seat:" + seatId;

        Boolean success = redisTemplate
                .opsForValue()
                .setIfAbsent(key, "locked", Duration.ofMinutes(5));

        return Boolean.TRUE.equals(success);
    }
}