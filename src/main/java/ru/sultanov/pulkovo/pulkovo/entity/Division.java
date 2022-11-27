package ru.sultanov.pulkovo.pulkovo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "FK_PARENT_ID"))
    private Division parentId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Division> children = new ArrayList<>();

    @Column(name = "dt_from")
    private LocalDateTime dtFrom;

    @JsonFormat(pattern = "yyy-mm-dd HH:ss")
    @Column(name = "dt_till")
    private LocalDateTime dtTill;

    @Column(name = "sort_priority")
    private int sortPriority;

    @Column(name = "is_system")
    private byte isSystem;

    @JsonFormat(pattern = "yyy-mm-dd HH:ss")
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @JsonFormat(pattern = "yyy-mm-dd HH:ss")
    @Column(name = "correction_date")
    private LocalDateTime correctionDate;

}
