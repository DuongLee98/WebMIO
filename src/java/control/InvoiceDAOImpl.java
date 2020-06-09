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
import model.Employee;
import model.Invoice;
import model.Myorder;
import model.Person;

/**
 *
 * @author kl
 */
public class InvoiceDAOImpl implements DAO{
private Connection connection;
private EmployeeDAOImpl pdi;
private MyorderDAOImpl modi;
    public InvoiceDAOImpl(Connection connection) {
        this.connection = connection;
        this.modi = new MyorderDAOImpl(this.connection);
        this.pdi = new EmployeeDAOImpl(this.connection);
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
                Employee per = this.pdi.searchById(rss.getInt("EmployeePersonId"));
                Myorder m = this.modi.searchById(rss.getInt("OrderId"));
                if(per != null && m != null){
                    Invoice.setEmployeePersonId(per);
                    Invoice.setOrderId(m);
                }
                Invoice.setId(rss.getInt("Id"));
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
            
            String query = "INSERT INTO Invoice VALUE (null, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Invoice.getEmployeePersonId().getPerson().getId());
            ps.setInt(2, Invoice.getOrderId().getId());
            ps.setInt(3, 100000);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Invoice b = (Invoice) t;
            String sql = "update Invoice set Paymentid = ? AND PayDate = ?"
                    + " where Id = ?";
            PreparedStatement p = connection.prepareCall(sql);
            
            
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

            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Invoice searchById(int id) {
        Invoice b = null;
        try {
            String sql = "select * from Invoice where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                Employee per = this.pdi.searchById(rs.getInt("EmployeePersonId"));
                Myorder m = this.modi.searchById(rs.getInt("OrderId"));
                if(per != null && m != null){
                    b.setEmployeePersonId(per);
                    b.setOrderId(m);
                }
                b.setId(rs.getInt("Id"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
}
