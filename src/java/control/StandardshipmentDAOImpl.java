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
import model.Standardshipment;

/**
 *
 * @author kl
 */
public class StandardshipmentDAOImpl implements DAO{
private Connection connection;

    public StandardshipmentDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Standardshipment> getAll() {
        ArrayList<Standardshipment> rs = new ArrayList<>();

        String sql = "SELECT * FROM Standardshipment";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Standardshipment Standardshipment = new Standardshipment();
                

                rs.add(Standardshipment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Standardshipment Standardshipment = (Standardshipment) t;
            
            String query = "INSERT INTO Standardshipment VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Standardshipment b = (Standardshipment) t;
            String sql = "update Standardshipment set Paymentid = ? AND PayDate = ?"
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
            Standardshipment b = (Standardshipment) t;
            String sql = "delete from Standardshipment where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Standardshipment searchById(int id) {
        Standardshipment b = new Standardshipment();
        try {
            String sql = "select * from Standardshipment where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
}
