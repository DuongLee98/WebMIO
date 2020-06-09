/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.util.ArrayList;
import model.Miostore;

/**
 *
 * @author kl
 */
public class MiostoreDAOImpl implements DAO{
private Connection connection;

    public MiostoreDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Miostore> getAll() {
        ArrayList<Miostore> rs = new ArrayList<>();

        String sql = "SELECT * FROM Miostore";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Miostore Miostore = new Miostore();
                Miostore.setCart(rss.getObject(1, Cart.class));
                Miostore.setItem(rss.getObject(2, Item.class));
                Miostore.setQuantity(rss.getInt("quantity"));

                rs.add(Miostore);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Miostore Miostore = (Miostore) t;
            
            String query = "INSERT INTO Miostore VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Miostore.getCart().getId());
            ps.setInt(2, Miostore.getItem().getId());
            ps.setInt(3, Miostore.getQuantity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
+
    }

    @Override
    public void edit(Object t) {
        try {
            Miostore b = (Miostore) t;
            String sql = "update Miostore set Paymentid = ? AND PayDate = ?"
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
            Miostore b = (Miostore) t;
            String sql = "delete from Miostore where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            p.setInt(2, b.getPaymentId().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Miostore searchById(int id) {
        Miostore b = new Miostore();
        try {
            String sql = "select * from Miostore where Id = ?";
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
