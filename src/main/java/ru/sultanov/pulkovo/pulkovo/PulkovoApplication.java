package ru.sultanov.pulkovo.pulkovo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PulkovoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PulkovoApplication.class, args);

    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
