package it.polito.tdp.dizionario.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.dizionario.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioController {
	private Model model;
	private int numLettere;
	private String parola;
	private UndirectedGraph<String,DefaultEdge> graph;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtNumLettere;

    @FXML
    private TextField txtParola;

    @FXML
    private TextField txtParolaArrivo;

    @FXML
    private Button btnGenera;

    @FXML
    private Button btnTrovaV;

    @FXML
    private Button btnTrovaC;

    @FXML
    private Button btnTrovaCammino;

    @FXML
    private TextArea txtOutput;

    @FXML
    private Button btnReset;

    @FXML
    void doGenera(ActionEvent event) {
        txtOutput.clear();
    	
        String input = txtNumLettere.getText();
    	
        if(input.length()==0){
    	txtOutput.appendText("Inserire la lunghezza della parola desiderata!!");
    	return;
    	}
     if(!model.isValid(input)){
      txtOutput.appendText("Formato della lunghezza errato!!");
      return;
     }
     numLettere = Integer.parseInt(input);
     graph=model.buildGraph(numLettere);
     txtOutput.appendText("Grafo per parole di lunghezza: "+numLettere+"\n");
     txtOutput.appendText(String.format("è stato generato un grafo con %d vertici e %d archi", graph.vertexSet().size(),graph.edgeSet().size()));
    }

    @FXML
    void doReset(ActionEvent event) {
    txtNumLettere.clear();
    txtParola.clear();
    txtParolaArrivo.clear();
    txtOutput.clear();
    btnGenera.setDisable(false);
    btnTrovaC.setDisable(false);
    btnTrovaV.setDisable(false);
    btnTrovaCammino.setDisable(false);
    }

    @FXML
    void doTrovaCammino(ActionEvent event) {
    	btnTrovaV.setDisable(true);
    	btnTrovaC.setDisable(true);
        txtOutput.clear();
    	
        parola = txtParola.getText();
        String arrivo = txtParolaArrivo.getText();
        if(parola.length()==0 || arrivo.length()==0){
         txtOutput.appendText("Inserire una parola di partenza e di arrivo");
         return;
        }
        if(parola.length()!=numLettere || arrivo.length()!=numLettere){
          	 txtOutput.appendText("Il grafo non è stato generato oppure La lunghezza delle parole inserite non corrisponde!!");
         	 return;
           }
        List<String> cammino = model.getCammino(parola,arrivo);
        if(cammino==null){
        txtOutput.appendText("Non esiste un cammino tra le parole inserite oppure non sono presenti nel Dizionario oppure il grafo non è stato generato");
        return;
        }
        txtOutput.appendText("***"+parola+"*** -- "+arrivo+"***("+cammino.size()+"passi");
        for(String s : cammino){
       	txtOutput.appendText("\t"+s+"\n");
        }
     
    }

    @FXML
    void doTrovaTuttiConnessi(ActionEvent event) {
      	btnTrovaC.setDisable(true);
    	btnGenera.setDisable(true);
    	btnTrovaCammino.setDisable(true);
        txtOutput.clear();
    
       parola = txtParola.getText();
	
    if(parola.length()==0){
	txtOutput.appendText("Inserire una parola !!!");
	return;
	}
    if(parola.length()!=numLettere){
   	 txtOutput.appendText("Il grafo non è stato generato oppure La lunghezza della parola inserita non corrisponde!!");
  	 return;
    }
    List<String> connessi = model.getTuttiConnessi(parola);
    if(connessi==null){
     txtOutput.appendText("La parola inserita non è presente nel dizionario");
     return;
    }
    txtOutput.appendText("Tutti i connessi a: "+parola+"\n");
    for(String str : connessi){
     txtOutput.appendText(str+"\n");
    }
    
    }

    @FXML
    void doTrovaVicini(ActionEvent event) {
    	btnTrovaC.setDisable(true);
    	btnGenera.setDisable(true);
    	btnTrovaCammino.setDisable(true);
        txtOutput.clear();
    
       parola = txtParola.getText();
	
    if(parola.length()==0){
	txtOutput.appendText("Inserire una parola !!!");
	return;
	}
    if(parola.length()!=numLettere){
   	 txtOutput.appendText("Il grafo non è stato generato oppure La lunghezza della parola inserita non corrisponde!!");
  	 return;
    }
    List<String> vicini = model.getVicini(parola);
    if(vicini==null){
     txtOutput.appendText("Parola non presente nel dizionario");
     return;
    }
    txtOutput.appendText("Tutti i vicini a: "+parola+"\n");
    for(String str : vicini){
     txtOutput.appendText(str+"\n");
    }
    }

    @FXML
    void initialize() {
        assert txtNumLettere != null : "fx:id=\"txtNumLettere\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert txtParola != null : "fx:id=\"txtParola\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert txtParolaArrivo != null : "fx:id=\"txtParolaArrivo\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnGenera != null : "fx:id=\"btnGenera\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnTrovaV != null : "fx:id=\"btnTrovaV\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnTrovaC != null : "fx:id=\"btnTrovaC\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnTrovaCammino != null : "fx:id=\"btnTrovaCammino\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Dizionario.fxml'.";

    }

	public void setModel(Model model) {
	 this.model= model;
	}
}