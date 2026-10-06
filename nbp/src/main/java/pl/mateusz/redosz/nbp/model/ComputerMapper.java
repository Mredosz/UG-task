package pl.mateusz.redosz.nbp.model;

import pl.mateusz.redosz.nbp.model.dto.ComputerDto;
import pl.mateusz.redosz.nbp.model.entity.Computer;

public class ComputerMapper {
    private ComputerMapper() {
        /* This utility class should not be instantiated */
    }

    public static Computer toEntity(ComputerDto computerDto){
        return Computer.builder()
                .name(computerDto.name())
                .accountingDate(computerDto.accountingDate())
                .usdCost(computerDto.usdCost())
                .plnCost(computerDto.plnCost())
                .build();
    }

    public static ComputerDto toDto(Computer computer){
        return ComputerDto.builder()
                .name(computer.getName())
                .accountingDate(computer.getAccountingDate())
                .usdCost(computer.getUsdCost())
                .plnCost(computer.getPlnCost())
                .build();
    }
}
