package pl.mateusz.redosz.nbp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.mateusz.redosz.nbp.exception.InvalidSortPropertyException;
import pl.mateusz.redosz.nbp.model.ComputerMapper;
import pl.mateusz.redosz.nbp.model.dto.ComputerDto;
import pl.mateusz.redosz.nbp.model.dto.ComputerSaveDto;
import pl.mateusz.redosz.nbp.repository.ComputerRepository;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ComputerService {
    private final ComputerRepository computerRepository;
    private final NbpService nbpService;
    private final XmlService xmlService;

    public List<ComputerDto> getAllComputers(String name, LocalDate accountingDate, String sort) {
        Sort sorting = Sort.unsorted();

        if (sort != null) {
            String[] parts = sort.split(",");

            String property = parts[0];

            if (!property.equals("name") && !property.equals("accountingDate")) {
                throw new InvalidSortPropertyException(property);
            }

            Sort.Direction direction = parts.length > 1
                    ? Sort.Direction.fromString(parts[1])
                    : Sort.Direction.ASC;

            sorting = Sort.by(direction, property);
        }

        return computerRepository.search(name, accountingDate, sorting)
                .stream()
                .map(ComputerMapper::toDto)
                .toList();
    }

    public void save(ComputerSaveDto computerSaveDto) {
        var rate = nbpService.getUsdRate(computerSaveDto.accountingDate());
        var plnCost = computerSaveDto.usdCost().multiply(rate);
        var computer = ComputerMapper.toEntity(computerSaveDto, plnCost);
        computerRepository.save(computer);
        xmlService.saveToXml();
    }

    public void deleteAll() {
        computerRepository.deleteAll();
    }
}
