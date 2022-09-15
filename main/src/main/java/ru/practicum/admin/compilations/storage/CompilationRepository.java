package ru.practicum.admin.compilations.storage;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.admin.compilations.model.Compilation;

import java.util.Collection;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
    Collection<Compilation> findByPinned(Boolean pinned, PageRequest of);
}
