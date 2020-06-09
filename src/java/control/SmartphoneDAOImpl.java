/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.util.ArrayList;
import model.Smartphone;

/**
 *
 * @author kl
 */
public class SmartphoneDAOImpl implements DAO{
private Connection connection;

    public SmartphoneDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Smartphone> getAll() {
        ArrayList<Smartphone> rs = new ArrayList<>();

        String sql = "SELECT * FROM Smartphone";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Smartphone Smartphone = new Smartphone();
                Smartphone.setCart(rss.getObject(1, Cart.class));
                Smartphone.setItem(rss.getObject(2, Item.class));
                Smartphone.setQuantity(rss.getInt("quantity"));

                rs.add(Smartphone);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Smartphone Smartphone = (Smartphone) t;
            
            String query = "INSERT INTO Smartphone VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Smartphone.getCart().getId());
            ps.setInt(2, Smartphone.getItem().getId());
            ps.setInt(3, Smartphone.getQuantity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
+
    }

    @Override
    public void edit(Object t) {
        try {
            Smartphone b = (Smartphone) t;
            String sql = "update Smartphone set Paymentid = ? AND PayDate = ?"
                    + " where Id = ?";
            PreparedStatement p = connection.prepareCall(sql);
            p.setInt(1, b.getPaymentId().getId());
            p.setString(2, b.getPayDate());
            
            p.setInt(3, b.getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Object t) {
        try {
            Smartphone b = (Smartphone) t;
            String sql = "delete from Smartphone where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            p.setInt(2, b.getPaymentId().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Smartphone searchById(int id) {
        Smartphone b = new Smartphone();
        try {
            String sql = "select * from Smartphone where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                b.setId(rs.getInt("Id"));
                b.setPaymentId(rs.getObject(2, Payment.class));
                b.setPayDate(rs.getString("PayDate"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
}
