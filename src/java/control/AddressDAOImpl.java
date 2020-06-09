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
import model.Address;

/**
 *
 * @author kl
 */
public class AddressDAOImpl implements DAO{
private Connection connection;

    public AddressDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Address> getAll() {
        ArrayList<Address> rs = new ArrayList<>();

        String sql = "SELECT * FROM address";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Address add = new Address();
                add.setId(rss.getInt("Id"));
                add.setNumber(rss.getInt("number"));
                add.setStreet(rss.getString("street"));
                add.setDistrict(rss.getString("district"));
                add.setCity(rss.getString("City"));
                

                rs.add(add);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Address a = (Address) t;
            String query = "INSERT INTO address VALUE (null, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, a.getNumber());
            ps.setString(2, a.getStreet());
            ps.setString(3, a.getDistrict());
            ps.setString(4, a.getCity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Address a = (Address) t;
            String sql = "update address set number = ? and street = ? "
                    + "and district = ? and city = ? where id = ?"
                    ;
            PreparedStatement p = connection.prepareCall(sql);
            p.setInt(1, a.getNumber());
            p.setString(2, a.getStreet());
            p.setString(3, a.getDistrict());
            p.setString(4, a.getCity());
            p.setInt(5, a.getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Object t) {
        try {
            Address a = (Address) t;
            String sql = "delete from address where Id =?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, a.getId());
           
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Address searchById(int id) {
        Address a = new Address();
        try {
            String sql = "select * from address where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                a.setId(rs.getInt("Id"));
                a.setNumber(rs.getInt("Number"));
                a.setStreet(rs.getString("Street"));
                a.setDistrict(rs.getString("District"));
                a.setCity(rs.getString("city"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

    
}
