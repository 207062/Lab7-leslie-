package it.polito.tdp.dizionario.model;

import java.util.*;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.dizionario.db.DizionarioDao;

public class Model {
	private List<String> dizionario;
	private int miaLunghezza;
	private UndirectedGraph<String, DefaultEdge> graph;

	public List<String> riempiDizionario(int lunghezza) {
		if (this.miaLunghezza != lunghezza) {
			this.miaLunghezza = lunghezza;
			DizionarioDao dao = new DizionarioDao();
			dizionario = dao.getParole(this.miaLunghezza);
		}
		return this.dizionario;
	}

	public UndirectedGraph<String,DefaultEdge> buildGraph(int lunghezza) {
		graph = new SimpleGraph<>(DefaultEdge.class);

		Graphs.addAllVertices(graph, this.riempiDizionario(lunghezza));

		for (String word1 : graph.vertexSet()) {
			for (String word2 : graph.vertexSet()) {
				if (areSimiles(word1, word2)) {
					graph.addEdge(word1, word2);
				}
			}

		}
		return graph;
	}

	private boolean areSimiles(String word1, String word2) {
		int diverso = 0;
		if (word1.equals(word2))
			return false;
		for (int i = 0; i < word1.length(); i++) {
			if (!word1.contains(String.valueOf(word2.charAt(i))))
				diverso++;
		}
		if (diverso > 1)
			return false;
		if (diverso == 1)
			return true;

		return false;
	}
	
	public boolean isValid(String input) {
		 boolean valid = true;
		 for(int i=0;i<input.length();i++){
		     if(!Character.isDigit(input.charAt(i)))
		          valid = false;
		 }
			return valid;
		}

	public List<String> getVicini(String parola) {
     if(!this.dizionario.contains(parola))	
		return null;
     return Graphs.neighborListOf(graph, parola);
	}
	
	public List<String> getTuttiConnessi(String word){
		 if(!this.dizionario.contains(word))
		  return null; 
	   BreadthFirstIterator<String, DefaultEdge> bfs = new BreadthFirstIterator<>(graph,word);
	   
	   List<String> tutti = new ArrayList<String>();
	     while(bfs.hasNext()){
	    	 tutti.add(bfs.next());
	     }
	  return tutti;
	  }
	
	public static void main(String[] args){
		Model m = new Model();
	  boolean bool=m.areSimiles("curo", "caro");
	  System.out.println(String.valueOf(bool));
	}

	public List<String> getCammino(String s1, String s2) {
		  if(!this.dizionario.contains(s1) || !this.dizionario.contains(s2))
				return null;
			  DijkstraShortestPath<String, DefaultEdge> dijkstra = new DijkstraShortestPath<>(graph, s1, s2);
			   
			  GraphPath<String,DefaultEdge> path = dijkstra.getPath();
			  if(path==null)
				return null;
		  return Graphs.getPathVertexList(path); 
	}

}
