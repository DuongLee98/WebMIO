/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author kl
 */
public class PantsDAOImpl implements DAO{
private Connection connection;

    public PantsDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public ArrayList<Pants> getAll() {
        ArrayList<Pants> rs = new ArrayList<>();

        String sql = "SELECT * FROM Pants";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Pants Pants = new Pants();
                Pants.setCart(rss.getObject(1, Cart.class));
                Pants.setItem(rss.getObject(2, Item.class));
                Pants.setQuantity(rss.getInt("quantity"));

                rs.add(Pants);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Pants Pants = (Pants) t;
            
            String query = "INSERT INTO Pants VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Pants.getCart().getId());
            ps.setInt(2, Pants.getItem().getId());
            ps.setInt(3, Pants.getQuantity());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
+
    }

    @Override
    public void edit(Object t) {
        try {
            Pants b = (Pants) t;
            String sql = "update Pants set Paymentid = ? AND PayDate = ?"
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
            Pants b = (Pants) t;
            String sql = "delete from Pants where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, b.getId());
            p.setInt(2, b.getPaymentId().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Pants searchById(int id) {
        Pants b = new Pants();
        try {
            String sql = "select * from Pants where Id = ?";
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
