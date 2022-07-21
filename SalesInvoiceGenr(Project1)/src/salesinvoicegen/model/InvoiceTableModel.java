/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salesinvoicegen.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import salesinvoicegen.view.SalesInvoiceGenFrame;

/**
 *
 * @author shahw
 */
public class InvoiceTableModel extends AbstractTableModel {
    
    private ArrayList <Invoice> invoices;
    private String [] cols={"No.","Date","Customer Date","Total"};
    
    
    public InvoiceTableModel(ArrayList<Invoice> invoices){
        this.invoices=invoices;
    }


    @Override
    public int getRowCount() {
        return this.invoices.size();
    }
    
    

    @Override
    public int getColumnCount() {
        return cols.length;
    }
    
    @Override
    public String getColumnName(int columnIndex){
        return cols[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Invoice inv= invoices.get(rowIndex);
        
        switch (columnIndex){
            case 0:return inv.getNum();
            case 1:return SalesInvoiceGenFrame.df.format(inv.getDate());
            case 2:return inv.getCustomer();
            case 3:return inv.getTotal();   
        }
        return "";
    }
    
}
