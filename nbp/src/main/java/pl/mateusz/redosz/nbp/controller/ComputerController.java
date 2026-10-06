package pl.mateusz.redosz.nbp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mateusz.redosz.nbp.model.dto.ComputerDto;
import pl.mateusz.redosz.nbp.service.ComputerService;

import java.util.List;

@RestController()
@RequestMapping("/computers")
@RequiredArgsConstructor
public class ComputerController {
    private final ComputerService computerService;

    @GetMapping
    public ResponseEntity<List<ComputerDto>> getComputers(){
        return ResponseEntity.ok(computerService.getAllComputers());
    }
}
