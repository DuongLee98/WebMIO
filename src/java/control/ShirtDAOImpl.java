/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.util.ArrayList;
import model.Shirt;

/**
 *
 * @author kl
 */
public class ShirtDAOImpl implements DAO{
private Connection connection;

    public ShirtDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Shirt> getAll() {
        ArrayList<Shirt> rs = new ArrayList<>();

        String sql = "SELECT * FROM Shirt";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Shirt Shirt = new Shirt();
                Shirt.setCart(rss.getObject(1, Cart.class));
                Shirt.setItem(rss.getObject(2, Item.class));
                Shirt.setQuantity(rss.getInt("quantity"));

                rs.add(Shirt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Shirt Shirt = (Shirt) t;
            
            String query = "INSERT INTO Shirt VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Shirt.getCart().getId());
            ps.setInt(2, Shirt.getItem().getId());
            ps.setInt(3, Shirt.getQuantity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
+
    }

    @Override
    public void edit(Object t) {
        try {
            Shirt b = (Shirt) t;
            String sql = "update Shirt set Paymentid = ? AND PayDate = ?"
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
            Shirt b = (Shirt) t;
            String sql = "delete from Shirt where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            p.setInt(2, b.getPaymentId().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Shirt searchById(int id) {
        Shirt b = new Shirt();
        try {
            String sql = "select * from Shirt where Id = ?";
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
