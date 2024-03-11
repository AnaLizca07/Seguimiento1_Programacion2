package model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Customer {
    private int id;
    private String name;
    private String email;
    private String password;
    private LocalDate birthdayDate;
    private String gender;
}
