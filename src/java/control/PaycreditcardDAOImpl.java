/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.util.ArrayList;
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
                Paycreditcard.setCart(rss.getObject(1, Cart.class));
                Paycreditcard.setItem(rss.getObject(2, Item.class));
                Paycreditcard.setQuantity(rss.getInt("quantity"));

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
            ps.setInt(1, Paycreditcard.getCart().getId());
            ps.setInt(2, Paycreditcard.getItem().getId());
            ps.setInt(3, Paycreditcard.getQuantity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
+
    }

    @Override
    public void edit(Object t) {
        try {
            Paycreditcard b = (Paycreditcard) t;
            String sql = "update Paycreditcard set Paymentid = ? AND PayDate = ?"
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
            Paycreditcard b = (Paycreditcard) t;
            String sql = "delete from Paycreditcard where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            p.setInt(2, b.getPaymentId().getId());
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
