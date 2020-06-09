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
import model.Miostore;

/**
 *
 * @author kl
 */
public class MiostoreDAOImpl implements DAO{
private Connection connection;
private AddressDAOImpl adi;
    public MiostoreDAOImpl(Connection connection) {
        this.connection = connection;
        this.adi = new AddressDAOImpl(this.connection);
    }
    @Override
    public ArrayList<Miostore> getAll() {
        ArrayList<Miostore> rs = new ArrayList<>();

        String sql = "SELECT * FROM Miostore";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Miostore Miostore = new Miostore();
                Miostore.setId(rss.getInt("Id"));
                Miostore.setEmail(rss.getString("Email"));
                Miostore.setPhone(rss.getString("Phone"));
                Address a = this.adi.searchById(rss.getInt("AddressId"));
                if(a!=null){
                    Miostore.setAddressId(a);
                }

                rs.add(Miostore);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Miostore Miostore = (Miostore) t;
            
            String query = "INSERT INTO Miostore VALUE (null, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Miostore.getAddressId().getId());
            ps.setString(2, "xoa ho tao cot nay voi");
            ps.setString(3, Miostore.getPhone());
            ps.setString(4, Miostore.getEmail());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Miostore b = (Miostore) t;
            String sql = "update Miostore set Paymentid = ? AND PayDate = ?"
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
            Miostore b = (Miostore) t;
            String sql = "delete from Miostore where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Miostore searchById(int id) {
        Miostore b = null;
        try {
            String sql = "select * from Miostore where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                b.setId(rs.getInt("Id"));
                
                b.setEmail(rs.getString("Email"));
                b.setPhone(rs.getString("Phone"));
                Address a = this.adi.searchById(rs.getInt("AddressId"));
                if(a!=null){
                    b.setAddressId(a);
                }
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
}
