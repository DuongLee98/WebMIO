/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.util.ArrayList;
import model.Standardshipment;

/**
 *
 * @author kl
 */
public class StandardshipmentDAOImpl implements DAO{
private Connection connection;

    public StandardshipmentDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Standardshipment> getAll() {
        ArrayList<Standardshipment> rs = new ArrayList<>();

        String sql = "SELECT * FROM Standardshipment";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Standardshipment Standardshipment = new Standardshipment();
                Standardshipment.setCart(rss.getObject(1, Cart.class));
                Standardshipment.setItem(rss.getObject(2, Item.class));
                Standardshipment.setQuantity(rss.getInt("quantity"));

                rs.add(Standardshipment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Standardshipment Standardshipment = (Standardshipment) t;
            
            String query = "INSERT INTO Standardshipment VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Standardshipment.getCart().getId());
            ps.setInt(2, Standardshipment.getItem().getId());
            ps.setInt(3, Standardshipment.getQuantity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
+
    }

    @Override
    public void edit(Object t) {
        try {
            Standardshipment b = (Standardshipment) t;
            String sql = "update Standardshipment set Paymentid = ? AND PayDate = ?"
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
            Standardshipment b = (Standardshipment) t;
            String sql = "delete from Standardshipment where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            p.setInt(2, b.getPaymentId().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Standardshipment searchById(int id) {
        Standardshipment b = new Standardshipment();
        try {
            String sql = "select * from Standardshipment where Id = ?";
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
