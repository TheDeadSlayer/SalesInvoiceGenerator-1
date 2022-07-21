/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salesinvoicegen.model;

/**
 *
 * @author shahw
 */
public class InvoiceLine {
    private Invoice inv;
    private String item;
    private double price;
    private int count;

    public InvoiceLine(Invoice inv, String item, double price, int count){
        this.inv = inv;
        this.item = item;
        this.price = price;
        this.count = count;
        
    }
    
    

    public Invoice getInv() {
        return inv;
    }

    public void setInv(Invoice inv) {
        this.inv = inv;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public double getTotal()
    {
        return price*count;
    }

     
}
