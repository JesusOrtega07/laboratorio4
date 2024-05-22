
package Model;


import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author dan
 */
public class Connexion {
    Connection conex = null; 
    Statement stmt = null;
    boolean b;

    public Connexion(){
        try {
        // Creando un objeto para el driver JDBC 
        Class.forName("org.postgresql.Driver");
        // Efectuando la conexion: donde está la BD NombreBD Usuario Password
        conex = DriverManager.getConnection("jdbc:postgresql://192.168.214.164/uacmerest","postgres","chivas1234");
        System.out.println("conexion establecida");
        stmt = (Statement) conex.createStatement();
            System.out.println("putito: "+stmt);
        b = true;
        } catch (Exception e ){
        System.out.println("Error al conectarse " + e.getMessage() );
        b = false;
        }finally{
        System.out.println("Base de datos conectada");
        }
        
        /*String sql = "select * from cuentacheques";
        ResultSet result = stmt.executeQuery(sql);
        while(result.next()){
            String ncuenta = result.getString(3);
            System.out.println("ncuenta: "+ncuenta);
        }*/
        }

        public Statement getConexion(){
            System.out.println("Stmt: "+stmt);
        return stmt;
    }
    public static void main(String[] args) throws SQLException {
        Connexion javaPostgreSQLBasic = new Connexion();
        javaPostgreSQLBasic.getConexion(); 
    }
    
}
