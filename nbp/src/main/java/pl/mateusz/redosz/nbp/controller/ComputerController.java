package pl.mateusz.redosz.nbp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pl.mateusz.redosz.nbp.service.ComputerService;

@RestController
@RequiredArgsConstructor
public class ComputerController {
    private final ComputerService computerService;
}
