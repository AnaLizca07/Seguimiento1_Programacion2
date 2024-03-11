package mapping.dtos;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Builder

public record CustomerDTO(int id, String name, String email, String password, LocalDate birthdayDate, String gender) {
}
