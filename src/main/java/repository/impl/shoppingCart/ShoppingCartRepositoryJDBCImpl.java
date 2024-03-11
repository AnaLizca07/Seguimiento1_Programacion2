package repository.impl.shoppingCart;

import config.DatabaseConnection;
import model.Customer;
import model.Employee;
import model.ShoppingCart;
import repository.Repository;
import repository.impl.customer.CustomerRepositoryJDBCImpl;
import repository.impl.employee.EmployeeRepositoryJDBCImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartRepositoryJDBCImpl implements Repository<ShoppingCart> {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance();
    }
    private ShoppingCart createCart(ResultSet resultSet) throws SQLException{
        ShoppingCart shoppingCart = new ShoppingCart();
        CustomerRepositoryJDBCImpl customerRepositoryJDBC = new CustomerRepositoryJDBCImpl();
        EmployeeRepositoryJDBCImpl employeeRepositoryJDBC = new EmployeeRepositoryJDBCImpl();
        shoppingCart.setId(resultSet.getInt("id"));
        shoppingCart.setDate(resultSet.getDate("date").toLocalDate());
        int customerId = resultSet.getInt("id_Customer");
        Customer customer = customerRepositoryJDBC.byId(customerId);
        shoppingCart.setCustomer(customer);
        int employeeId = resultSet.getInt("id_Employee");
        Employee employee = employeeRepositoryJDBC.byId(employeeId);
        shoppingCart.setEmployee(employee);
        return shoppingCart;
    }
    @Override
    public List<ShoppingCart> list() {
        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        try(Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    """
                        SELECT * FROM shoppingCart
                        """
            ))
        {
            while (resultSet.next()){
                ShoppingCart shoppingCart = createCart(resultSet);
                shoppingCarts.add(shoppingCart);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return shoppingCarts;

    }
    @Override
    public ShoppingCart byId(Integer id) {
        ShoppingCart shoppingCart = null;
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                SELECT *
                        FROM shoppingCart
                        WHERE id = ?
                """
        ))
        {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                shoppingCart=createCart(resultSet);
            }
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return shoppingCart;
    }
    @Override
    public int save(ShoppingCart shoppingCart) {
        int idGenerator = 0;
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                    INSERT INTO shoppingCart (id_Customer, id_Employee, date) VALUES (?,?,?)""",
                Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setInt(1,shoppingCart.getCustomer().getId());
            preparedStatement.setInt(2,shoppingCart.getEmployee().getId());
            preparedStatement.setDate(3, Date.valueOf(shoppingCart.getDate()));
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
                    DELETE FROM shoppingCart WHERE id =?"""
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
            SELECT * FROM shoppingCart WHERE id =?"""))
        {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();//si no coincide nunguna fila, no existe
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
