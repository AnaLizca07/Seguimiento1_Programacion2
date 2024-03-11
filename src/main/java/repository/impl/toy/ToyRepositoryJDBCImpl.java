package repository.impl.toy;

import config.DatabaseConnection;
import model.Toy;
import model.ToyCategory;
import repository.ToyRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToyRepositoryJDBCImpl implements ToyRepository {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance();
    }

    private Toy createToy(ResultSet resultSet) throws SQLException{
        Toy toy = new Toy();
        toy.setId(resultSet.getInt("id"));
        toy.setName(resultSet.getString("name"));
        toy.setAmount(resultSet.getInt("amount"));
        toy.setPrice(resultSet.getDouble("price"));
        toy.setCategory(new ToyCategory(resultSet.getInt("id_Category"),resultSet.getString("category_Name")));
//        toy.setCategory(new ToyCategory(resultSet.getInt("id"),resultSet.getString("name")));
        return toy;
    }

    @Override
    public List<Toy> list() {
        List<Toy> toyList = new ArrayList<>();
        try(Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("""
                                   SELECT t.*, c.name AS category_Name, c.id AS category_Id 
                                   FROM toys AS t 
                                   INNER JOIN toyCategories AS c ON t.id_Category = c.id"""))
        {
            while (resultSet.next()){
                Toy toy = createToy(resultSet);
                toyList.add(toy);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return toyList;
    }

    @Override
    public Toy byId(Integer id) {
        Toy toy = null;
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                    SELECT t.*, c.name AS category_Name, c.id AS categoryId
                    FROM toys AS t
                    INNER JOIN toyCategories AS c ON t.id_Category = c.id
                    WHERE t.id = ?"""
        ))
        {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                toy = createToy(resultSet);
            }
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return toy;
    }

    @Override
    public int save(Toy toy) {
        int generatedId = 0;
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                     INSERT INTO toys (name, id_Category, price, amount ) VALUES (?,?,?,?)""",Statement.RETURN_GENERATED_KEYS
        ))
        {
            preparedStatement.setString(1,toy.getName());
            preparedStatement.setInt(2,toy.getCategory().getId());
            preparedStatement.setDouble(3,toy.getPrice());
            preparedStatement.setInt(4,toy.getAmount());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                generatedId = resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return generatedId;
    }

    @Override
    public void delete(Long id) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(
                """ 
                     DELETE FROM toys WHERE id = ?"""
        ))
        {
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public ToyCategory maxToy() throws Exception {
        ToyCategory maxCategory=null;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                SELECT  c.name AS category_Name, c.id AS category_Id
                FROM toyCategories AS c
                INNER JOIN (
                SELECT id_Category, COUNT(*) AS category_Count
                FROM toys
                GROUP BY id_Category
                ORDER BY COUNT(*) DESC 
                LIMIT 1)
                AS counted_Categories ON c.id = counted_Categories.id_Category;
              
                """)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                maxCategory = new ToyCategory();
                maxCategory.setId(resultSet.getInt("category_Id"));
                maxCategory.setName(resultSet.getString("category_Name"));
            }
            resultSet.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return maxCategory;
    }

    @Override
    public ToyCategory minToy() throws Exception {
        ToyCategory minCategory=null;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                SELECT  c.name AS category_Name, c.id AS category_Id
                FROM toyCategories AS c
                INNER JOIN (
                SELECT id_Category, COUNT(*) AS category_Count
                FROM toys
                GROUP BY id_Category
                ORDER BY COUNT(*) ASC 
                LIMIT 1)
                AS counted_Categories ON c.id = counted_Categories.id_Category;
                """)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                minCategory = new ToyCategory();
                minCategory.setId(resultSet.getInt("category_Id"));
                minCategory.setName(resultSet.getString("category_Name"));
            }
            resultSet.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return minCategory;
    }

    @Override
    public Map<ToyCategory, Integer> categories() throws Exception {
        Map<ToyCategory, Integer> categories = new HashMap<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                SELECT  c.name AS category_Name, c.id AS category_Id, COUNT(t.id) AS toy_count
                FROM toyCategories AS c
                LEFT JOIN toys AS t ON c.id = t.id_Category
                GROUP BY c.id, c.name
                """))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ToyCategory toyCategory = new ToyCategory();
                toyCategory.setId(resultSet.getInt("category_Id"));
                toyCategory.setName(resultSet.getString("category_Name"));
                categories.put(toyCategory, resultSet.getInt("toy_count"));
            }
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Map<ToyCategory, Integer> sortByCategory()  {
        Map<ToyCategory, Integer> categories = new HashMap<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                SELECT  c.name AS category_Name, c.id AS category_Id, 
                COUNT(t.id) AS toy_count
                FROM toyCategories AS c
                LEFT JOIN toys AS t ON c.id = t.id_Category
                GROUP BY c.id, c.name
                ORDER BY toy_count DESC
                """))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ToyCategory toyCategory = new ToyCategory();
                toyCategory.setId(resultSet.getInt("category_Id"));
                toyCategory.setName(resultSet.getString("category_Name"));
                categories.put(toyCategory, resultSet.getInt("toy_count"));
            }
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return categories;
    }


    @Override
    public void increase(int idToy, int amount)  {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                     UPDATE toys SET amount = amount + ? 
                     WHERE id = ?"""))
        {
            preparedStatement.setInt(1,amount);
            preparedStatement.setInt(2,idToy);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void decrease(int idToy, int amount) throws Exception {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                     SELECT amount FROM toys WHERE id = ?"""))
        {
            preparedStatement.setInt(1,idToy);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int currentAmount = resultSet.getInt("amount");
                if(currentAmount-amount<0){
                    System.out.println("WARNING! You cannot remove more toys than are available ");
                } else if (currentAmount-amount==0) {
                    System.out.println("The toy is removed because there are 0 units");
                    delete((long) idToy);

                } else {
                    try(PreparedStatement updateStatment = getConnection().prepareStatement(
                            """
                 UPDATE toys SET amount = amount -? WHERE id =?"""))
                    {
                        updateStatment.setInt(1,amount);
                        updateStatment.setInt(2,idToy);
                        updateStatment.executeUpdate();
                    }
                }
            }
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Toy> showToysAboveOf(double value) throws Exception {
        List<Toy> toyList = new ArrayList<>();
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                    SELECT t.*, c.name AS category_Name, c.id AS category_Id
                    FROM toys AS t 
                    INNER JOIN toyCategories AS c ON t.id_Category = c.id
                    WHERE t.price >?"""
        ))
        {
            preparedStatement.setDouble(1,value);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Toy toy = createToy(resultSet);
                toyList.add(toy);
            }
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return toyList;
    }

    @Override
    public Boolean verifyExist(int id) {
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
            SELECT * FROM toys WHERE id =?"""))
        {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Integer totalToys() throws Exception {
        Integer total = null;
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                    SELECT SUM(amount) AS Total FROM toys"""))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                total = resultSet.getInt("Total");
            }else {
                return 0;//si no hay juguetes se devuelve cero.
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public Double totalPriceAllToys() throws Exception {
        Double totalPrice = null;
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                    SELECT SUM(price) AS TotalPrice FROM toys"""
        ))
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                totalPrice = resultSet.getDouble("TotalPrice");
            }else {
                return 0.0;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return totalPrice;
    }
}
