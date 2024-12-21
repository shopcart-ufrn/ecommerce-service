package br.edu.ufrn.ecommerce.service;

import br.edu.ufrn.ecommerce.dto.request.BonusRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.concurrent.*;

@Service
public class FidelityService {

    private static final Logger logger = LoggerFactory.getLogger(FidelityService.class);

    private static final HttpClient client = HttpClient.newHttpClient();

    private TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(1)).build();

    private TimeLimiter timeLimiter = TimeLimiter.of(timeLimiterConfig);

    private BlockingQueue<HttpRequest> queue = new LinkedBlockingQueue<>();

    public void applyBonus(BonusRequestDTO bonusRequestDTO) throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(bonusRequestDTO);

        HttpRequest request = HttpRequest.newBuilder(
                        URI.create("http://localhost:8083/bonus"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        sendRequest(request);
    }

    public void sendRequest(HttpRequest request) {
        try {
            String result = timeLimiter.executeFutureSupplier(
                    () -> CompletableFuture.supplyAsync(() -> {
                        try {
                            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
            );
            logger.info("Request successfully registered with status code " + result);

        } catch (HttpTimeoutException | TimeoutException e) {
            queue.add(request);
            logger.error("Unable to obtain result, the request exceeded the 1 s limit");
        } catch (Exception e) {
            logger.error("Error when trying to process the request, please try again later", e);
        }
    }

    @Scheduled(initialDelay = 0, fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    public void processQueue() {
        while (!queue.isEmpty()) {
            HttpRequest request = queue.poll();
            sendRequest(request);
        }
    }
}
