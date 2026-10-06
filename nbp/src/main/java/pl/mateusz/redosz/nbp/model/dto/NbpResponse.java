package pl.mateusz.redosz.nbp.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record NbpResponse(String currency,
                          String code,
                          List<Rate> rates) {
   public record Rate(LocalDate effectiveDate,
                BigDecimal mid) {
    }
}
