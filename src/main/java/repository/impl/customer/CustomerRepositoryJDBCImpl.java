package repository.impl.customer;

import config.DatabaseConnection;
import model.Customer;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryJDBCImpl implements Repository<Customer> {
    private Connection getConnection() throws SQLException{
        return DatabaseConnection.getInstance();
    }
    private Customer createCustomer(ResultSet resultSet) throws SQLException{
        Customer customer = new Customer();
        customer.setId(resultSet.getInt("id"));
        customer.setName(resultSet.getString("name"));
        customer.setEmail(resultSet.getString("email"));
        customer.setPassword(resultSet.getString("password"));
        customer.setBirthdayDate(resultSet.getDate("birthdayDate").toLocalDate());
        customer.setGender(resultSet.getString("gender"));
        return customer;
    }
    @Override
    public List<Customer> list() {
        List<Customer> customerList = new ArrayList<>();
        try(Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    """
                        SELECT  * FROM customers"""
            )
        )
        {
            while (resultSet.next()){
                Customer customer = createCustomer(resultSet);
                customerList.add(customer);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return customerList;
    }

    @Override
    public Customer byId(Integer id) {
        Customer customer = null;
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                    SELECT * FROM customers
                    WHERE id = ?"""
        ))
        {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                customer = createCustomer(resultSet);
            }
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public int save(Customer customer) {
        int idGenerator = 0;
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                    INSERT INTO customers (name,email,password,birthdayDate,gender)
                    VALUES(?,?,?,?,?) """,Statement.RETURN_GENERATED_KEYS
        ))
        {
            preparedStatement.setString(1,customer.getName());
            preparedStatement.setString(2,customer.getEmail());
            preparedStatement.setString(3,customer.getPassword());
            preparedStatement.setDate(4, Date.valueOf(customer.getBirthdayDate()));
            preparedStatement.setString(5,customer.getGender());
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
                     DELETE * FROM customers 
                     WHERE id = ?"""
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
            SELECT * FROM customers WHERE id =?"""))
        {
            preparedStatement.setInt(1,id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                return resultSet.next();
            }catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
