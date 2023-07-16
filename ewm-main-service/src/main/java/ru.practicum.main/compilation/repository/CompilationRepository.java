package ru.practicum.main.compilation.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.compilation.model.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    @EntityGraph(value = Compilation.GRAPH_EVENT, type = EntityGraph.EntityGraphType.LOAD)
    List<Compilation> findByPinned(Boolean pinned, Pageable pageable);
}
