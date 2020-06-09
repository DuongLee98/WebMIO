/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.util.ArrayList;
import model.Item;

/**
 *
 * @author kl
 */
public class ItemDAOImpl implements DAO{
private Connection connection;

    public ItemDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Item> getAll() {
        ArrayList<Item> rs = new ArrayList<>();

        String sql = "SELECT * FROM Item";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Item Item = new Item();
                Item.setCart(rss.getObject(1, Cart.class));
                Item.setItem(rss.getObject(2, Item.class));
                Item.setQuantity(rss.getInt("quantity"));

                rs.add(Item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Item Item = (Item) t;
            
            String query = "INSERT INTO Item VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Item.getCart().getId());
            ps.setInt(2, Item.getItem().getId());
            ps.setInt(3, Item.getQuantity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
+
    }

    @Override
    public void edit(Object t) {
        try {
            Item b = (Item) t;
            String sql = "update Item set Paymentid = ? AND PayDate = ?"
                    + " where Id = ?";
            PreparedStatement p = connection.prepareCall(sql);
            p.setInt(1, b.getPaymentId().getId());
            p.setString(2, b.getPayDate());
            
            p.setInt(3, b.getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Object t) {
        try {
            Item b = (Item) t;
            String sql = "delete from Item where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            p.setInt(2, b.getPaymentId().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Item searchById(int id) {
        Item b = new Item();
        try {
            String sql = "select * from Item where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                b.setId(rs.getInt("Id"));
                b.setPaymentId(rs.getObject(2, Payment.class));
                b.setPayDate(rs.getString("PayDate"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
}
