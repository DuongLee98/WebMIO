/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.util.ArrayList;
import model.Invoice;

/**
 *
 * @author kl
 */
public class InvoiceDAOImpl implements DAO{
private Connection connection;

    public InvoiceDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Invoice> getAll() {
        ArrayList<Invoice> rs = new ArrayList<>();

        String sql = "SELECT * FROM Invoice";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Invoice Invoice = new Invoice();
                Invoice.setCart(rss.getObject(1, Cart.class));
                Invoice.setItem(rss.getObject(2, Item.class));
                Invoice.setQuantity(rss.getInt("quantity"));

                rs.add(Invoice);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Invoice Invoice = (Invoice) t;
            
            String query = "INSERT INTO Invoice VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Invoice.getCart().getId());
            ps.setInt(2, Invoice.getItem().getId());
            ps.setInt(3, Invoice.getQuantity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
+
    }

    @Override
    public void edit(Object t) {
        try {
            Invoice b = (Invoice) t;
            String sql = "update Invoice set Paymentid = ? AND PayDate = ?"
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
            Invoice b = (Invoice) t;
            String sql = "delete from Invoice where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            p.setInt(2, b.getPaymentId().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Invoice searchById(int id) {
        Invoice b = new Invoice();
        try {
            String sql = "select * from Invoice where Id = ?";
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
