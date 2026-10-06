package pl.mateusz.redosz.nbp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import pl.mateusz.redosz.nbp.model.dto.NbpResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Service
public class NbpService {
    private final RestClient restClient = RestClient.create();

    public BigDecimal getUsdRate(LocalDate date) {
        var currentDate = date;

        while (!currentDate.isBefore(date.minusDays(7))) {
            try {
                return getUsdRateForDate(currentDate);
            } catch (HttpClientErrorException.NotFound e) {
                currentDate = currentDate.minusDays(1);
            }
        }

        throw new IllegalStateException(
                "USD exchange rate not found for date: " + date
        );
    }

    private BigDecimal getUsdRateForDate(LocalDate date) {
        var nbpResponse = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("api.nbp.pl")
                        .pathSegment("api", "exchangerates", "rates", "a", "usd", date.toString())
                        .queryParam("format", "json")
                        .build())
                .retrieve()
                .body(NbpResponse.class);


        return Objects.requireNonNull(nbpResponse).rates().getFirst().mid();
    }
}
