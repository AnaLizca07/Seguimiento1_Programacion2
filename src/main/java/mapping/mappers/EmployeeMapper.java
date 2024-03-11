package mapping.mappers;

import mapping.dtos.EmployeeDTO;
import mapping.dtos.ToyDTO;
import model.Employee;

public class EmployeeMapper {
    public static Employee mapFrom(EmployeeDTO employeeDTO){
      return Employee.builder()
              .id(employeeDTO.id())
              .name(employeeDTO.name())
              .position(employeeDTO.position())
              .admissionDate(employeeDTO.admissionDate())
              .birthdayDate(employeeDTO.birthdayDate())
              .email(employeeDTO.email())
              .password(employeeDTO.password())
              .gender(employeeDTO.gender())
              .salary(employeeDTO.salary())
              .build();
    }

    public static EmployeeDTO mapFrom(model.Employee employee){
        return EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .position(employee.getPosition())
                .salary(employee.getSalary())
                .email(employee.getEmail())
                .password(employee.getPassword())
                .admissionDate(employee.getAdmissionDate())
                .birthdayDate(employee.getBirthdayDate())
                .gender(employee.getGender())
                .build();
//        return new EmployeeDTO(employee.getId(), employee.getName(), employee.getPosition(), employee.getSalary(), employee.getEmail(), employee.getPassword(),
//                employee.getAdmissionDate(), employee.getBirthdayDate(), employee.getGender());
    }
}

