/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author kl
 */
public class EmployeeDAOImpl implements DAO{
private Connection connection;

    public EmployeeDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Employee> getAll() {
        ArrayList<Employee> rs = new ArrayList<>();

        String sql = "SELECT * FROM Employee";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Employee Employee = new Employee();
                Employee.setCart(rss.getObject(1, Cart.class));
                Employee.setItem(rss.getObject(2, Item.class));
                Employee.setQuantity(rss.getInt("quantity"));

                rs.add(Employee);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Employee Employee = (Employee) t;
            
            String query = "INSERT INTO Employee VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Employee.getCart().getId());
            ps.setInt(2, Employee.getItem().getId());
            ps.setInt(3, Employee.getQuantity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
+
    }

    @Override
    public void edit(Object t) {
        try {
            Employee b = (Employee) t;
            String sql = "update Employee set Paymentid = ? AND PayDate = ?"
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
            Employee b = (Employee) t;
            String sql = "delete from Employee where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            p.setInt(2, b.getPaymentId().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Employee searchById(int id) {
        Employee b = new Employee();
        try {
            String sql = "select * from Employee where Id = ?";
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
