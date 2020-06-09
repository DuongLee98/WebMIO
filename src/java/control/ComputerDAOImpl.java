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
import model.Computer;
import model.Item;

/**
 *
 * @author kl
 */
public class ComputerDAOImpl implements DAO{
private Connection connection;

    public ComputerDAOImpl(Connection connection) {
        this.connection = connection;
    }
   @Override
    public ArrayList<Computer> getAll() {
        ArrayList<Computer> rs = new ArrayList<>();

        String sql = "SELECT * FROM Computer";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Computer Computer = new Computer();
//                Computer.setCart(rss.getObject(1, Cart.class));
//                Computer.setItem(rss.getObject(2, Item.class));
//                Computer.setQuantity(rss.getInt("quantity"));

                rs.add(Computer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Computer Computer = (Computer) t;
            
            String query = "INSERT INTO Computer VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
//            ps.setInt(1, Computer.getCart().getId());
//            ps.setInt(2, Computer.getItem().getId());
//            ps.setInt(3, Computer.getQuantity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Computer b = (Computer) t;
            String sql = "update Computer set Paymentid = ? AND PayDate = ?"
                    + " where Id = ?";
            PreparedStatement p = connection.prepareCall(sql);
//            p.setInt(1, b.getPaymentId().getId());
//            p.setString(2, b.getPayDate());
            
            p.setInt(3, b.getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Object t) {
        try {
            Computer b = (Computer) t;
            String sql = "delete from Computer where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            //p.setInt(2, b.getPaymentId().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Computer searchById(int id) {
        Computer b = new Computer();
        try {
            String sql = "select * from Computer where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                b.setId(rs.getInt("Id"));
                //b.setPaymentId(rs.getObject(2, Payment.class));
                //b.setPayDate(rs.getString("PayDate"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
}
