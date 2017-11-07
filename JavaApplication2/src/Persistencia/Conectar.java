/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

/**
 *
 * @author 9alej
 */

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.Statement; 
import javax.swing.JOptionPane; 
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;


public class Conectar { 
    Connection conexion; 
    Statement sentencia;
    private final String NOMBRE_BASE_DE_DATOS = "C:\\Users\\9alej\\Documents\\NetBeansProjects\\ISO\\Proyecto\\Proyecto\\MaGeSft\\JavaApplication2\\BD\\BBDDIPO.accdb";
    private final String CLAVE_BASE_DE_DATOS = "";
    private final String USUARIO_BASE_DE_DATOS = "admin";
   // private final String USUARIO_BASE_DE_DATOS = "admin";

 public void PrepararBaseDatos() { 
        try{ 
            conexion=DriverManager.getConnection("jdbc:ucanaccess://"+this.NOMBRE_BASE_DE_DATOS,this.USUARIO_BASE_DE_DATOS,this.CLAVE_BASE_DE_DATOS);
            
        } 
        catch (Exception e) { 
            JOptionPane.showMessageDialog(null,"Error al realizar la conexion "+e);
        } 
        try { 
            sentencia=conexion.createStatement( 
                    ResultSet.TYPE_SCROLL_INSENSITIVE, 
                    ResultSet.CONCUR_READ_ONLY); 
            
            System.out.println("Se ha establecido la conexión correctamente");
        } 
        catch (Exception e) { 
            JOptionPane.showMessageDialog(null,"Error al crear el objeto sentencia "+e);
        } 
    }
 
 public void desconectar(){
        try {
            conexion.close();            
            System.out.println("La conexion a la base de datos a terminado");
        } catch (SQLException ex) {
            System.out.println( ex.getMessage() );
        }       
   }
 
 public boolean verificarContraseña(String User, String Pass){
        String Nombre = "";
        String Clave = "";
   
        String sql = " SELECT Nombre, Clave "
                 + " FROM Login "
                 + " WHERE (((Nombre) Like '" + User + "%') AND ((Clave) Like '" + Pass + "%')) ";
        try{
            PreparedStatement pstm = conexion.prepareStatement( sql );
            ResultSet res = pstm.executeQuery();            
             while(res.next())
             {            
                Nombre = res.getString( "Nombre" );
                Clave = res.getString( "Clave" );
             }
         res.close();         
        }catch( SQLException e ){
            System.err.println( e.getMessage() );
        }
        
        return (User.equals(Nombre) && Pass.equals(Clave));
               
    }

 public void CambiarContraseña (String claveAntigua, String claveNueva, String usuario){
        String sql = 
         "UPDATE Login SET Clave = '" + claveNueva + "' WHERE Nombre='" + usuario + "';";
        System.out.println(sql);
        System.out.println(usuario);
        try{
            PreparedStatement pstm = conexion.prepareCall( sql );      
            int res = pstm.executeUpdate();
            pstm.close();
        }catch( SQLException e ){
            System.err.println( e.getMessage() );
        }
    }
}
