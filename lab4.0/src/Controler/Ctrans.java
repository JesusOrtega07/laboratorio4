/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controler;

import Model.Connexion;
import Model.CuentaCheque;
import view.VTransacciones;
import view.Vticket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import view.VTransacciones;
import view.Vticket;
/**
 *
 * @author dan
 */
public class Ctrans implements ActionListener{
    VTransacciones trans;
    Connexion conex = new Connexion();
    Statement t;
    
    public Ctrans(VTransacciones trans) {
        this.trans = trans;
        this.trans.jButton1.addActionListener(this);
        this.trans.jButton2.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.trans.jButton1 == e.getSource()){
            System.out.println("esto hace las transacciones");
            // Obtener texto de los JTextField//
            String textoCuenta = trans.jTextField1.getText();
            String textoCheque = trans.jTextField2.getText();
            String textoTotal = trans.jTextField3.getText();
            String textoCurso = trans.jComboBox1.getSelectedItem().toString();
           
            // Convertir texto a tipo int//
            int cuenta = Integer.parseInt(textoCuenta);
            int cheque = Integer.parseInt(textoCheque);
            int total = Integer.parseInt(textoTotal);
            int curso = Integer.parseInt(textoCurso);
            
            CuentaCheque cuentac = new CuentaCheque(cuenta, cheque, total, curso);//creamos el obj de tipo CuentaCheque 
            
            //datos en consola//
            System.out.println("a la cuenta "+cuentac.getCuenta());
            System.out.println("a la cheque "+cuentac.getCheque());
            System.out.println("a la total "+cuentac.getTotal());
            System.out.println("a la curso "+cuentac.getCurso());
            
             t = conex.getConexion();
             System.out.println("t: "+t);
                try {
                int x = t.executeUpdate("Select AltaDeCheque(" + cuenta + ", " + cheque + ", " + total + ", " + curso + ")");
                }
                catch (Exception ex){
                System.out.println(ex.getMessage() );
                }
                
             Vticket tik = new Vticket();
             tik.setVisible(true);
              if (textoCurso.equals("10")) {
                    // Acciones para la selección de "Opción 1"
                    System.out.println("Seleccionaste la Opción 1");
                    tik.jTextField5.setText("JAVA");
            } else if (textoCurso.equals("20")) {
                    // Acciones para la selección de "Opción 2"
                    System.out.println("Seleccionaste la Opción 2");
                    tik.jTextField5.setText("DISEÑO");
            }
             tik.jTextField1.setText(textoCuenta);
             tik.jTextField2.setText(textoCheque);
             tik.jTextField3.setText(textoTotal);
             tik.jTextField4.setText(textoCurso);
             

        }else if(this.trans.jButton2 == e.getSource()){
            
            System.out.println("yo limpio los textfield");
            trans.jTextField1.setText("");
            trans.jTextField2.setText("");
            trans.jTextField3.setText("");
        }   
    }
}
