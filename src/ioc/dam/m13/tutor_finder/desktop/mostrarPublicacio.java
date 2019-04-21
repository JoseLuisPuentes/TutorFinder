/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ioc.dam.m13.tutor_finder.desktop;

import ioc.dam.m13.tutor_finder.client.TFClient;
import ioc.dam.m13.tutor_finder.client.TFClientImple;
import static ioc.dam.m13.tutor_finder.desktop.login.UserID;
import static ioc.dam.m13.tutor_finder.desktop.login.usuari;
import ioc.dam.m13.tutor_finder.dtos.UserDTO;

/**
 *
 * @author Alexandre
 */
public class mostrarPublicacio extends javax.swing.JFrame {
        //variable per saber quina fila de la taula s'ha seleccionat
        int filaSeleccionada = -1;
        //variable per saber el nom del usuari que ha accedit
        String userName;
    /**
     * Creates new form menuPrincipal
     */
    public mostrarPublicacio() {
        initComponents();
        //Fiquem els camps bloquejats que no es puguin modificar
        jTextAreaDescripcio.setLineWrap(true);
        jTextAreaDescripcio.setEditable(false);
        textTitol.setEditable(false); 
        comboCategoria.setEnabled(false);
        textPreu.setEditable(false);
        
        //variable per saber quin user id ha accedit a l'aplicació
        int UserID = login.UserID;
        //per saber el nom del usuari que ha accedit a l'aplicació
        userName = login.usuari;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    /**
     * Al initComponents tenim els titols i botons de la part del Menu Usuari
     * */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttornTornar = new javax.swing.JButton();
        labelTitol = new javax.swing.JLabel();
        labelPublicacions3 = new javax.swing.JLabel();
        textTitol = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaDescripcio = new javax.swing.JTextArea();
        textPreu = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        comboCategoria = new javax.swing.JComboBox<>();
        fons = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttornTornar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        buttornTornar.setText("Tornar");
        buttornTornar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttornTornarActionPerformed(evt);
            }
        });
        getContentPane().add(buttornTornar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 600, 120, 40));

        labelTitol.setFont(new java.awt.Font("Gigi", 0, 105)); // NOI18N
        labelTitol.setText("TutorFinder");
        getContentPane().add(labelTitol, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, 540, 90));

        labelPublicacions3.setFont(new java.awt.Font("Matura MT Script Capitals", 0, 36)); // NOI18N
        labelPublicacions3.setForeground(new java.awt.Color(0, 0, 204));
        labelPublicacions3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPublicacions3.setText("Publicació:");
        getContentPane().add(labelPublicacions3, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 280, 270, -1));
        getContentPane().add(textTitol, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 330, 450, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Titol: ");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 330, 50, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Descripció: ");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 380, 100, -1));

        jTextAreaDescripcio.setColumns(20);
        jTextAreaDescripcio.setRows(5);
        jScrollPane2.setViewportView(jTextAreaDescripcio);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 370, 450, 160));
        getContentPane().add(textPreu, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 580, 450, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Categoria: ");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 550, 90, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Preu: ");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 580, 50, -1));

        comboCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "                           ", "Física/Química", "Idiomes", "Informàtica", "Electricitat/Mecànica", "Dibuix", "Economía", "Arts escéniques", "Mùsica" }));
        getContentPane().add(comboCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 550, 450, -1));

        fons.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ioc/dam/m13/tutor_finder/desktop/fondo.jpg"))); // NOI18N
        getContentPane().add(fons, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Botó per sortir de l'aplicació
     * */
    private void buttornTornarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttornTornarActionPerformed
        // TODO add your handling code here:
        //boto per tornar al menu principal segons si ets usuari student, tutpor o admin
        TFClient l = new TFClientImple();
        UserDTO userData = l.userData(usuari);
        UserID=l.userData(usuari).getUserId();
        String userRole = userData.getUserRole();
            
            switch (userRole){
                case "student" :
                    this.dispose();
                    menuPrincipalClient formMenuClient = new menuPrincipalClient();
                    formMenuClient.setVisible(true);
                    break;
                
                case "tutor" :
                    this.dispose();
                    menuPrincipalProfessor formMenuProfessor = new menuPrincipalProfessor();
                    formMenuProfessor.setVisible(true);
                    break;
                    
                case "admin" :
                    this.dispose();
                    gestioPublicacions formMenuGestioPublicacions = new gestioPublicacions();
                    formMenuGestioPublicacions.setVisible(true);
                    break;
            }
            this.dispose();
        
    }//GEN-LAST:event_buttornTornarActionPerformed

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
            java.util.logging.Logger.getLogger(mostrarPublicacio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mostrarPublicacio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mostrarPublicacio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mostrarPublicacio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
     

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mostrarPublicacio().setVisible(true);
            }
        });
    }
    
   
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttornTornar;
    public javax.swing.JComboBox<String> comboCategoria;
    private javax.swing.JLabel fons;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTextArea jTextAreaDescripcio;
    private javax.swing.JLabel labelPublicacions3;
    private javax.swing.JLabel labelTitol;
    public javax.swing.JTextField textPreu;
    public javax.swing.JTextField textTitol;
    // End of variables declaration//GEN-END:variables
}