package application;

import mapping.dtos.*;
import mapping.mappers.CustomerMapper;
import mapping.mappers.EmployeeMapper;
import mapping.mappers.ShoppingCartMapper;
import mapping.mappers.ToyMapper;
import model.*;
import repository.Repository;
import repository.impl.cartDetail.CartDetailRepositoryJDBCImpl;
import services.Service;
import services.ToyService;
import services.exceptions.HandleExceptions;
import services.impl.cartDetail.CartDetailServiceImpl;
import services.impl.customer.CustomerServiceImpl;
import services.impl.employee.EmployeeServiceImpl;
import services.impl.shoppingCart.ShoppingCartServiceImpl;
import services.impl.toy.ToyServiceImpl;
import services.threads.Threads;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class main {
    public static void main(String[] args) throws Exception {
        Threads threads = new Threads();
        ToyService repository = new ToyServiceImpl();
        Service<EmployeeDTO> employeeDTOService = new EmployeeServiceImpl();
        Service<CustomerDTO> customerDTOService = new CustomerServiceImpl();
        Service<ShoppingCartDTO> shoppingCartDTOService = new ShoppingCartServiceImpl();
        Service<CartDetailDTO> cartDetailDTOService = new CartDetailServiceImpl();
        String opc;

        List<ToyCategory> categories = new ArrayList<>();
        categories.add(ToyCategory.builder()
                .id(1)
                .name("Female")
                .build());
        categories.add(ToyCategory.builder()
                .id(2)
                .name("Male")
                .build());
        categories.add(ToyCategory.builder()
                .id(3)
                .name("Unisex")
                .build());

        do{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to the ToyStore");
            System.out.println("Are you an employee or customer?");
            System.out.println("1.Employee \n2.Customer \nWrite 'e' to exit");
            opc = scanner.nextLine();
            switch (opc) {
                case "1" -> {
                    HandleMenusMain.handleEmployeeMenu(scanner);
                    do {
                        System.out.println("Toy`s Store Menu for Employees");
                        System.out.println("1.List all employees");
                        System.out.println("2.Show employee by Id");
                        System.out.println("3.Add new toy");
                        System.out.println("4.Show toy`s list");
                        System.out.println("5.Show toy`s amount by type");
                        System.out.println("6.Order toys by type");
                        System.out.println("7.Show toys above a price");
                        System.out.println("8.Search toy by Id");
                        System.out.println("9.Show category with the largest stock");
                        System.out.println("10.Show category with less stock");
                        System.out.println("11.Show amount of toys available");
                        System.out.println("12.Show price of all toys");
                        System.out.println("13.Delete a toy");
                        System.out.println("14.List customers");
                        System.out.println("15.Search customer by Id");
                        System.out.println("16.Exit");
                        opc = scanner.nextLine();
                        switch (opc) {
                            case "1" -> {
                                System.out.println("List all employees");
                                employeeDTOService.list().forEach(System.out::println);
                            }
                            case "2" -> {
                                System.out.println("Show employee by Id");
                                System.out.println("Please, enter your employee`s id");
                                int idEmployee = Integer.parseInt(scanner.nextLine());
                                System.out.println(employeeDTOService.byId(idEmployee));
                            }
                            case "3" -> {
                                System.out.println("Add new toy");
                                System.out.println("Enter the toy`s name:");
                                String name = scanner.nextLine();
                                System.out.println("Select the category:");
                                categories.forEach(System.out::println);
                                int category = Integer.parseInt(scanner.nextLine());
                                System.out.println("Enter the price:");
                                Double price = Double.parseDouble(scanner.nextLine());
                                System.out.println("Enter the amount");
                                int amount = Integer.parseInt(scanner.nextLine());
                                repository.save(Toy.builder()
                                        .name(name)
                                        .category(categories.get(category))
                                        .price(price)
                                        .amount(amount)
                                        .build());
                            }
                            case "4" -> {
                                System.out.println("Toy List");
                                CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(()->{
                                    for(ToyDTO toyDTO:repository.list()){
                                        System.out.println(toyDTO);
                                        threads.sleepThread(2000);
                                    }
                                });
                                completableFuture.join();
                            }
                            case "5" -> {
                                System.out.println("Show toys amount by type");
                                System.out.println(repository.categories());
                            }
                            case "6" -> {
                                System.out.println("Order toys by type");
                                System.out.println(repository.sortByCategory());
                            }
                            case "7" -> {
                                System.out.println("Show toys above a price");
                                System.out.println("Enter the price:");
                                double price = Double.parseDouble(scanner.nextLine());
                                repository.showToysAboveOf(price).forEach(System.out::println);
                            }
                            case "8" -> {
                                System.out.println("Search toy by Id");
                                System.out.println("Enter the toy`s Id:");
                                int id = Integer.parseInt(scanner.nextLine());
                                System.out.println(repository.byId(id));
                            }
                            case "9" -> {
                                System.out.println("Show category with the largest stock");
                                System.out.println(repository.maxToy());
                            }
                            case "10" -> {
                                System.out.println("Show category with less stock");
                                System.out.println(repository.minToy());
                            }
                            case "11" -> {
                                System.out.println("Show amount of toys available");
                                System.out.println(repository.totalToys());
                            }
                            case "12" -> {
                                System.out.println("Show price of all toys");
                                System.out.println(repository.totalPriceAllToys());
                            }
                            case "13" -> {
                                System.out.println("Delete a toy");
                                System.out.println("Enter the toy`s Id");
                                Long idTot = scanner.nextLong();
                                repository.delete(idTot);
                            }
                            case "14" -> {
                                System.out.println("List customers");
                                customerDTOService.list().forEach(System.out::println);
                            }
                            case "15" -> {
                                System.out.println("Search customer by Id");
                                System.out.println("Enter the customer`s Id");
                                int idCustomer = Integer.parseInt(scanner.nextLine());
                                System.out.println(customerDTOService.byId(idCustomer));
                            }
                        }
                    }while (!opc.equals("16"));
                }
                case "2"->{
                    HandleMenusMain.handleCustomerMenu(scanner);
                    do {
                        System.out.println("Toy`s Store Menu for Customers");
                        System.out.println("1.Create a new Cart");
                        System.out.println("2.Buy a toy");
                        System.out.println("3.Show toy`s list");
                        System.out.println("4.Show toy`s amount by type");
                        System.out.println("5.Order toys by type");
                        System.out.println("6.Search toy by Id");
                        System.out.println("7.Show category with the largest stock");
                        System.out.println("8.Show category with less stock");
                        System.out.println("9.Show toys above a price");
                        System.out.println("10.Show amount of toys available");
                        System.out.println("11.View cart");
                        System.out.println("12.Exit");
                        opc = scanner.nextLine();
                        switch (opc){
                            case "1"->{
                                System.out.println("Create a new cart");
                                System.out.println("Enter your id:");
                                int idCustomer = Integer.parseInt(scanner.nextLine());
//
                                System.out.println("Employees");
                                for(EmployeeDTO employeeDTO:employeeDTOService.list()){
                                    System.out.println("Id: "+employeeDTO.id()+ "Name: "+employeeDTO.name() + "Position:"+employeeDTO.position());
                                }
                                System.out.println("Please, select the Id of the employee who helped you");
                                int idEmployee = Integer.parseInt(scanner.nextLine());
                                int idShoppingCart =
                                        shoppingCartDTOService.save(ShoppingCartDTO.builder()
                                                .idCustomer(CustomerMapper.mapFrom(customerDTOService.byId(idCustomer)))
                                                .idEmployee(EmployeeMapper.mapFrom(employeeDTOService.byId(idEmployee)))
                                                .date(LocalDate.now())
                                                .build());
                                System.out.println("Your shopping cart is is: "+idShoppingCart);
                            }
                            case "2"->{
                                System.out.println("Buy a toy");
                                System.out.println("Enter the shoppingCartId");
                                int shoppingCartId = Integer.parseInt(scanner.next());
                                System.out.println("Toys available:");
                                repository.list().forEach(System.out::println);
                                System.out.println("Please, select the Id of the toy you want to buy");
                                int idToy = Integer.parseInt(scanner.next());
                                System.out.println("How many do you want to buy?");
                                int amount = Integer.parseInt(scanner.next());
                                cartDetailDTOService.save(CartDetailDTO.builder()
                                                .amount(amount)
                                                .idToy(ToyMapper.mapFrom(repository.byId(idToy)))
                                                .idCart(ShoppingCartMapper.mapFrom(shoppingCartDTOService.byId(shoppingCartId)))
                                        .build());
                                repository.decrease(idToy,amount);

                            }
                            case "3"->{
                                System.out.println("Toy List");
                                CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(()->{
                                    for(ToyDTO toyDTO:repository.list()){
                                        System.out.println(toyDTO);
                                        threads.sleepThread(2000);
                                    }
                                });
                                completableFuture.join();
                            }
                            case "4" -> {
                                System.out.println("Show types");
                                System.out.println(repository.categories());
                            }
                            case "5" -> {
                                System.out.println("Order types");
                                System.out.println(repository.sortByCategory());
                            }
                            case "6" -> {
                                System.out.println("Search a toy by ID");
                                System.out.println("Enter the toy`s ID");
                                int idToy = Integer.parseInt(scanner.next());
                                System.out.println(repository.byId(idToy));
                            }
                            case "7"->{
                                System.out.println("Show category with the largest stock");
                                System.out.println(repository.maxToy());
                            }
                            case "8"->{
                                System.out.println("Show category with less stock");
                                System.out.println(repository.minToy());
                            }
                            case "9"->{
                                System.out.println("Show toys above a price");
                                System.out.println("Enter the price");
                                double price = Double.parseDouble(scanner.nextLine());
                                repository.showToysAboveOf(price).forEach(System.out::println);
                            }
                            case "10"->{
                                System.out.println("Show amount of toys available");
                                System.out.println(repository.totalToys());
                            }
                            case "11"->{
                                System.out.println("View cart");
                                System.out.println("Enter the cart Id");
                                int idCart = Integer.parseInt(scanner.nextLine());
                                List<CartDetailDTO> cartDetailDTOList = cartDetailDTOService.list();
                                displayCartDetails(cartDetailDTOList);

                            }

                        }
                    }while (!opc.equalsIgnoreCase("12"));
                }
            }
        }while (!opc.equalsIgnoreCase("e"));


    }
    private static void displayCartDetails(List<CartDetailDTO> cartDetailList) {
        if (cartDetailList.isEmpty()) {
            System.out.println("No hay ventas y detalles registradas.");
        } else {
            System.out.println("Lista de ventas y sus detalles:");
            for (CartDetailDTO cartDetailsDTO : cartDetailList) {
                System.out.println(cartDetailsDTO);
            }
        }
    }


}

















//
//
//        Employee employee = Employee.builder()
//                .name("Juan Carlos")
//                .position("Supervisor")
//                .admissionDate(LocalDate.of(2020,8,3))
//                .birthdayDate(LocalDate.of(1999,9,8))
//                .email("Juan@gmail.com")
//                .password("12qwq")
//                .gender("masculino")
//                .salary(12222.22)
//                .build();
//
//       int idEmployee= employeeRepository.save(employee);
//        employeeRepository.list().forEach(System.out::println);
//
//        Customer customer = Customer.builder()
//                .name("ana")
//                .birthdayDate(LocalDate.of(2005,8,19))
//                .gender("femenino")
//                .password("1234dcc")
//                .email("aliz@gmail.com")
//                .build();
//
//        int idCystomer = customerRepository.save(customer);
//        customerRepository.list().forEach(System.out::println);
//
//        Toy toy = Toy.builder()
//                .name("box")
//                .category(categories.get(2))
//                .price(122.99)
//                .amount(30)
//                .build();
//
//        int idToy = repository.save(toy);
//        repository.list().forEach(System.out::println);
//        System.out.println("el supuesto juguete");
//        System.out.println(repository.byId(idToy));
//
//
//        ShoppingCart shoppingCart = ShoppingCart.builder()
//                .date(LocalDate.of(2024, 9, 20))
//                .customer(customerRepository.byId(idCystomer))
//                .employee(employeeRepository.byId(idEmployee))
//                .build();
//
//        int idCart = shoppingCartRepository.save(shoppingCart);
//        System.out.println("SHOPPING CART"+shoppingCartRepository.byId(idCart));
//        shoppingCartRepository.list().forEach(System.out::println);
//
//        CartDetail cartDetail = CartDetail.builder()
//                .cart(shoppingCartRepository.byId(idCart))
//                .toy(repository.byId(idToy))
//                .amount(40)
//                .build();
//
//        cartDetailRepository.save(cartDetail);
//        cartDetailRepository.list().forEach(System.out::println);
