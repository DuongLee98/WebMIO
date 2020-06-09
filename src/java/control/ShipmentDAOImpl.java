/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.util.ArrayList;
import model.Shipment;

/**
 *
 * @author kl
 */
public class ShipmentDAOImpl implements DAO{
private Connection connection;

    public ShipmentDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Shipment> getAll() {
        ArrayList<Shipment> rs = new ArrayList<>();

        String sql = "SELECT * FROM Shipment";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Shipment Shipment = new Shipment();
                Shipment.setCart(rss.getObject(1, Cart.class));
                Shipment.setItem(rss.getObject(2, Item.class));
                Shipment.setQuantity(rss.getInt("quantity"));

                rs.add(Shipment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Shipment Shipment = (Shipment) t;
            
            String query = "INSERT INTO Shipment VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Shipment.getCart().getId());
            ps.setInt(2, Shipment.getItem().getId());
            ps.setInt(3, Shipment.getQuantity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
+
    }

    @Override
    public void edit(Object t) {
        try {
            Shipment b = (Shipment) t;
            String sql = "update Shipment set Paymentid = ? AND PayDate = ?"
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
            Shipment b = (Shipment) t;
            String sql = "delete from Shipment where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            p.setInt(2, b.getPaymentId().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Shipment searchById(int id) {
        Shipment b = new Shipment();
        try {
            String sql = "select * from Shipment where Id = ?";
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
