/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;

/**
 *
 * @author DuongLee
 */
public class AccountDAO implements DAO{
    
    private Connection connection;

    public AccountDAO(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public ArrayList<Account> getAll() {
        ArrayList<Account> rs = new ArrayList<>();
        
        String sql="SELECT * FROM account";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while(rss.next())
            {
                Account account = new Account();
                account.setUsrname(rss.getString("Usrname"));
                account.setId(rss.getInt("Id"));
                
                //account.setPersonId(personId);
                rs.add(account);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
}
