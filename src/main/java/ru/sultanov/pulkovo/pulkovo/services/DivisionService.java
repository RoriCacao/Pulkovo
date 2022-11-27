package ru.sultanov.pulkovo.pulkovo.services;

import org.springframework.stereotype.Service;
import ru.sultanov.pulkovo.pulkovo.dto.DivisionDTO;
import ru.sultanov.pulkovo.pulkovo.entity.Division;
import ru.sultanov.pulkovo.pulkovo.exceptions.DivisionNotFoundException;
import ru.sultanov.pulkovo.pulkovo.repositories.DivisionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DivisionService {

    private final DivisionRepository divisionRepository;

    public DivisionService(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    public List<Division> loadDivisionByDate(LocalDate date) {

        Optional<List<Division>> divisions = Optional.ofNullable(divisionRepository.findAllByDtFrom(date));
        if (divisions.isEmpty()) {
            throw new DivisionNotFoundException("Divisions not found");
        }
        return divisions.get();
    }

    public Division createDivision(DivisionDTO divisionDTO) {
        Division division = new Division();
        division.setName(divisionDTO.getName());
        division.setParentId(divisionDTO.getParent());
        division.setSortPriority(divisionDTO.getSortPriority());
        division.setDtTill(divisionDTO.getDtTill());
        division.setDtFrom(LocalDateTime.now());
        if (division.getParentId() == null) {
            division.setIsSystem((byte) 1);
        } else division.setIsSystem((byte) 0);
        division.setCorrectionDate(LocalDateTime.now());

        return divisionRepository.save(division);

    }

    public Division updateDivision(DivisionDTO divisionDTO, int divisionId) {
        Optional<Division> optionalDivision = divisionRepository.findById(divisionId);

        if (optionalDivision.isEmpty()) {
            throw new DivisionNotFoundException("Division not found");
        }
        Division division = optionalDivision.get();

        division.setDtTill(divisionDTO.getDtTill());
        division.setParentId(divisionDTO.getParent());
        division.setName(divisionDTO.getName());
        division.setSortPriority(divisionDTO.getSortPriority());
        if (division.getParentId() == null) {
            division.setIsSystem((byte) 1);
        } else division.setIsSystem((byte) 0);
        division.setDtTill(LocalDateTime.now());

        divisionRepository.save(division);

        Division newDivision = new Division();
        newDivision.setName(division.getName());
        newDivision.setParentId(division.getParentId());
        newDivision.setSortPriority(division.getSortPriority());
        newDivision.setDtTill(divisionDTO.getDtTill());
        newDivision.setDtFrom(division.getDtFrom());
        if (division.getParentId() == null) {
            division.setIsSystem((byte) 1);
        } else division.setIsSystem((byte) 0);
        newDivision.setCorrectionDate(LocalDateTime.now());

        return divisionRepository.save(newDivision);

    }

    public void deleteDivision(int id){
        Optional<Division> division = divisionRepository.findById(id);
        if (division.isEmpty()){
            throw new DivisionNotFoundException("Division not found");
        }
        Division division1 = division.get();
        if (division1.getChildren()==null){
            divisionRepository.delete(division1);
        }
    }


}
