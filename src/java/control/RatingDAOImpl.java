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
import model.Item;
import model.Rating;

/**
 *
 * @author kl
 */
public class RatingDAOImpl implements DAO{
private Connection connection;
private ItemDAOImpl idi;
    public RatingDAOImpl(Connection connection) {
        this.connection = connection;
        this.idi = new ItemDAOImpl(this.connection);
    }
    @Override
    public ArrayList<Rating> getAll() {
        ArrayList<Rating> rs = new ArrayList<>();

        String sql = "SELECT * FROM Rating";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Rating Rating = new Rating();
                Rating.setId(rss.getInt("Id"));
                Item item = this.idi.searchById(rss.getInt("Itemid"));
                if(item != null){
                    Rating.setItemId(item);
                }
                Rating.setComment(rss.getString("Comment"));
                Rating.setStar(rss.getInt("star"));
                
                
                rs.add(Rating);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Rating Rating = (Rating) t;
            
            String query = "INSERT INTO Rating VALUE (null, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Rating.getItemId().getId());
            ps.setInt(2, Rating.getStar());
            ps.setString(3, Rating.getComment());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Rating b = (Rating) t;
            String sql = "update Rating set Paymentid = ? AND PayDate = ?"
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
            Rating b = (Rating) t;
            String sql = "delete from Rating where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Rating searchById(int id) {
        Rating b = null;
        try {
            String sql = "select * from Rating where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                b.setId(rs.getInt("Id"));
                Item item = this.idi.searchById(rs.getInt("Itemid"));
                if(item != null){
                    b.setItemId(item);
                }
                b.setComment(rs.getString("Comment"));
                b.setStar(rs.getInt("star"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
}
