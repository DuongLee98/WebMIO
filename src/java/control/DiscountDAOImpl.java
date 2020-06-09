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
import model.Discount;
import model.Pricetag;

/**
 *
 * @author kl
 */
public class DiscountDAOImpl implements DAO{
private Connection connection;
private PricetagDAOImpl pdi;

    public DiscountDAOImpl(Connection connection) {
        this.connection = connection;
        this.pdi = new PricetagDAOImpl(this.connection);
    }
    @Override
    public ArrayList<Discount> getAll() {
        ArrayList<Discount> rs = new ArrayList<>();

        String sql = "SELECT * FROM Discount";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Discount Discount = new Discount();
                Pricetag pt = this.pdi.searchById(rss.getInt("PricetagId"));
                if(pt != null){
                    Discount.setPriceTagId(pt);
                }
                Discount.setId(rss.getInt("Id"));
                Discount.setDescription(rss.getString("Description"));
                Discount.setValue(rss.getInt("value"));
                rs.add(Discount);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Discount Discount = (Discount) t;
            
            String query = "INSERT INTO Discount VALUE (null, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Discount.getPriceTagId().getId());
            ps.setInt(2, Discount.getValue());
            ps.setString(3, Discount.getDescription());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Discount b = (Discount) t;
            String sql = "update Discount set Paymentid = ? AND PayDate = ?"
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
            Discount b = (Discount) t;
            String sql = "delete from Discount where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Discount searchById(int id) {
        Discount b = null;
        try {
            String sql = "select * from Discount where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                b.setId(rs.getInt("Id"));
                Pricetag pt = this.pdi.searchById(rs.getInt("PricetagId"));
                if(pt != null){
                    b.setPriceTagId(pt);
                }
                
                b.setDescription(rs.getString("Description"));
                b.setValue(rs.getInt("value"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
}
