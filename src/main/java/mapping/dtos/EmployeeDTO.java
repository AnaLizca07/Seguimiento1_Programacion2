package mapping.dtos;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Builder

public record EmployeeDTO(int id, String name, String position, Double salary, String email, String password,
                          LocalDate admissionDate, LocalDate birthdayDate, String gender) {
}
