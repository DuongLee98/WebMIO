/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.item;

import control.AccountDAOImpl;
import control.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.item.Pricetag;

/**
 *
 * @author kl
 */
public class PricetagDAOImpl implements DAO{
private Connection connection;

    public PricetagDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Pricetag> getAll() {
        ArrayList<Pricetag> rs = new ArrayList<>();

        String sql = "SELECT * FROM Pricetag";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Pricetag Pricetag = new Pricetag();
                Pricetag.setId(rss.getInt("Id"));
                Pricetag.setPrice(rss.getInt("Price"));

                rs.add(Pricetag);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Pricetag Pricetag = (Pricetag) t;
            
            String query = "INSERT INTO Pricetag VALUE (null, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Pricetag.getPrice());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Pricetag b = (Pricetag) t;
            String sql = "update Pricetag set Paymentid = ? AND PayDate = ?"
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
            Pricetag b = (Pricetag) t;
            String sql = "delete from Pricetag where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Pricetag searchById(int id) {
        Pricetag b = new Pricetag();
        try {
            String sql = "select * from Pricetag where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                b.setId(rs.getInt("Id"));
                b.setPrice(rs.getInt("Price"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
}
