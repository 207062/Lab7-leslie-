package it.polito.tdp.dizionario.db;

import java.sql.*;

public class DBConnect {
	private static final String jdbcURL = "jdbc:mysql://localhost/dizionario?user=root&password=root";
	 public static Connection getConnection(){
		  Connection conn;
		   try {
			conn = DriverManager.getConnection(jdbcURL);
			return conn;                                     // Se va tutto bene ritorno la coonessione
		   } catch (SQLException e) {
			e.printStackTrace();
			 throw new RuntimeException("Errore nella connessione", e); // Se non va bene questo metodo non in grado di gestire cosa
		     }                                                         // non va allora chiamo il chiamante errore(in qto caso è runtime che gestirà lui l'eccezione)
		  }

}
