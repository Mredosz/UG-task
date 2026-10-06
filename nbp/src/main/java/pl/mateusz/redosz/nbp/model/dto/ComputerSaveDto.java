package pl.mateusz.redosz.nbp.model.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record ComputerSaveDto(String name,
                              LocalDate accountingDate,
                              BigDecimal usdCost) {
}
