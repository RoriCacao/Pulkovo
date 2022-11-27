package ru.sultanov.pulkovo.pulkovo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sultanov.pulkovo.pulkovo.entity.Division;

import java.time.LocalDate;
import java.util.List;

public interface DivisionRepository extends JpaRepository<Division, Integer> {
    public List<Division> findAllByDtFrom(LocalDate date);
}
