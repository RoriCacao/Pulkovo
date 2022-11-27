package ru.sultanov.pulkovo.pulkovo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sultanov.pulkovo.pulkovo.dto.DivisionDTO;
import ru.sultanov.pulkovo.pulkovo.entity.Division;
import ru.sultanov.pulkovo.pulkovo.facade.DivisionFacade;
import ru.sultanov.pulkovo.pulkovo.services.DivisionService;
import ru.sultanov.pulkovo.pulkovo.validations.ResponseErrorValidation;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/division")
public class DivisionController {

    private final DivisionService divisionService;
    private final DivisionFacade divisionFacade;
    private final ResponseErrorValidation responseErrorValidation;


    @Autowired
    public DivisionController(DivisionService divisionService, DivisionFacade divisionFacade, ResponseErrorValidation responseErrorValidation) {
        this.divisionService = divisionService;
        this.divisionFacade = divisionFacade;

        this.responseErrorValidation = responseErrorValidation;
    }

    @GetMapping("/getByDate")
    public ResponseEntity<List<DivisionDTO>> getDivisionsByDate(LocalDate date){
        List<DivisionDTO> divisionDTOList = divisionService.loadDivisionByDate(date)
                .stream()
                .map(divisionFacade::divisionToDivisionDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(divisionDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createDivision(@Valid @RequestBody DivisionDTO divisionDTO,
                                                 BindingResult bindingResult){

        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Division division = divisionService.createDivision(divisionDTO);
        DivisionDTO createdDivision = divisionFacade.divisionToDivisionDTO(division);

        return new ResponseEntity<>(createdDivision,HttpStatus.OK);
    }

    @PostMapping("/{divisionId}")
    public ResponseEntity<Object> updateDivision(@Valid @RequestBody DivisionDTO divisionDTO, @PathVariable("divisionId") int id) {
        Division division = divisionService.updateDivision(divisionDTO, id);
        DivisionDTO updatedDivision = divisionFacade.divisionToDivisionDTO(division);

        return new ResponseEntity<>(updatedDivision, HttpStatus.OK);
    }

    @PostMapping("/{divisionId}/delete")
    public ResponseEntity<String> deleteDivision(@PathVariable("divisionId") int id){
        divisionService.deleteDivision(id);

        return new ResponseEntity<>("Post was deleted",HttpStatus.OK);
    }

}
