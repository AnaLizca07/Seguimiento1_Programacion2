package services.impl.employee;

import mapping.dtos.EmployeeDTO;
import mapping.mappers.EmployeeMapper;
import model.Employee;
import model.Toy;
import repository.Repository;
import repository.ToyRepository;
import repository.impl.employee.EmployeeRepositoryJDBCImpl;
import repository.impl.toy.ToyRepositoryJDBCImpl;
import services.Service;

import java.util.List;

public class EmployeeServiceImpl implements Service<EmployeeDTO> {
    private final Repository<Employee> repository;

    public EmployeeServiceImpl() {
        this.repository = new EmployeeRepositoryJDBCImpl() {
        };
    }



    @Override
    public List<EmployeeDTO> list() {
        return repository.list().stream().map(EmployeeMapper::mapFrom).toList();
    }

    @Override
    public EmployeeDTO byId(Integer id) {
        Employee employee = repository.byId(id);
        return EmployeeMapper.mapFrom(employee);
    }

    @Override
    public int save(EmployeeDTO employeeDTO) {
        return repository.save(EmployeeMapper.mapFrom(employeeDTO));
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
