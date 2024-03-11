package repository.impl.employee;

import config.DatabaseConnection;
import model.Employee;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryJDBCImpl implements Repository<Employee> {
    private Connection getConnection() throws SQLException{
        return DatabaseConnection.getInstance();
    }
    private Employee createEmployee(ResultSet resultSet) throws SQLException{
        Employee employee = new Employee();
        employee.setId(resultSet.getInt("id"));
        employee.setName(resultSet.getString("name"));
        employee.setPosition(resultSet.getString("position"));
        employee.setSalary(resultSet.getDouble("salary"));
        employee.setEmail(resultSet.getString("email"));
        employee.setPassword(resultSet.getString("password"));
        employee.setAdmissionDate(resultSet.getDate("admissionDate").toLocalDate());
        employee.setBirthdayDate(resultSet.getDate("birthdayDate").toLocalDate());
        employee.setGender(resultSet.getString("gender"));
        return employee;
    }
    @Override
    public List<Employee> list() {
        List<Employee> employeeList = new ArrayList<>();
        try(Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    """
                        SELECT * FROM employees"""
            ))
        {
            while(resultSet.next()){
                Employee employee = createEmployee(resultSet);
                employeeList.add(employee);
            }
        }catch (SQLException e){
                e.printStackTrace();
        }
        return employeeList;
    }

    @Override
    public Employee byId(Integer id) {
            Employee employee = null;
            try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                    """
                                                
                            SELECT * FROM employees
                                WHERE id = ?"""
            )) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    employee = createEmployee(resultSet);
                }
                resultSet.close();
            } catch (SQLException e){
e.printStackTrace();            }
        return employee;

    }

    @Override
    public int save(Employee employee) {
        int idGenerator = 0;
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                     INSERT INTO employees(name, position, salary, email, password, admissionDate, birthdayDate, gender)
                     VALUES (?,?,?,?,?,?,?,?)""",Statement.RETURN_GENERATED_KEYS
        ))
        {
            preparedStatement.setString(1,employee.getName());
            preparedStatement.setString(2,employee.getPosition());
            preparedStatement.setDouble(3,employee.getSalary());
            preparedStatement.setString(4,employee.getEmail());
            preparedStatement.setString(5,employee.getPassword());
            preparedStatement.setDate(6, Date.valueOf(employee.getAdmissionDate()));
            preparedStatement.setDate(7, Date.valueOf(employee.getBirthdayDate()));
            preparedStatement.setString(8,employee.getGender());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                idGenerator = resultSet.getInt(1);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return idGenerator;
    }

    @Override
    public void delete(Long id) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                     DELETE * FROM employees WHERE id = ?"""
        ))
        {
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Boolean verifyExist(int id) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
            SELECT * FROM employees WHERE id =?""")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            } catch (SQLException e) {
                System.out.println("The id is nooot");
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
