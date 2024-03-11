package mapping.mappers;

import mapping.dtos.CustomerDTO;
import model.Customer;

public class CustomerMapper {
    public static Customer mapFrom(CustomerDTO customerDTO){
        return Customer.builder()
                .id(customerDTO.id())
                .name(customerDTO.name())
                .email(customerDTO.email())
                .password(customerDTO.password())
                .birthdayDate(customerDTO.birthdayDate())
                .gender(customerDTO.gender())
                .build();
//        return new model.Curtomer(customerDTO.id(), customerDTO.name(), customerDTO.password(),customerDTO.birthdayDate(),
//                customerDTO.gender());
    }

    public static CustomerDTO mapFrom(Customer curtomer) {
        return CustomerDTO.builder()
                .id(curtomer.getId())
                .name(curtomer.getName())
                .email(curtomer.getEmail())
                .password(curtomer.getPassword())
                .birthdayDate(curtomer.getBirthdayDate())
                .gender(curtomer.getGender())
                .build();
//        return new CustomerDTO(curtomer.getId(), curtomer.getName(), curtomer.getPassword(), curtomer.getBirthdayDate(),curtomer.getGender());
    }
}

