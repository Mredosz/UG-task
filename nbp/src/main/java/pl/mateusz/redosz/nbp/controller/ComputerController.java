package pl.mateusz.redosz.nbp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mateusz.redosz.nbp.model.dto.ComputerDto;
import pl.mateusz.redosz.nbp.service.ComputerService;

import java.time.LocalDate;
import java.util.List;

@RestController()
@RequestMapping("/computers")
@RequiredArgsConstructor
public class ComputerController {
    private final ComputerService computerService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ComputerDto>> getComputers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDate accountingDate,
            @RequestParam(required = false) String sort
    ) {
        return ResponseEntity.ok(computerService.getAllComputers(name, accountingDate, sort));
    }
}
