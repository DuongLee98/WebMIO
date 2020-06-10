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
import model.userinfomation.Fullname;

/**
 *
 * @author kl
 */
public class FullnameDAOImpl implements DAO{
private Connection connection;

    public FullnameDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Fullname> getAll() {
        ArrayList<Fullname> rs = new ArrayList<>();

        String sql = "SELECT * FROM Fullname";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Fullname Fullname = new Fullname();
                Fullname.setId(rss.getInt("Id"));
                Fullname.setFirstname(rss.getString("Firstname"));
                Fullname.setMidname(rss.getString("Midname"));
                Fullname.setLastname(rss.getString("Lastname"));
                rs.add(Fullname);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Fullname Fullname = (Fullname) t;
            
            String query = "INSERT INTO Fullname VALUE (null, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, Fullname.getFirstname());
            ps.setString(2, Fullname.getMidname());
            ps.setString(3, Fullname.getLastname());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Fullname b = (Fullname) t;
            String sql = "update Fullname set Paymentid = ? AND PayDate = ?"
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
            Fullname b = (Fullname) t;
            String sql = "delete from Fullname where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Fullname searchById(int id) {
        Fullname b = null;
        try {
            String sql = "select * from Fullname where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                b.setId(rs.getInt("Id"));
                b.setFirstname(rs.getString("Firstname"));
                b.setMidname(rs.getString("Midname"));
                b.setLastname(rs.getString("Lastname"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
}
