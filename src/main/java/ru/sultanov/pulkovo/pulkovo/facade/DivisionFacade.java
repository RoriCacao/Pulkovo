package ru.sultanov.pulkovo.pulkovo.facade;

import org.springframework.stereotype.Component;
import ru.sultanov.pulkovo.pulkovo.dto.DivisionDTO;
import ru.sultanov.pulkovo.pulkovo.entity.Division;

@Component
public class DivisionFacade {
    public DivisionDTO divisionToDivisionDTO(Division division){
        DivisionDTO divisionDTO = new DivisionDTO();
        divisionDTO.setName(division.getName());
        divisionDTO.setParent(division.getParentId());
        divisionDTO.setDtTill(division.getDtTill());
        divisionDTO.setSortPriority(division.getSortPriority());

        return divisionDTO;
    }
}
