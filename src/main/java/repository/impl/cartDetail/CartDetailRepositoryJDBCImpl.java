package repository.impl.cartDetail;

import config.DatabaseConnection;
import model.*;
import repository.Repository;
import repository.impl.shoppingCart.ShoppingCartRepositoryJDBCImpl;
import repository.impl.toy.ToyRepositoryJDBCImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDetailRepositoryJDBCImpl implements Repository<CartDetail> {
    private Connection getConnection() throws SQLException{
        return DatabaseConnection.getInstance();
    }
    private CartDetail createCartDetail(ResultSet resultSet) throws SQLException {
        CartDetail cartDetail = new CartDetail();
        ShoppingCartRepositoryJDBCImpl shoppingCartRepositoryJDBC = new ShoppingCartRepositoryJDBCImpl();
        ToyRepositoryJDBCImpl toyRepositoryJDBC = new ToyRepositoryJDBCImpl();
        cartDetail.setId(resultSet.getInt("id"));
        cartDetail.setAmount(resultSet.getInt("amount"));
        int idCart = resultSet.getInt("id_Cart");
        ShoppingCart shoppingCart = shoppingCartRepositoryJDBC.byId(idCart);
        cartDetail.setCart(shoppingCart);
        int idToy = resultSet.getInt("id_Toy");
        Toy toy = toyRepositoryJDBC.byId(idToy);
        cartDetail.setToy(toy);
        return cartDetail;
    }
    @Override
    public List<CartDetail> list() {
        List<CartDetail> detailList = new ArrayList<>();
        try(Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(
                """
                      SELECT * FROM cartDetail"""
        ))
        {
            while(resultSet.next()){
                CartDetail cartDetail = createCartDetail(resultSet);
                detailList.add(cartDetail);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return detailList;
    }

    @Override
    public CartDetail byId(Integer id) {
        CartDetail cartDetail = null;
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                     SELECT cd.*
                     FROM cartDetail AS cd
                     JOIN shoppingCart AS sc ON cd.id_Cart = sc.id
                     WHERE sc.id = ?"""
        ))
        {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                cartDetail = createCartDetail(resultSet);
            }
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cartDetail;
    }

    @Override
    public int save(CartDetail cartDetail) {
        int idGenerator = 0;
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                     INSERT INTO cartDetail (id_Toy, id_Cart, amount) VALUES (?,?,?)""",
                Statement.RETURN_GENERATED_KEYS
        ))
        {
            preparedStatement.setInt(1,cartDetail.getToy().getId());
            preparedStatement.setInt(2,cartDetail.getCart().getId());
            preparedStatement.setInt(3,cartDetail.getAmount());
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
                     DELETE FROM cartDetail WHERE id = ?"""
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
            SELECT * FROM cartDetail WHERE id =?"""))
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
