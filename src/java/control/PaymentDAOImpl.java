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
import model.orderprocess.Myorder;
import model.payment.Payment;

/**
 *
 * @author kl
 */
public class PaymentDAOImpl implements DAO{
private Connection connection;
private MyorderDAOImpl mdi;
    public PaymentDAOImpl(Connection connection) {
        this.connection = connection;
        this.mdi = new MyorderDAOImpl(this.connection);
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
                Payment.setId(rss.getInt("Id"));
                
                Myorder mo = this.mdi.searchById(rss.getInt("OrderId"));
                
                if(mo != null){
                    Payment.setOrderId(mo);
                }
                Payment.setPaymentStt(rss.getBoolean("PaymentStt"));
                Payment.setTotalWithShip(rss.getInt("TotalWithShip"));
                
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
            
            String query = "INSERT INTO Payment VALUE (null, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Payment.getOrderId().getId());
            ps.setBoolean(2, Payment.getPaymentStt());
            ps.setInt(3, Payment.getTotalWithShip());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Payment b = (Payment) t;
            String sql = "update Payment set Paymentid = ? AND PayDate = ?"
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
            Payment b = (Payment) t;
            String sql = "delete from Payment where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Payment searchById(int id) {
        Payment b = null;
        try {
            String sql = "select * from Payment where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                b.setId(rs.getInt("Id"));
                
                Myorder mo = this.mdi.searchById(rs.getInt("OrderId"));
                
                if(mo != null){
                    b.setOrderId(mo);
                }
                b.setPaymentStt(rs.getBoolean("PaymentStt"));
                b.setTotalWithShip(rs.getInt("TotalWithShip"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
}
