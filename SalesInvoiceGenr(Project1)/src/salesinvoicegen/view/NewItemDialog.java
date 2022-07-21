/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salesinvoicegen.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author shahw
 */
public class NewItemDialog extends JDialog {
    private JLabel itemName;
    private JLabel itemPrice;
    private JLabel itemCount;
    private JTextField itemNameTF;
    private JTextField itemPriceTF;
    private JTextField itemCountTF;
    private JButton ok;
    private JButton cancel;
    
    public NewItemDialog(SalesInvoiceGenFrame frame){
        
        
        setLayout(new GridLayout(4,2));
        itemName=new JLabel("Item Name:");
        itemPrice=new JLabel ("Item Price:");
        itemCount=new JLabel ("Count:");
        itemNameTF= new JTextField(20);
        itemPriceTF= new JTextField(20);
        itemCountTF= new JTextField(20);
        ok= new JButton ("Ok");
        cancel =new JButton ("Cancel");
        
        ok.setActionCommand("Create New Item OK");
        ok.addActionListener(frame.getListener());
        cancel.setActionCommand("Create New Item CANCEL");
        cancel.addActionListener(frame.getListener());
        
        add(itemName);
        add(itemNameTF);
        add(itemPrice);
        add(itemPriceTF);
        add(itemCount);
        add(itemCountTF);
        add(ok);
        add(cancel);
        
        setTitle("New Item");
        setSize(500,175);
        setLocation(400,350);  
    }

    public JTextField getItemNameTF() {
        return itemNameTF;
    }

    public JTextField getItemPriceTF() {
        return itemPriceTF;
    }

    public JTextField getItemCountTF() {
        return itemCountTF;
    }

    
}
