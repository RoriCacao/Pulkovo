package ru.sultanov.pulkovo.pulkovo.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sultanov.pulkovo.pulkovo.config.JWTUtil;
import ru.sultanov.pulkovo.pulkovo.dto.PersonDTO;
import ru.sultanov.pulkovo.pulkovo.entity.Person;
import ru.sultanov.pulkovo.pulkovo.services.RegistrationService;
import ru.sultanov.pulkovo.pulkovo.utils.PersonValidator;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService, JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid PersonDTO personDTO,
                                                   BindingResult bindingResult) {

        Person person = convertToPerson(personDTO);

        personValidator.validate(person, bindingResult);

        //Захендлить исключение как в REST уроках
        if (bindingResult.hasErrors()) {
            return Map.of("message", "Ошибка");
        }

        registrationService.register(person);

        String token = jwtUtil.generateToken(person.getUsername());
        return Map.of("jwt-token",token);
    }

    @PostMapping("/login")
    public Map<String,Object> performLogin(@RequestBody PersonDTO authenticationDTO){
        UsernamePasswordAuthenticationToken athInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername()
                        ,authenticationDTO.getPassword());
        try {
            authenticationManager.authenticate(athInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message","Incorrect credentials");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return Map.of("jwt-token",token);
    }

    public Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }
}
