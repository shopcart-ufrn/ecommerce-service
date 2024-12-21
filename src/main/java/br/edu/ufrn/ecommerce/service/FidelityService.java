package br.edu.ufrn.ecommerce.service;

import br.edu.ufrn.ecommerce.dto.request.FidelityBonusRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.concurrent.*;

@Service
public class FidelityService {

    @Value("${fidelity.baseUrl}")
    private String baseUrl;

    private WebClient client;
    
    private LinkedBlockingQueue<FidelityBonusRequestDTO> queue = new LinkedBlockingQueue<FidelityBonusRequestDTO>();

    private static final Logger logger = LoggerFactory.getLogger(FidelityService.class);

    private WebClient getClient() {
        if (this.client == null) {
            this.client = WebClient.builder()
            .baseUrl(this.baseUrl)
            .build();
        }

        return this.client;
    }

    private void postBonus(
        FidelityBonusRequestDTO bonusRequestDTO
    ) throws TimeoutException {
        String endpoint = "/bonus";

        WebClient client = this.getClient();

        client
            .post()
            .uri(endpoint)
            .bodyValue(bonusRequestDTO)
            .retrieve()
            .toBodilessEntity()
            .timeout(Duration.ofSeconds(1))
            .block();
        
        logger.debug(
            String.format(
                "Bonus successfully sent to %s.",
                this.baseUrl + endpoint
            )
        );
    }

    public void sendBonusWithFaultTolerance(
        Integer user,
        Integer bonus
    ) {
        FidelityBonusRequestDTO bonusRequestDTO = new FidelityBonusRequestDTO(
            user, bonus
        );

        try {
            this.postBonus(bonusRequestDTO);

            logger.debug("Bonus successfully sent with ft enabled.");
        } catch (TimeoutException e) {
            this.queue.add(bonusRequestDTO);

            logger.error(
                "Unable to send bonus. "
                + "The request exceeded the 1s of limit. "
                + "A new request will be sent later."
            );
        }
    }

    public void sendBonusWithoutFaultTolerance(
        Integer user,
        Integer bonus
    ) throws TimeoutException {
        FidelityBonusRequestDTO bonusRequestDTO = new FidelityBonusRequestDTO(user, bonus);

        this.postBonus(bonusRequestDTO);

        logger.debug("Bonus successfully sent with ft disabled.");
    }

    @Scheduled(initialDelay = 0, fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    public void sendBonusRetry() {
        while (!queue.isEmpty()) {
            FidelityBonusRequestDTO bonusRequestDTO = this.queue.poll();

            try {
                this.postBonus(bonusRequestDTO);

                logger.debug("Bonus successfully sent from a retry call.");
            } catch (TimeoutException e) {
                this.queue.add(bonusRequestDTO);

                logger.error(
                    "Unable to send bonus from a retry call. "
                    + "The request exceeded the 1s of limit. "
                    + "The request will be send again to the queue."
                );
            }
        }
    }
}
