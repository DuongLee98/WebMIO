/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.util.ArrayList;
import model.Payment;

/**
 *
 * @author kl
 */
public class PaymentDAOImpl implements DAO{
private Connection connection;

    public PaymentDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Payment> getAll() {
        ArrayList<Payment> rs = new ArrayList<>();

        String sql = "SELECT * FROM Payment";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Payment Payment = new Payment();
                Payment.setCart(rss.getObject(1, Cart.class));
                Payment.setItem(rss.getObject(2, Item.class));
                Payment.setQuantity(rss.getInt("quantity"));

                rs.add(Payment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Payment Payment = (Payment) t;
            
            String query = "INSERT INTO Payment VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Payment.getCart().getId());
            ps.setInt(2, Payment.getItem().getId());
            ps.setInt(3, Payment.getQuantity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
+
    }

    @Override
    public void edit(Object t) {
        try {
            Payment b = (Payment) t;
            String sql = "update Payment set Paymentid = ? AND PayDate = ?"
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
            Payment b = (Payment) t;
            String sql = "delete from Payment where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            p.setInt(2, b.getPaymentId().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Payment searchById(int id) {
        Payment b = new Payment();
        try {
            String sql = "select * from Payment where Id = ?";
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
