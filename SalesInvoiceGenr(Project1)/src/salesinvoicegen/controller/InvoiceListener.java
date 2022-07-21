/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salesinvoicegen.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import salesinvoicegen.model.Invoice;
import salesinvoicegen.model.InvoiceLine;
import salesinvoicegen.model.InvoiceLineTableModel;
import salesinvoicegen.model.InvoiceTableModel;
import salesinvoicegen.view.NewInvoiceDialog;
import salesinvoicegen.view.NewItemDialog;
import salesinvoicegen.view.SalesInvoiceGenFrame;

/**
 *
 * @author TheDeadSlayer
 */
public class InvoiceListener implements ActionListener, ListSelectionListener {
    
    private SalesInvoiceGenFrame frame;
    NewInvoiceDialog invDialog;
    NewItemDialog itemDialog;
    
    public  InvoiceListener(SalesInvoiceGenFrame frame)
    {
        this.frame=frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String command = e.getActionCommand();
        
        switch(command){
            case "Load File":loadFile(null,null);break;
            case "Save File":saveFile();break;
            case "Create New Invoice":newInv();break;
            case "Create New Invoice OK":newInvOk();break;
            case "Create New Invoice CANCEL":newInvCancel();break;
            case "Delete Invoice":delInv();break;
            case "Add Line":addLine();break;
            case "Create New Item OK":newLineOk();break;
            case "Create New Item CANCEL":newLineCancel();break;
            case "Delete Line":delLine();break;  
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        int idx= frame.getInvoiceTab().getSelectedRow();

        if(idx!= -1){
            Invoice inv= frame.getInvoices().get(idx);
            InvoiceLineTableModel invLineTM= new InvoiceLineTableModel(inv.getLines());
            frame.getInvoiceItemsTab().setModel(invLineTM);
            frame.getInvoiceNumLbl().setText(""+inv.getNum());
            frame.getInvoiceDateLbl().setText(""+SalesInvoiceGenFrame.df.format(inv.getDate()));
            frame.getCustNameLbl().setText(""+inv.getCustomer());
            frame.getInvoiceTotalLbl().setText(""+inv.getTotal());
            invLineTM.fireTableDataChanged();
        }
    }

    public void loadFile(String invoicePath, String invoiceItemsPath) {
        File invoiceFile= null;
        File invoiceItemsFile= null;
        

        if(invoicePath== null && invoiceItemsPath== null){
            JFileChooser fc= new JFileChooser();
            int res= fc.showOpenDialog(frame);

            if(res == JFileChooser.APPROVE_OPTION){
                invoiceFile= fc.getSelectedFile();

                res = fc.showOpenDialog(frame);
                if(res == JFileChooser.APPROVE_OPTION)
                    invoiceItemsFile= fc.getSelectedFile();
            }

        }
        else{
            invoiceFile= new File(invoicePath);
            invoiceItemsFile= new File(invoiceItemsPath);    
        }


        if( invoiceFile != null && invoiceItemsFile!= null){
            
            try {
                
                if(!invoiceFile.getName().contains(".csv")||!invoiceItemsFile.getName().contains(".csv")){
                    throw new IllegalArgumentException();
                }
                
                List <String> invoiceLines=Files.lines(Paths.get(invoiceFile.getAbsolutePath())).collect(Collectors.toList());
                List <String> invoiceItemsLines=Files.lines(Paths.get(invoiceItemsFile.getAbsolutePath())).collect(Collectors.toList());
                ArrayList<Invoice> invoices= new ArrayList<>();
                System.out.println("Invoice File: ");
                for (String line :invoiceLines){
                    System.out.println(line);
                    String [] words= line.split(",");
                    Invoice inv= new Invoice(Integer.parseInt(words[0]),words[2],SalesInvoiceGenFrame.df.parse(words[1]));
                    invoices.add(inv);
                }
                System.out.println("\nInvoice Items File: ");
                for (String line :invoiceItemsLines){
                    System.out.println(line);
                    String [] words= line.split(",");
                    Invoice inv=null;
                    int num= Integer.parseInt(words[0]);
                    String itemName=words[1];
                    double price= Double.parseDouble(words[2]);
                    int count=Integer.parseInt(words[3]);

                    for(Invoice invoice: invoices){
                        if(invoice.getNum()==num){
                            inv=invoice;
                            break;
                        }
                    }

                    InvoiceLine item= new InvoiceLine(inv,itemName,price,count);
                    inv.getLines().add(item);

                    InvoiceTableModel invTM= new InvoiceTableModel(invoices);
                    frame.setInvoices(invoices);
                    frame.setInvoiceTM(invTM);
                    frame.getInvoiceTab().setModel(invTM);
                    frame.getInvoiceTM().fireTableDataChanged();

                }
            } catch (FileNotFoundException ex ) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,"File Does not exisit","ERROR",JOptionPane.ERROR_MESSAGE);
           }catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,"Wrong File Format\nCorrect Format: .csv","ERROR",JOptionPane.ERROR_MESSAGE);
            }catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,"Some Kind of error","ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
}


    private void saveFile() {
        ArrayList<Invoice> invoices=frame.getInvoices();
        String invs="";
        String invoiceItems="";
        for(Invoice inv: invoices){
            invs+= ""+inv.getNum()+","+SalesInvoiceGenFrame.df.format(inv.getDate())+","+inv.getCustomer()+"\n";

            for(InvoiceLine line: inv.getLines()){
                invoiceItems+=""+inv.getNum()+","+line.getItem()+","+line.getPrice()+","+line.getCount()+"\n";
            }
        }

        JFileChooser fc= new JFileChooser();
        int res= fc.showOpenDialog(frame);

        if(res == JFileChooser.APPROVE_OPTION){

            String path= fc.getSelectedFile().getPath();
            FileWriter fw = null;

            try {
                 if(!fc.getSelectedFile().getName().contains(".csv")){
                    throw new IllegalArgumentException();
                }
                fw = new FileWriter(path);
                fw.write(invs);
                fw.flush();

                res= fc.showOpenDialog(frame);
                if(res == JFileChooser.APPROVE_OPTION){

                    String pathL= fc.getSelectedFile().getPath();
                    FileWriter fwL = null;

                    try {
                        fwL = new FileWriter(pathL);
                        fwL.write(invoiceItems);

                    }catch (FileNotFoundException ex){
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null,"File Does not exisit","ERROR",JOptionPane.ERROR_MESSAGE);
                    }catch (InvalidPathException ex){
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null,"Invlaid Path or Path does not exist","ERROR",JOptionPane.ERROR_MESSAGE);
                    }catch (IllegalArgumentException ex){
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null,"Wrong File Format\nCorrect Format: .csv","ERROR",JOptionPane.ERROR_MESSAGE);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null,"IO Error","ERROR",JOptionPane.ERROR_MESSAGE);
                     }finally {
                        try{fwL.close();} catch (IOException e){}
                    }
                }

            }catch (FileNotFoundException ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,"File Does not exisit","ERROR",JOptionPane.ERROR_MESSAGE);
            }catch (InvalidPathException ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,"Invlaid Path or Path does not exist","ERROR",JOptionPane.ERROR_MESSAGE);
            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,"Wrong File Format\nCorrect Formate: .csv","ERROR",JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,"IO Error","ERROR",JOptionPane.ERROR_MESSAGE);
            }finally {
                try{fw.close();} catch (IOException e){}
            }
        }
    }



    private void newInv() {
        invDialog= new NewInvoiceDialog(frame);
        invDialog.setVisible(true);
    }

    private void newInvOk() {
        try {
            int num=frame.getNewInvoiceNum();
            String name= invDialog.getCustNameTF().getText();
            Date date= SalesInvoiceGenFrame.df.parse(invDialog.getDateTF().getText());
            
            Invoice inv= new Invoice(num,name,date);
            frame.getInvoices().add(inv);
            frame.getInvoiceTM().fireTableDataChanged();
            invDialog.setVisible(false);
            invDialog.dispose();
            invDialog=null;
        } catch (ParseException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null,"Wrong Date Format","ERROR",JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }  
    }

    private void newInvCancel() {
        invDialog.setVisible(false);
        invDialog.dispose();
        invDialog=null;
    }
    
    private void delInv() {
        int idx= frame.getInvoiceTab().getSelectedRow();
        if(idx!=-1){
            frame.getInvoices().remove(idx);
            frame.getInvoiceTM().fireTableDataChanged();
            frame.getInvoiceNumLbl().setText("--");
            frame.getCustNameLbl().setText("--");
            frame.getInvoiceDateLbl().setText("--");
            frame.getInvoiceTotalLbl().setText("--");
            DefaultTableModel clear= new DefaultTableModel(0,0);
            frame.getInvoiceItemsTab().setModel(clear);
        }
    }

    private void addLine() {
        itemDialog= new NewItemDialog(frame);
        itemDialog.setVisible(true);
    }


    private void newLineOk() {
        
        String itemName= itemDialog.getItemNameTF().getText();
        double price= Double.parseDouble(itemDialog.getItemPriceTF().getText());
        int count= Integer.parseInt(itemDialog.getItemCountTF().getText());
        int idx= frame.getInvoiceTab().getSelectedRow();
        
        if(idx!=-1){
            Invoice inv = frame.getInvoices().get(idx);
            InvoiceLine  item = new InvoiceLine(inv,itemName,price,count);
            inv.getLines().add(item);
            
            InvoiceLineTableModel itemTM= (InvoiceLineTableModel) frame.getInvoiceItemsTab().getModel();
            itemTM.fireTableDataChanged();
            frame.getInvoiceTM().fireTableDataChanged();
        }
        
        itemDialog.setVisible(false);
        itemDialog.dispose();
        itemDialog=null;   
    }

    private void newLineCancel() {
        itemDialog.setVisible(false);
        itemDialog.dispose();
        itemDialog=null;  
    }
    
    private void delLine() {
        int idx= frame.getInvoiceItemsTab().getSelectedRow();
        if(idx!=-1){
            InvoiceLineTableModel itemTM= (InvoiceLineTableModel) frame.getInvoiceItemsTab().getModel();
            itemTM.getItems().remove(idx);
            frame.getInvoiceTM().fireTableDataChanged();
            itemTM.fireTableDataChanged();
        }           
    }
    
}
