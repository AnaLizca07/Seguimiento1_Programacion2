package model;

import lombok.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Employee {
    private int id;
    private String name;
    private String position;
    private Double salary;
    private String email;
    private String password;
    private LocalDate admissionDate;
    private LocalDate birthdayDate;
    private String gender;
}
