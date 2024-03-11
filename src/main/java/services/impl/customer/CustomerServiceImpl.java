package services.impl.customer;

import mapping.dtos.CustomerDTO;
import mapping.mappers.CustomerMapper;
import model.Customer;
import repository.Repository;
import repository.impl.customer.CustomerRepositoryJDBCImpl;
import services.Service;

import java.util.List;

public class CustomerServiceImpl implements Service<CustomerDTO> {
    private final Repository<Customer> repository;

    public CustomerServiceImpl() {
        this.repository = new CustomerRepositoryJDBCImpl();
    }


    @Override
    public List<CustomerDTO> list() {
        return repository.list().stream().map(CustomerMapper::mapFrom).toList();
    }

    @Override
    public CustomerDTO byId(Integer id) {
        Customer customer = repository.byId(id);
        return CustomerMapper.mapFrom(customer);
    }

    @Override
    public int save(CustomerDTO customerDTO) {
        return repository.save(CustomerMapper.mapFrom(customerDTO));
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public Boolean verifyExist(int id) {
        return repository.verifyExist(id);
    }
}
