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
import model.Expressshipment;

/**
 *
 * @author kl
 */
public class ExpressshipmentDAOImpl implements DAO{
private Connection connection;

    public ExpressshipmentDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Expressshipment> getAll() {
        ArrayList<Expressshipment> rs = new ArrayList<>();

        String sql = "SELECT * FROM Expressshipment";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Expressshipment Expressshipment = new Expressshipment();
                

                rs.add(Expressshipment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Expressshipment Expressshipment = (Expressshipment) t;
            
            String query = "INSERT INTO Expressshipment VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Expressshipment b = (Expressshipment) t;
            String sql = "update Expressshipment set Paymentid = ? AND PayDate = ?"
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
            Expressshipment b = (Expressshipment) t;
            String sql = "delete from Expressshipment where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Expressshipment searchById(int id) {
        Expressshipment b = new Expressshipment();
        try {
            String sql = "select * from Expressshipment where Id = ?";
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
