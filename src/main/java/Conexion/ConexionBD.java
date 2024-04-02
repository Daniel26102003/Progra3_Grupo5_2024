/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Esaú
 */

public class ConexionBD {
    Connection conn;
    private String host = "localhost";
    private String port = "3308";
    private String dbName = "proyectogrupo5";
    private String userName = "root";
    private String password = "Grupo5";
    
    public ConexionBD (){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dbName;
            conn = DriverManager.getConnection(url, this.userName, this.password);
            System.out.println("Conexion exitosa");
        }catch (ClassNotFoundException | SQLException e){
            System.out.println("Error no se conecto");   
        }
            
    }
    
}

