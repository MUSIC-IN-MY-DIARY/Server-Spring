package com.diary.musicinmydiaryspring.common.config;

import com.diary.musicinmydiaryspring.common.response.BaseResponse;
import com.diary.musicinmydiaryspring.common.response.BaseResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class RateLimitConfig implements HandlerInterceptor {
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException{
        if (!isBookmarkEndpoint(request)){
            return true;
        }

        String userId = request.getUserPrincipal().getName();
        Bucket bucket = buckets.computeIfAbsent(userId, k -> createNewBucket());

        if (!bucket.tryConsume(1)){
            sendErrorResponse(response);
            return false;
        }

        return true;
    }

    private void sendErrorResponse(HttpServletResponse response) throws IOException {
        BaseResponse<?> errorResponse = new BaseResponse<>(BaseResponseStatus.TOO_MANY_REQUESTS);

        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.classic(3, Refill.intervally(3, Duration.ofSeconds(10)));

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    private boolean isBookmarkEndpoint(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/bookmark") &&
                request.getMethod().equals("PUT");
    }
}
