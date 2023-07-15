package ru.practicum.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationRequestDto;
import ru.practicum.main.compilation.service.CompilationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class CompilationAdminController {
    private final CompilationService compilationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CompilationDto createCompilation(@RequestBody(required = false) @Valid NewCompilationDto newCompilationDto) {
        return compilationService.createCompilation(newCompilationDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCompilation(@PathVariable Long id) {
        compilationService.deleteCompilation(id);
    }

    @PatchMapping("/{id}")
    public CompilationDto updateCompilation(@PathVariable Long id, @RequestBody @Valid UpdateCompilationRequestDto updateCompilationRequestDto) {
        return compilationService.updateCompilation(id, updateCompilationRequestDto);
    }
}
