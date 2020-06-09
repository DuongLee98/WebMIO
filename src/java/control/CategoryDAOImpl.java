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
    public ArrayList getAll() {
        ArrayList<Category> rs = new ArrayList<>();
        
        String sql="SELECT * FROM category";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while(rss.next())
            {
                Category obj = new Category();
                obj.setId(rss.getInt("Id"));
                obj.setName(rss.getString("Name"));
                
                //account.setPersonId(personId);
                rs.add(obj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        Category in = (Category) t;
        String sql = "Insert into category (name) values (?)";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, in.getName());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void edit(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object searchById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
