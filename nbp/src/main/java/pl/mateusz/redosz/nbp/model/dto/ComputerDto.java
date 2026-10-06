package pl.mateusz.redosz.nbp.model.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record ComputerDto(String name,
                          LocalDate accountingDate,
                          BigDecimal usdCost,
                          BigDecimal plnCost) {
}
