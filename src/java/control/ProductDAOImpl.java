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
import model.Product;

/**
 *
 * @author kl
 */
public class ProductDAOImpl implements DAO{
private Connection connection;

    public ProductDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Product> getAll() {
        ArrayList<Product> rs = new ArrayList<>();

        String sql = "SELECT * FROM Product";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Product product = new Product();
                product.setId(rss.getInt("Id"));
                //product.setCategoryId(rss.getObject(2, Category.class));

                rs.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Product Product = (Product) t;
            
            String query = "INSERT INTO Product VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
//            ps.setInt(1, Product.getCart().getId());
//            ps.setInt(2, Product.getItem().getId());
//            ps.setInt(3, Product.getQuantity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void edit(Object t) {

    }

    @Override
    public void delete(Object t) {

    }

    @Override
    public Product searchById(int id) {
        Product b = new Product();
        try {
            String sql = "select * from Product where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
//                b.setId(rs.getInt("Id"));
//                b.setPaymentId(rs.getObject(2, Payment.class));
//                b.setPayDate(rs.getString("PayDate"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
}