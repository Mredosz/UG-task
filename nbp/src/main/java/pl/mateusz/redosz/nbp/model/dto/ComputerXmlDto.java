package pl.mateusz.redosz.nbp.model.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ComputerXmlDto(
        @JacksonXmlProperty(localName = "nazwa")
        String name,

        @JacksonXmlProperty(localName = "data_ksiegowania")
        String accountingDate,

        @JacksonXmlProperty(localName = "koszt_USD")
        BigDecimal usdCost,

        @JacksonXmlProperty(localName = "koszt_PLN")
        BigDecimal plnCost
) {
}