package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories()
    {
        // get all categories
        List<Category> categories = new ArrayList<>();

        String sql = """
               SELECT *
               FROM categories;""";

        try(Connection connection = getConnection();
            PreparedStatement query = connection.prepareStatement(sql);
            ResultSet results = query.executeQuery())
        {
            while (results.next()){
                categories.add(mapRow(results));

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return categories;
    }

    @Override
    public Category getById(int categoryId)
    {
        // get category by id
        Category category;

        String sql = """
                SELECT
                *
                FROM categories
                WHERE category_id = ?;""";

        try(Connection connection = getConnection();
            PreparedStatement query = connection.prepareStatement(sql);
        )
        {
            query.setInt(1, categoryId);
            try( ResultSet results = query.executeQuery()){
                while (results.next()){
                    return mapRow(results);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Category create(Category category)
    {
        // create a new category
        String sql = """
            INSERT INTO categories (name, description)
            VALUES (?, ?)
            """;

        try (Connection connection = getConnection();
             PreparedStatement query = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            query.setString(1, category.getName());
            query.setString(2, category.getDescription());

            query.executeUpdate();

            try (ResultSet generatedKeys = query.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    category.setCategoryId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error creating category", e);
        }
        return category;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        // update category
        String sql = """
                UPDATE categories
                SET name = ?, description = ?
                WHERE category_id = ?
                """;

        try(Connection connection = getConnection();
            PreparedStatement query = connection.prepareStatement(sql);
        )
        {
            query.setString(1, category.getName());
            query.setString(2, category.getDescription());
            query.setInt(3, categoryId);

            query.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int categoryId)
    {
        // delete category
        String sql = """
                DELETE FROM categories
                WHERE category_id = ?
                """;

        try(Connection connection = getConnection();
            PreparedStatement query = connection.prepareStatement(sql);
        )
        {
            query.setInt(1, categoryId);
            query.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
