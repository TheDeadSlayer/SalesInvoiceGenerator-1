/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salesinvoicegen.view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import salesinvoicegen.controller.InvoiceListener;

/**
 *
 * @author shahw
 */
public class NewInvoiceDialog extends JDialog {
    
    private JLabel custNameLbl;
    private JLabel dateLbl;
    private JTextField custNameTF;
    private JTextField dateTF;
    private JButton ok;
    private JButton cancel;
    
    public NewInvoiceDialog(SalesInvoiceGenFrame frame){
        
        
        setLayout(new GridLayout(3,2));
        custNameLbl=new JLabel("Customer Name:");
        dateLbl=new JLabel ("Date:");
        custNameTF= new JTextField(20);
        dateTF= new JTextField(20);
        ok= new JButton ("Ok");
        cancel =new JButton ("Cancel");
        
        ok.setActionCommand("Create New Invoice OK");
        ok.addActionListener(frame.getListener());
        cancel.setActionCommand("Create New Invoice CANCEL");
        cancel.addActionListener(frame.getListener());
        
        add(custNameLbl);
        add(custNameTF);
        add(dateLbl);
        add(dateTF);
        add(ok);
        add(cancel);
        
        setTitle("New Invoice");
        setSize(500,125);
        setLocation(400,350);  
    }

    public JTextField getCustNameTF() {
        return custNameTF;
    }

    public void setCustNameTF(JTextField custNameTF) {
        this.custNameTF = custNameTF;
    }

    public JTextField getDateTF() {
        return dateTF;
    }

    public void setDateTF(JTextField dateTF) {
        this.dateTF = dateTF;
    }
    
    
             
}
