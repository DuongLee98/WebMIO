/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import model.Product;

/**
 *
 * @author DuongLee
 */
public class CartItem {
    public Product product;
    public int sl;

    public CartItem(Product product, int sl) {
        this.product = product;
        this.sl = sl;
    }
    
    
}
