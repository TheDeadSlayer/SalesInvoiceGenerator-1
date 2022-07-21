/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salesinvoicegen.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author shahw
 */
public class InvoiceLineTableModel extends AbstractTableModel{
    
    private ArrayList <InvoiceLine> items;
    private String [] cols={"No.","Item","Price","Count","Total"};
    
    
    public InvoiceLineTableModel(ArrayList<InvoiceLine> items){
        this.items=items;
    }

    public ArrayList<InvoiceLine> getItems() {
        return items;
    }

    @Override
    public int getRowCount() {
        return this.items.size();
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
        InvoiceLine item= items.get(rowIndex);
        
        switch (columnIndex){
            case 0:return item.getInv().getNum();
            case 1:return item.getItem();
            case 2:return item.getPrice();
            case 3:return item.getCount();
            case 4:return item.getTotal();
        }
        return "";
    }
    
}
