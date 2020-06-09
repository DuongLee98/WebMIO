/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.util.ArrayList;
import model.Paycod;

/**
 *
 * @author kl
 */
public class PaycodDAOImpl implements DAO{
private Connection connection;

    public PaycodDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Paycod> getAll() {
        ArrayList<Paycod> rs = new ArrayList<>();

        String sql = "SELECT * FROM Paycod";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Paycod Paycod = new Paycod();
                Paycod.setCart(rss.getObject(1, Cart.class));
                Paycod.setItem(rss.getObject(2, Item.class));
                Paycod.setQuantity(rss.getInt("quantity"));

                rs.add(Paycod);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Paycod Paycod = (Paycod) t;
            
            String query = "INSERT INTO Paycod VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Paycod.getCart().getId());
            ps.setInt(2, Paycod.getItem().getId());
            ps.setInt(3, Paycod.getQuantity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
+
    }

    @Override
    public void edit(Object t) {
        try {
            Paycod b = (Paycod) t;
            String sql = "update Paycod set Paymentid = ? AND PayDate = ?"
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
            Paycod b = (Paycod) t;
            String sql = "delete from Paycod where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            p.setInt(2, b.getPaymentId().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Paycod searchById(int id) {
        Paycod b = new Paycod();
        try {
            String sql = "select * from Paycod where Id = ?";
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
