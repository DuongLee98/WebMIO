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
import model.Customer;
import model.Person;

/**
 *
 * @author kl
 */
public class CustomerDAOImpl implements DAO{
private Connection connection;
private PersonDAOImpl pdi;
    public CustomerDAOImpl(Connection connection) {
        this.connection = connection;
        this.pdi = new PersonDAOImpl(this.connection);
    }
    @Override
    public ArrayList<Customer> getAll() {
        ArrayList<Customer> rs = new ArrayList<>();

        String sql = "SELECT * FROM Customer";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Customer Customer = new Customer();
                Person p = this.pdi.searchById(rss.getInt("PersonId"));
                if(p != null){
                    Customer.setPerson(p);
                    
                }
                Customer.setBonusPoint(rss.getInt("BonusPoint"));
                rs.add(Customer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Customer Customer = (Customer) t;
            
            String query = "INSERT INTO Customer VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
//            ps.setInt(1, Customer.getCart().getId());
//            ps.setInt(2, Customer.getItem().getId());
//            ps.setInt(3, Customer.getQuantity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void edit(Object t) {
        try {
            Customer b = (Customer) t;
            String sql = "update Customer set Paymentid = ? AND PayDate = ?"
                    + " where Id = ?";
            PreparedStatement p = connection.prepareCall(sql);
//            p.setInt(1, b.getPaymentId().getId());
//            p.setString(2, b.getPayDate());
//            
//            p.setInt(3, b.getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Object t) {
        try {
            Customer b = (Customer) t;
            String sql = "delete from Customer where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

//            p.setInt(1, b.getId());
//            p.setInt(2, b.getPaymentId().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Customer searchById(int id) {
        Customer b = null;
        try {
            String sql = "select * from Customer where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                Person per = this.pdi.searchById(rs.getInt("PersonId"));
                if(per != null){
                    b.setPerson(per);
                    
                }
                b.setBonusPoint(rs.getInt("BonusPoint"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
}
