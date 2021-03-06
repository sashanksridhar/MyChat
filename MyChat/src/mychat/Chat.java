/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mychat;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Sashank
 */


public class Chat extends javax.swing.JFrame {
    long id_src,id_des ;
    String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "mychat";
        String pass = "password";
        long id ;
        int flag = 0;
    /**
     * Creates new form Chat
     */
    
        class Load implements Runnable{
long id_src,id_des ;
    String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "mychat";
        String pass = "password";
        long id ;
        int flag = 0;
    public Load(long i,long j) {
        id_src = i;
        id_des = j;
        
    }

    @Override
    public void run() {
        try
        {
            // Displaying the thread that is running
        try
        {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            String sql;
            
            //Reference to connection interface
             Connection con=null;
            
       StyledDocument doc = chatWindow.getStyledDocument();
SimpleAttributeSet right = new SimpleAttributeSet();
StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
SimpleAttributeSet left = new SimpleAttributeSet();
StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
Style style = chatWindow.addStyle("I'm a Style", null);
        
             while(true){ 
                 
                con = DriverManager.getConnection(url,user,pass);
      chatWindow.setText(null);
            sql = "SELECT msg,id FROM CHAT where cid in(select cid from chatting where id = "+id_src+"and contact = "+id_des+")" +"order by cid asc";
             Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String msg = rs.getString("msg");
                Long id = rs.getLong("id");
               
                if(id == id_src)
                {
                //System.out.println("right");
                    doc.setParagraphAttributes(doc.getLength(),1, right, false);
                    StyleConstants.setForeground(style, Color.red);
                    doc.insertString(doc.getLength(),msg + "\n",style);
                }
                else
                {
                  //  System.out.print("hi");
                    doc.setParagraphAttributes( doc.getLength(),1, left, false);
                    StyleConstants.setForeground(style, Color.blue);
                    doc.insertString(doc.getLength(),msg + "\n",style);
                }
            
            }
            con.close();
            Thread.sleep(5000);
            
        }
            
        }
            catch(Exception e)
        {
            e.printStackTrace();
        }
           }
        
        catch (Exception e)
        {
            // Throwing an exception
            System.out.println ("Exception is caught");
            e.printStackTrace();
        }
    }

}
        public Chat(long i,long j) {
        id_src = i;
        id_des = j;
        initComponents();
        Thread object = new Thread(new Load(id_src,id_des));
            object.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        chatWindow = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        chatArea.setColumns(20);
        chatArea.setRows(5);
        jScrollPane2.setViewportView(chatArea);

        sendButton.setText("send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        chatWindow.setEditable(false);
        jScrollPane3.setViewportView(chatWindow);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        Connection con=null;
        try
        {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            String sql;
            
            //Reference to connection interface
            con = DriverManager.getConnection(url,user,pass);
            String chat = chatArea.getText();
            sql = "SELECT MAX(CID) FROM CHAT";
            
            Statement st = con.createStatement();
            
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            long  chatid = rs.getLong("max(cid)") + 1;
            sql = "INSERT INTO chat VALUES("+chatid+",'"+chat+"',"+id_src+")";
            int m = st.executeUpdate(sql);
            sql = "INSERT INTO chatting VALUES(" + id_src +","+chatid +","+id_des+")";
            m = st.executeUpdate(sql);
            sql = "INSERT INTO chatting VALUES(" + id_des +","+chatid +","+id_src+")";
            m = st.executeUpdate(sql);
           
            con.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_sendButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Chat(0,0).setVisible(true);
               
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea chatArea;
    private javax.swing.JTextPane chatWindow;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables
}
