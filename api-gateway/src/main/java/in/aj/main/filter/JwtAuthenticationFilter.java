package in.aj.main.filter;


import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
        implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        String path =
                exchange.getRequest()
                        .getURI()
                        .getPath();

        // =========================
        // PUBLIC APIs
        // =========================

        if(path.contains("/auth/login")) {
            return chain.filter(exchange);
        }

        // =========================
        // GET AUTH HEADER
        // =========================

        String authHeader =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION);

        // =========================
        // HEADER VALIDATION
        // =========================

        if(authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            throw new RuntimeException(
                    "Missing Authorization Header");
        }

        // =========================
        // EXTRACT TOKEN
        // =========================

        String token =
                authHeader.substring(7);

        // =========================
        // VALIDATE TOKEN
        // =========================

        jwtUtil.validateToken(token);

        // =========================
        // EXTRACT ROLE
        // =========================

        String role =
                jwtUtil.extractRole(token);

        // =========================
        // ADMIN ONLY APIs
        // =========================

        if(path.contains("/api/events/create")
                || path.contains("/api/events/delete")) {

            if(!role.equals("ADMIN")) {

                throw new RuntimeException(
                        "Access Denied");
            }
        }

        // =========================
        // USER + ADMIN APIs
        // =========================

        if(path.contains("/api/bookings")) {

            if(!(role.equals("USER")
                    || role.equals("ADMIN"))) {

                throw new RuntimeException(
                        "Access Denied");
            }
        }

        // FORWARD REQUEST
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
