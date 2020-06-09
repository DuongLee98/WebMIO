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
import model.Customer;
import model.Myorder;

/**
 *
 * @author kl
 */
public class MyorderDAOImpl implements DAO{
private Connection connection;
private CustomerDAOImpl cdi;
private CartDAOImpl cadi;
    public MyorderDAOImpl(Connection connection) {
        this.connection = connection;
        this.cadi = new CartDAOImpl(this.connection);
        this.cdi = new CustomerDAOImpl(this.connection);
    }
    @Override
    public ArrayList<Myorder> getAll() {
        ArrayList<Myorder> rs = new ArrayList<>();

        String sql = "SELECT * FROM Myorder";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Myorder Myorder = new Myorder();
                Cart c= this.cadi.searchById(rss.getInt("CartId"));
                Customer cus = this.cdi.searchById(rss.getInt("CustomerPersonId"));
                if(c != null && cus != null){
                    Myorder.setCustomerPersonId(cus);
                    Myorder.setCartId(c);
                }
                Myorder.setId(rss.getInt("Id"));
                Myorder.setOrderDate(rss.getString("OrderDate"));
                Myorder.setShipTo(rss.getString("ShipTo"));
                
                rs.add(Myorder);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Myorder Myorder = (Myorder) t;
            
            String query = "INSERT INTO Myorder VALUE (null, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Myorder.getCartId().getId());
            ps.setInt(2, Myorder.getCustomerPersonId().getPerson().getId());
            ps.setString(3, Myorder.getOrderDate());
            ps.setString(4, Myorder.getShipTo());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Myorder b = (Myorder) t;
            String sql = "update Myorder set Paymentid = ? AND PayDate = ?"
                    + " where Id = ?";
            PreparedStatement p = connection.prepareCall(sql);
            
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Object t) {
        try {
            Myorder b = (Myorder) t;
            String sql = "delete from Myorder where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Myorder searchById(int id) {
        Myorder b = null;
        try {
            String sql = "select * from Myorder where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                
                Cart c= this.cadi.searchById(rs.getInt("CartId"));
                Customer cus = this.cdi.searchById(rs.getInt("CustomerPersonId"));
                if(c != null && cus != null){
                    b.setCustomerPersonId(cus);
                    b.setCartId(c);
                }
                b.setId(rs.getInt("Id"));
                b.setOrderDate(rs.getString("OrderDate"));
                b.setShipTo(rs.getString("ShipTo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
}
