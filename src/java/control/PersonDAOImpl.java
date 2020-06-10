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
import model.userinfomation.Address;
import model.userinfomation.Fullname;
import model.userinfomation.Person;

/**
 *
 * @author kl
 */
public class PersonDAOImpl implements DAO{
private Connection connection;
private AddressDAOImpl adi;
private FullnameDAOImpl fdi;
    public PersonDAOImpl(Connection connection) {
        this.connection = connection;
        this.adi = new AddressDAOImpl(this.connection);
        this.fdi = new FullnameDAOImpl(this.connection);
    }
   
   @Override
    public ArrayList<Person> getAll() {
        ArrayList<Person> rs = new ArrayList<>();

        String sql = "SELECT * FROM Person";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Person Person = new Person();
                Fullname f = this.fdi.searchById(rss.getInt("FullNameid"));
                Address a = this.adi.searchById(rss.getInt("AddressId"));
                if(f!=null && a!= null){
                    Person.setFullNameId(f);
                    Person.setAddressId(a);
                }
                Person.setId(rss.getInt("Id"));
                Person.setDob(rss.getString("Dob"));

                rs.add(Person);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Person Person = (Person) t;
            
            String query = "INSERT INTO Person VALUE (null, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Person.getAddressId().getId());
            ps.setInt(2, Person.getFullNameId().getId());
            ps.setString(3, Person.getDob());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Person b = (Person) t;
            String sql = "update Person set Paymentid = ? AND PayDate = ?"
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
            Person b = (Person) t;
            String sql = "delete from Person where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Person searchById(int id) {
        Person b = null;
        try {
            String sql = "select * from Person where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                Fullname f = this.fdi.searchById(rs.getInt("FullNameid"));
                Address a = this.adi.searchById(rs.getInt("AddressId"));
                if(f!=null && a!= null){
                    b.setFullNameId(f);
                    b.setAddressId(a);
                }
                b.setId(rs.getInt("Id"));
                b.setDob(rs.getString("Dob"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
}
