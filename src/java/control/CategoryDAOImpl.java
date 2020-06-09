/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;

/**
 *
 * @author kl
 */
public class CategoryDAOImpl implements DAO{
private Connection connection;

    public CategoryDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Category> getAll() {
        ArrayList<Category> rs = new ArrayList<>();

        String sql = "SELECT * FROM Category";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Category category = new Category();
                category.setId(rss.getInt("Id"));
                category.setName(rss.getString("Name"));

                rs.add(category);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Category Category = (Category) t;
            
            String query = "INSERT INTO Category VALUE (null, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, Category.getName());
            
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Category b = (Category) t;
            String sql = "update Category set Name = ?"
                    + " where Id = ?";
            PreparedStatement p = connection.prepareCall(sql);
            
            p.setString(1, b.getName());
            
            p.setInt(2, b.getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Object t) {
        try {
            Category b = (Category) t;
            String sql = "delete from Category where Id =?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Category searchById(int id) {
        Category b = new Category();
        try {
            String sql = "select * from Category where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                b.setId(rs.getInt("Id"));
                b.setName(rs.getString("Name"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
}
