/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salesinvoicegen.model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author shahw
 */
public class Invoice {
    private int num;
    private String customer;
    private Date date;
    private ArrayList <InvoiceLine> lines;

    public Invoice(int num, String customer, Date date) {
        this.num = num;
        this.customer = customer;
        this.date = date;
    }
    
    
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public ArrayList <InvoiceLine> getLines() {
        if(lines==null)
            lines=new ArrayList<>();
        return lines;
    }

    public void setLines(ArrayList <InvoiceLine> lines) {
        this.lines = lines;
    }
    
    
    public double getTotal()
    {
        double total= 0.0;
        for (InvoiceLine line:getLines())
            total+=line.getTotal();
        return total;
    }  
    
}
