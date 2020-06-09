/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.util.ArrayList;
import model.Fullname;

/**
 *
 * @author kl
 */
public class FullnameDAOImpl implements DAO{
private Connection connection;

    public FullnameDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Fullname> getAll() {
        ArrayList<Fullname> rs = new ArrayList<>();

        String sql = "SELECT * FROM Fullname";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Fullname Fullname = new Fullname();
                Fullname.setCart(rss.getObject(1, Cart.class));
                Fullname.setItem(rss.getObject(2, Item.class));
                Fullname.setQuantity(rss.getInt("quantity"));

                rs.add(Fullname);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Fullname Fullname = (Fullname) t;
            
            String query = "INSERT INTO Fullname VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Fullname.getCart().getId());
            ps.setInt(2, Fullname.getItem().getId());
            ps.setInt(3, Fullname.getQuantity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
+
    }

    @Override
    public void edit(Object t) {
        try {
            Fullname b = (Fullname) t;
            String sql = "update Fullname set Paymentid = ? AND PayDate = ?"
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
            Fullname b = (Fullname) t;
            String sql = "delete from Fullname where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            p.setInt(2, b.getPaymentId().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Fullname searchById(int id) {
        Fullname b = new Fullname();
        try {
            String sql = "select * from Fullname where Id = ?";
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
