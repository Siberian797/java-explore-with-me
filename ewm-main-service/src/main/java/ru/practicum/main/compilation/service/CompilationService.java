package ru.practicum.main.compilation.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationRequestDto;

import java.util.List;

public interface CompilationService {
    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    CompilationDto readCompilation(Long compilationId);

    CompilationDto updateCompilation(Long compilationId, UpdateCompilationRequestDto updateCompilationRequestDto);

    void deleteCompilation(Long compilationId);

    List<CompilationDto> getAllCompilations(Boolean pinned, PageRequest pageRequest);
}
