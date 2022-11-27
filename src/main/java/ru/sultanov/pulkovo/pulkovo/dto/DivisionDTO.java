package ru.sultanov.pulkovo.pulkovo.dto;

import lombok.Data;
import ru.sultanov.pulkovo.pulkovo.entity.Division;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
public class DivisionDTO {
    @NotEmpty
    private String name;

    private Division parent;

    @NotEmpty
    private int sortPriority;

    @Pattern(regexp = "yyy-mm-dd HH:ss")
    private LocalDateTime dtTill;
}
