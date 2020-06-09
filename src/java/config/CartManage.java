/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.util.ArrayList;

/**
 *
 * @author DuongLee
 */
public class CartManage {
    public static ArrayList<CartItem> cart = new ArrayList<>();
    
    public static void addCart(CartItem ci)
    {
        for (int i=0; i<cart.size(); i++)
        {
            if (cart.get(i).product.getId() == ci.product.getId())
            {
                cart.get(i).sl += ci.sl;
                return;
            }
        }
        cart.add(ci);
    }
    
    public static int total()
    {
        int rs = 0;
        for (int i=0; i<cart.size(); i++)
        {
            rs += cart.get(i).product.getUnitPrice()*cart.get(i).sl;
        }
        return rs;
    }
    
    public static int slp()
    {
        int rs = 0;
        for (int i=0; i<cart.size(); i++)
        {
            rs += cart.get(i).sl;
        }
        return rs;
    }
}
