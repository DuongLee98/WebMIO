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
import model.Cart;

/**
 *
 * @author kl
 */
public class CartDAOImpl implements DAO{
private Connection connection;

    public CartDAOImpl(Connection connection) {
        this.connection = connection;
    }
        @Override
    public ArrayList<Cart> getAll() {
        ArrayList<Cart> rs = new ArrayList<>();

        String sql = "SELECT * FROM Cart";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Cart cart = new Cart();
                cart.setId(rss.getInt("Id"));
                cart.setTotal(rss.getInt("Total"));
                

                rs.add(cart);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Cart cart = (Cart) t;
            
            String query = "INSERT INTO Cart VALUE (null, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, cart.getId());
            ps.setInt(2, cart.getTotal());
            
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Cart b = (Cart) t;
            String sql = "update cart set Total = ?"
                    + " where Id = ?";
            PreparedStatement p = connection.prepareCall(sql);
            p.setInt(1, b.getTotal());
            
            
            p.setInt(2, b.getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Object t) {
        try {
            Cart b = (Cart) t;
            String sql = "delete from Cart where Id =?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Cart searchById(int id) {
        Cart b = null;
        try {
            String sql = "select * from Cart where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                b.setId(rs.getInt("Id"));
                b.setTotal(rs.getInt("Total"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
}
