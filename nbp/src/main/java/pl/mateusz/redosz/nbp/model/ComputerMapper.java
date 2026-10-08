package pl.mateusz.redosz.nbp.model;

import pl.mateusz.redosz.nbp.model.dto.ComputerDto;
import pl.mateusz.redosz.nbp.model.dto.ComputerSaveDto;
import pl.mateusz.redosz.nbp.model.dto.ComputerXmlDto;
import pl.mateusz.redosz.nbp.model.entity.Computer;

import java.math.BigDecimal;

public class ComputerMapper {
    private ComputerMapper() {
        /* This utility class should not be instantiated */
    }

    public static Computer toEntity(ComputerSaveDto computerSaveDto, BigDecimal plnCost){
        return Computer.builder()
                .name(computerSaveDto.name())
                .accountingDate(computerSaveDto.accountingDate())
                .usdCost(computerSaveDto.usdCost())
                .plnCost(plnCost)
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

    public static ComputerXmlDto toXmlDto(Computer computer){
        return ComputerXmlDto.builder()
                .name(computer.getName())
                .accountingDate(computer.getAccountingDate().toString())
                .usdCost(computer.getUsdCost())
                .plnCost(computer.getPlnCost())
                .build();
    }
}
