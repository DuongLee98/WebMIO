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
import model.Miostore;
import model.Person;

/**
 *
 * @author kl
 */
public class EmployeeDAOImpl implements DAO{
private Connection connection;
private PersonDAOImpl pdi;
private MiostoreDAOImpl mdi;
    public EmployeeDAOImpl(Connection connection) {
        this.connection = connection;
        this.pdi = new PersonDAOImpl(this.connection);
        this.mdi = new MiostoreDAOImpl(this.connection);
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
                Person p = this.pdi.searchById(rss.getInt("Personid"));
                Miostore m = this.mdi.searchById(rss.getInt("Miostoreid"));
                if(p != null && m != null){
                    Employee.setPerson(p);
                    Employee.setMIOStoreId(m);
                }
                Employee.setRole(rss.getString("Role"));
                Employee.setSalary(rss.getInt("Salary"));
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
           
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Employee b = (Employee) t;
            String sql = "update Employee set Paymentid = ? AND PayDate = ?"
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
            Employee b = (Employee) t;
            String sql = "delete from Employee where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Employee searchById(int id) {
        Employee b = null;
        try {
            String sql = "select * from Employee where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                Person per = this.pdi.searchById(rs.getInt("Personid"));
                Miostore m = this.mdi.searchById(rs.getInt("Miostoreid"));
                if(p != null && m != null){
                    b.setPerson(per);
                    b.setMIOStoreId(m);
                }
                b.setRole(rs.getString("Role"));
                b.setSalary(rs.getInt("Salary"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
}
