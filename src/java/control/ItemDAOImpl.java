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
import model.Pricetag;
import model.Product;

/**
 *
 * @author kl
 */
public class ItemDAOImpl implements DAO{
private Connection connection;
private PricetagDAOImpl prdi;
private ProductDAOImpl pdi;
    public ItemDAOImpl(Connection connection) {
        this.connection = connection;
        this.pdi = new ProductDAOImpl(this.connection);
        this.prdi = new PricetagDAOImpl(this.connection);
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
                Product pro = this.pdi.searchById(rss.getInt("ProductId"));
                Pricetag price = this.prdi.searchById(rss.getInt("Pricetagid"));
                if(pro!=null && price != null){
                    Item.setPriceTagId(price);
                    Item.setProductId(pro);
                }
                Item.setId(rss.getInt("Id"));
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
            
            String query = "INSERT INTO Item VALUE (null, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Item.getPriceTagId().getId());
            ps.setInt(2, Item.getProductId().getId());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Item b = (Item) t;
            String sql = "update Item set Paymentid = ? AND PayDate = ?"
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
            Item b = (Item) t;
            String sql = "delete from Item where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Item searchById(int id) {
        Item b = null;
        try {
            String sql = "select * from Item where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                Product pro = this.pdi.searchById(rs.getInt("ProductId"));
                Pricetag price = this.prdi.searchById(rs.getInt("Pricetagid"));
                if(pro!=null && price != null){
                    b.setPriceTagId(price);
                    b.setProductId(pro);
                }
                b.setId(rs.getInt("Id"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
}
