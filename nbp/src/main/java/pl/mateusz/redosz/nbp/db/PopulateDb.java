package pl.mateusz.redosz.nbp.db;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mateusz.redosz.nbp.model.dto.ComputerSaveDto;
import pl.mateusz.redosz.nbp.service.ComputerService;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class PopulateDb {
    private final ComputerService computerService;

    @Bean
    CommandLineRunner initComputers() {
        return args -> {
            computerService.deleteAll();

            var computer1 = new ComputerSaveDto(
                    "ACER Aspire",
                    LocalDate.of(2026, 7, 3),
                    BigDecimal.valueOf(345)
            );

            var computer2 = new ComputerSaveDto(
                    "DELL Latitude",
                    LocalDate.of(2026, 7, 12),
                    BigDecimal.valueOf(543)
            );

            var computer3 = new ComputerSaveDto(
                    "HP Victus",
                    LocalDate.of(2026, 7, 15),
                    BigDecimal.valueOf(346)
            );

            computerService.save(computer1);
            computerService.save(computer2);
            computerService.save(computer3);
        };
    }
}
