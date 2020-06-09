/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.util.ArrayList;
import model.Myorder;

/**
 *
 * @author kl
 */
public class MyorderDAOImpl implements DAO{
private Connection connection;

    public MyorderDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Myorder> getAll() {
        ArrayList<Myorder> rs = new ArrayList<>();

        String sql = "SELECT * FROM Myorder";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Myorder Myorder = new Myorder();
                Myorder.setCart(rss.getObject(1, Cart.class));
                Myorder.setItem(rss.getObject(2, Item.class));
                Myorder.setQuantity(rss.getInt("quantity"));

                rs.add(Myorder);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Myorder Myorder = (Myorder) t;
            
            String query = "INSERT INTO Myorder VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Myorder.getCart().getId());
            ps.setInt(2, Myorder.getItem().getId());
            ps.setInt(3, Myorder.getQuantity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
+
    }

    @Override
    public void edit(Object t) {
        try {
            Myorder b = (Myorder) t;
            String sql = "update Myorder set Paymentid = ? AND PayDate = ?"
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
            Myorder b = (Myorder) t;
            String sql = "delete from Myorder where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            p.setInt(2, b.getPaymentId().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Myorder searchById(int id) {
        Myorder b = new Myorder();
        try {
            String sql = "select * from Myorder where Id = ?";
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
