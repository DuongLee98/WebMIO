/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.item.ItemDAOImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.cart.Cart;
import model.cart.CartItem;
import model.item.Item;

/**
 *
 * @author kl
 */
public class CartItemDAOImpl implements DAO{
private Connection connection;
private CartDAOImpl cdi;
private ItemDAOImpl idi;
    public CartItemDAOImpl(Connection connection) {
        this.connection = connection;
        this.cdi = new CartDAOImpl(this.connection);
        this.idi = new ItemDAOImpl(this.connection);
    }
        @Override
    public ArrayList<CartItem> getAll() {
        ArrayList<CartItem> rs = new ArrayList<>();

        String sql = "SELECT * FROM CartItem";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                CartItem cartItem = new CartItem();
                Cart c = this.cdi.searchById(rss.getInt("CartID"));
                Item item = this.idi.searchById(rss.getInt("ItemId"));
                
                if(c != null && item != null){
                    cartItem.setCart(c);
                    cartItem.setItem(item);
                }
                cartItem.setQuantity(rss.getInt("quantity"));

                rs.add(cartItem);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            CartItem cartItem = (CartItem) t;
            
            String query = "INSERT INTO CartItem VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, cartItem.getCart().getId());
            ps.setInt(2, cartItem.getItem().getId());
            ps.setInt(3, cartItem.getQuantity());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            CartItem b = (CartItem) t;
            String sql = "update CartItem set Cartid = ? AND ItemId = ? and quantity = ? "
                    + " where Id = ?";
            PreparedStatement p = connection.prepareCall(sql);
            p.setInt(1, b.getCart().getId());
            p.setInt(2, b.getItem().getId());
            
            p.setInt(3, b.getQuantity());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Object t) {
        try {
            CartItem b = (CartItem) t;
            String sql = "delete from CartItem where CartId =? and itemid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getCart().getId());
            p.setInt(2, b.getItem().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public CartItem searchById(int id) {
        CartItem b = new CartItem();
        try {
            String sql = "select * from CartItem where cartid = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
//                b.setCart(rs.getObject(1, Cart.class));
//                b.setItem(rs.getObject(2, Item.class));
                Cart cart = this.cdi.searchById(rs.getInt("CartId"));
                Item item = this.idi.searchById(rs.getInt("ItemId"));
                if(cart != null && item!= null){
                    b.setCart(cart);
                    b.setItem(item);
                }
                b.setQuantity(rs.getInt("quantity"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
}
