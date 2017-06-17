/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.io.IOException;
import java.util.ResourceBundle.Control;

import javax.swing.JOptionPane;

import Control.CityPoly;
import Control.GameController;
import Control.Jugador;
import Estructuras.ArchivoSecuencial;
import Estructuras.BPlusTree;

/**
 *
 * @author Daar375
 */
public class LoginWindow extends javax.swing.JFrame {

	/**
	 * Creates new form Login
	 */
	private CityPoly Control;
	
	public LoginWindow(CityPoly bcontrol) throws ClassNotFoundException, IOException {
		ArchivoSecuencial usertree = new ArchivoSecuencial();
		initComponents();
		Control = bcontrol;

			Control.setPlayers(		 usertree.LeerSecuenciaUsuarios()	);
		
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        Usuario = new javax.swing.JTextField();
        CrearCuentaB = new javax.swing.JButton();
        LogInB = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        UiContrasenha = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsuarioActionPerformed(evt);
            }
        });

        CrearCuentaB.setText("Crear Cuenta");
        CrearCuentaB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearCuentaBActionPerformed(evt);
            }
        });

        LogInB.setText("Log in");
        LogInB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogInBActionPerformed(evt);
            }
        });

        jLabel1.setText("Usuario");

        jLabel2.setText("Contraseņa");

        UiContrasenha.setText("jPasswordField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(UiContrasenha))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addComponent(Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(59, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LogInB)
                .addGap(18, 18, 18)
                .addComponent(CrearCuentaB)
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(UiContrasenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CrearCuentaB)
                    .addComponent(LogInB))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

	private void UsuarioActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void UiContrasenhaActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void LogInBActionPerformed(java.awt.event.ActionEvent evt) {
		if (!Control.logIn(Usuario.getText(), UiContrasenha.getText())) {
			JOptionPane.showMessageDialog(null, "Wrong user or pass");
		}

		else if(Control.getPlayer1()!=null &&Control.getPlayer2()!=null){
			Control.getGame();
			Control.getGameWindow().setVisible(true);
			Control.getGameWindow().setPlayerLabel(Control.getPlayer1().getName(),Control.getPlayer2().getName());
			dispose();
		}

	}

	private void CrearCuentaBActionPerformed(java.awt.event.ActionEvent evt) {
		if (!Control.newPlayer(Usuario.getText(), UiContrasenha.getText())) {
			
			JOptionPane.showMessageDialog(null, "User already exist");
		}
		JOptionPane.showMessageDialog(null, "User Created");


	}

	/**
	 * @param args
	 *            the command line arguments
	 */

	// Variables declaration - do not modify
    private javax.swing.JPasswordField UiContrasenha;
    private javax.swing.JButton CrearCuentaB;
    private javax.swing.JButton LogInB;
    private javax.swing.JTextField Usuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
	// End of variables declaration
}
