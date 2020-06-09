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
import model.Bill;
import model.Payment;

/**
 *
 * @author kl
 */
public class BillDAOImpl implements DAO{
private Connection connection;
    private PaymentDAOImpl pdi;
    public BillDAOImpl(Connection connection) {
        this.connection = connection;
        this.pdi = new PaymentDAOImpl(this.connection);
    }
    @Override
    public ArrayList<Bill> getAll() {
        ArrayList<Bill> rs = new ArrayList<>();

        String sql = "SELECT * FROM bill";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Bill bill = new Bill();
                bill.setId(rss.getInt("Id"));
                Payment pay = this.pdi.searchById(rss.getInt("PaymentId"));
                bill.setPayDate(rss.getString("PayDate"));
 
                rs.add(bill);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Bill bill = (Bill) t;
            
            String query = "INSERT INTO bill VALUE (null, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, bill.getPaymentId().getId());
            ps.setString(2, bill.getPayDate());
            
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Bill b = (Bill) t;
            String sql = "update bill set Paymentid = ? AND PayDate = ?"
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
            Bill b = (Bill) t;
            String sql = "delete from bill where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            p.setInt(2, b.getPaymentId().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Bill searchById(int id) {
        Bill b = null;
        try {
            String sql = "select * from bill where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                b.setId(rs.getInt("Id"));
                Payment pay = this.pdi.searchById(rs.getInt("PaymentId"));
                if(p != null){
                    b.setPaymentId(pay);
                }
                b.setPayDate(rs.getString("PayDate"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    
}
