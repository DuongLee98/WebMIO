/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.util.ArrayList;
import model.Pricetag;

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
                Pricetag.setCart(rss.getObject(1, Cart.class));
                Pricetag.setItem(rss.getObject(2, Item.class));
                Pricetag.setQuantity(rss.getInt("quantity"));

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
            
            String query = "INSERT INTO Pricetag VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Pricetag.getCart().getId());
            ps.setInt(2, Pricetag.getItem().getId());
            ps.setInt(3, Pricetag.getQuantity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
+
    }

    @Override
    public void edit(Object t) {
        try {
            Pricetag b = (Pricetag) t;
            String sql = "update Pricetag set Paymentid = ? AND PayDate = ?"
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
            Pricetag b = (Pricetag) t;
            String sql = "delete from Pricetag where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            p.setInt(2, b.getPaymentId().getId());
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
                b.setPaymentId(rs.getObject(2, Payment.class));
                b.setPayDate(rs.getString("PayDate"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
}
