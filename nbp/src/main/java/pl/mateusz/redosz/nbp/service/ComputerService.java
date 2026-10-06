package pl.mateusz.redosz.nbp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mateusz.redosz.nbp.model.ComputerMapper;
import pl.mateusz.redosz.nbp.model.dto.ComputerDto;
import pl.mateusz.redosz.nbp.model.dto.ComputerSaveDto;
import pl.mateusz.redosz.nbp.repository.ComputerRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ComputerService {
    private final ComputerRepository computerRepository;
    private final NbpService nbpService;

    public List<ComputerDto> getAllComputers(){
        return computerRepository.findAll()
                .stream()
                .map(ComputerMapper::toDto)
                .toList();

    }

    public void save(ComputerSaveDto computerSaveDto){
        var rate = nbpService.getUsdRate(computerSaveDto.accountingDate());
        var plnCost = computerSaveDto.usdCost().multiply(rate);
        var computer = ComputerMapper.toEntity(computerSaveDto, plnCost);
        computerRepository.save(computer);
    }

    public void deleteAll(){
        computerRepository.deleteAll();
    }
}
