package ru.sultanov.pulkovo.pulkovo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sultanov.pulkovo.pulkovo.entity.Person;
import ru.sultanov.pulkovo.pulkovo.services.PersonDetailsService;

@Component
public class PersonValidator implements Validator {
    private final PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //Используем валидацию из персон детейлс сервис
        //Но лучше создать отдельный класс и прописать там метод валидации, а не использовать метод предназначенный для валидации
        //другой части проекта
        Person person = (Person) target;

        try {
            personDetailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }
        errors.rejectValue("username","","Человек с таким именем пользоватея уже существует");

    }
}
