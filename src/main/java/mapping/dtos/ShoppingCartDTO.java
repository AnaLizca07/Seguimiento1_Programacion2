package mapping.dtos;

import lombok.Builder;
import model.Customer;
import model.Employee;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Builder
public record ShoppingCartDTO(int id, Customer idCustomer, Employee idEmployee, LocalDate date) {
}