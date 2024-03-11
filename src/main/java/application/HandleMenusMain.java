package application;

import lombok.*;
import mapping.dtos.CustomerDTO;
import mapping.dtos.EmployeeDTO;
import services.Service;
import services.exceptions.HandleExceptions;
import services.impl.customer.CustomerServiceImpl;
import services.impl.employee.EmployeeServiceImpl;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class HandleMenusMain {
    static Service<EmployeeDTO> employeeDTOService = new EmployeeServiceImpl();
    static Service<CustomerDTO> customerDTOService = new CustomerServiceImpl();
    String opc;

    public static void handleEmployeeMenu(Scanner scanner) throws HandleExceptions {
        String opc;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                System.out.println("Employee Menu");
                System.out.println("1. New Employee");
                System.out.println("2. Existing Employee");

                opc = scanner.nextLine();
                do {
                    switch (opc) {
                        case "1":
                            System.out.println("Welcome new employee");
                            System.out.println("Enter the employee's name");
                            String name = scanner.nextLine();
                            System.out.println("Enter the employee's admission date");
                            String date = scanner.nextLine();
                            System.out.println("Enter the employee's position");
                            String position = scanner.nextLine();
                            System.out.println("Enter the employee's birthday date");
                            String birthdayDate = scanner.nextLine();
                            System.out.println("Enter the employee's email");
                            String email = scanner.nextLine();
                            System.out.println("Enter the employee's password");
                            String password = scanner.nextLine();
                            System.out.println("Enter the employee's gender");
                            String gender = scanner.nextLine();
                            LocalDate dateAdmission = LocalDate.parse(date, dateTimeFormatter);
                            LocalDate birthday = LocalDate.parse(birthdayDate, dateTimeFormatter);
                            System.out.println("Enter the employee's salary");
                            Double salary = Double.valueOf(scanner.nextLine());
                            int idNewEmployee =
                                    employeeDTOService.save(
                                            EmployeeDTO.builder()
                                                    .name(name)
                                                    .position(position)
                                                    .admissionDate(dateAdmission)
                                                    .birthdayDate(birthday)
                                                    .email(email)
                                                    .password(password)
                                                    .gender(gender)
                                                    .salary(salary)
                                                    .build()
                                    );
                            System.out.println("That is your information");
                            System.out.println(employeeDTOService.byId(idNewEmployee));
                            opc = "3";
                            break;
                        case "2":
                            System.out.println("Welcome employee");
                            System.out.println("Plese, enter your employee`s id");
                            int idEmployee = Integer.parseInt(scanner.nextLine());
                            if (employeeDTOService.verifyExist(idEmployee)) {
                                System.out.println("That is your information:");
                                System.out.println(employeeDTOService.byId(idEmployee));
                                opc = "3";
                            } else {
                                System.out.println("Please, enter a correct Id");
                            }

                            break;
                    }
                }while (!opc.equals("3"));
    }

    public static void handleCustomerMenu(Scanner scanner){
        String opc;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Customer Menu");
        System.out.println("1. New Customer");
        System.out.println("2. Existing Customer");
        opc = scanner.nextLine();
        do {
            switch (opc) {
                case "1":
                    System.out.println("Welcome new customer");
                    System.out.println("Enter the customer`s name");
                    String name = scanner.nextLine();
                    System.out.println("Enter the customer`s birthday date");
                    String birthdayDate = scanner.nextLine();
                    System.out.println("Enter the customer`s email");
                    String email = scanner.nextLine();
                    System.out.println("Enter the customer`s password");
                    String password = scanner.nextLine();
                    System.out.println("Enter the customer`s gender");
                    String gender = scanner.nextLine();
                    LocalDate birthday = LocalDate.parse(birthdayDate, dateTimeFormatter);
                    int idNewCustomer =
                            customerDTOService.save(CustomerDTO.builder()
                                    .name(name)
                                    .email(email)
                                    .password(password)
                                    .gender(gender)
                                    .birthdayDate(birthday)
                                    .build());
                    System.out.println("That is your information");
                    System.out.println(customerDTOService.byId(idNewCustomer));
                    opc ="3";
                    break;
                case "2":
                    System.out.println("Welcome customer");
                    System.out.println("Please, enter your customer`s id");
                    int idCustomer = Integer.parseInt(scanner.nextLine());
                    if(customerDTOService.verifyExist(idCustomer)) {
                        System.out.println("That is your information");
                        System.out.println(customerDTOService.byId(idCustomer));
                        opc = "3";
                    }else{
                        System.out.println("Please, enter a correct id");
                    }

                    break;
            }
        }while (!opc.equals("3"));
    }
}
