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
import model.Paycreditcard;

/**
 *
 * @author kl
 */
public class PaycreditcardDAOImpl implements DAO{
private Connection connection;

    public PaycreditcardDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Paycreditcard> getAll() {
        ArrayList<Paycreditcard> rs = new ArrayList<>();

        String sql = "SELECT * FROM Paycreditcard";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Paycreditcard Paycreditcard = new Paycreditcard();
                

                rs.add(Paycreditcard);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Paycreditcard Paycreditcard = (Paycreditcard) t;
            
            String query = "INSERT INTO Paycreditcard VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Paycreditcard b = (Paycreditcard) t;
            String sql = "update Paycreditcard set Paymentid = ? AND PayDate = ?"
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
            Paycreditcard b = (Paycreditcard) t;
            String sql = "delete from Paycreditcard where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Paycreditcard searchById(int id) {
        Paycreditcard b = new Paycreditcard();
        try {
            String sql = "select * from Paycreditcard where Id = ?";
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
