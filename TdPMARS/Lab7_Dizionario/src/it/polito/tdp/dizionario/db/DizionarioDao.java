package it.polito.tdp.dizionario.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DizionarioDao {
 
	public List<String> getParole(int lunghezza){
	 List<String> list = new ArrayList<>();
	 String sql="select nome from parola where LENGTH(nome)=?";
	 Connection conn;
	 try {
		 conn= DBConnect.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, lunghezza);
		ResultSet rs = st.executeQuery();
	    while(rs.next()){
	     list.add(rs.getString("nome"));
	    }
	    conn.close();
	} catch (SQLException e) {
		e.printStackTrace();
	   throw new RuntimeException("Errore di connessione", e);
	}
	return list; 
 }
}
