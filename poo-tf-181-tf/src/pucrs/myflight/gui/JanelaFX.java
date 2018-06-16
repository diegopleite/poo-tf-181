package pucrs.myflight.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.SwingUtilities;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pucrs.myflight.modelo.Aeronave;
import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.CiaAerea;
import pucrs.myflight.modelo.Geo;
import pucrs.myflight.modelo.GerenciadorAeronaves;
import pucrs.myflight.modelo.GerenciadorAeroportos;
import pucrs.myflight.modelo.GerenciadorCias;
import pucrs.myflight.modelo.GerenciadorPaises;
import pucrs.myflight.modelo.GerenciadorRotas;
import pucrs.myflight.modelo.Rota;

public class JanelaFX extends Application {

	final SwingNode mapkit = new SwingNode();

	private GerenciadorCias gerCias;
	private GerenciadorAeroportos gerAero;
	private GerenciadorRotas gerRotas;
	private GerenciadorAeronaves gerAvioes;

	private static GerenciadorMapa gerenciador;

	private EventosMouse mouse;

	private ObservableList<CiaAerea> comboCiasData;
	private ComboBox<CiaAerea> comboCia;

	@Override
	public  void start(Stage primaryStage) throws Exception {
		GerenciadorCias gerCias = new GerenciadorCias();
		GerenciadorAeroportos gerencAero = new GerenciadorAeroportos();
		GerenciadorRotas gerRotes = new GerenciadorRotas();
		GerenciadorPaises gerPais = new GerenciadorPaises();
		GerenciadorAeronaves gerAeronave = new GerenciadorAeronaves();

        try {
            gerPais.carregaDados("countries.dat");
        } catch (IOException e) {
           System.out.println("N�o foi possivel ler countries.dat!");
            System.exit(1);
        }
        try {
        	gerCias.carregaDados("airlines.dat");
        } catch (IOException e) {
           System.out.println("N�o foi possivel ler countries.dat!");
            System.exit(1);
        }
        try {
        	gerencAero.carregaDados("airports.dat");
        } catch (IOException e) {
           System.out.println("N�o foi possivel ler countries.dat!");
            System.exit(1);
        }
        try {
        	gerAeronave.carregaDados("equipment.dat");
        } catch (IOException e) {
           System.out.println("N�o foi possivel ler countries.dat!");
            System.exit(1);
        }
        try {
        	gerRotes.carregaDados("routes.dat");
        } catch (IOException e) {
           System.out.println("N�o foi possivel ler countries.dat!");
            System.exit(1);
        }
        
        
       
        
    	GeoPosition poa = new GeoPosition(-30.05, -51.18);
		gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
		mouse = new EventosMouse();
		gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
		gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);

        
       
       // System.out.println(trpoints1);
        int b = 0;
for (Rota r: GerenciadorRotas.listarTodas()) {
	
	System.out.println(r);
	if (b == 10) {
		break;
	}
	b=b+1;
}
		//setup();

	
		createSwingContent(mapkit);

		BorderPane pane = new BorderPane();
		GridPane leftPane = new GridPane();

		leftPane.setAlignment(Pos.CENTER);
		leftPane.setHgap(10);
		leftPane.setVgap(10);
		leftPane.setPadding(new Insets(10, 10, 10, 10));

		Button btnConsulta1 = new Button("Consulta 1");
		Button btnConsulta2 = new Button("Consulta 2");
		Button btnConsulta3 = new Button("Consulta 3");
		Button btnConsulta4 = new Button("Consulta 4");

		leftPane.add(btnConsulta1, 0, 0);
		leftPane.add(btnConsulta2, 2, 0);
		leftPane.add(btnConsulta3, 3, 0);
		leftPane.add(btnConsulta4, 4, 0);

		btnConsulta1.setOnAction(e -> {
			consulta1();
		});			

		pane.setCenter(mapkit);
		pane.setTop(leftPane);

		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Mapas com JavaFX");
		primaryStage.show();

	}

	// Inicializando os dados aqui...
	private void setup() {

		gerCias = new GerenciadorCias();
		gerAero = new GerenciadorAeroportos();
		gerRotas = new GerenciadorRotas();
		gerAvioes = new GerenciadorAeronaves();
	}
	public static void gerapontosecaminhos(CiaAerea a) {
		gerenciador.clear();
		for (Rota c : GerenciadorRotas.listarTodas()) {
			if (c.getCia().getCodigo().equals(a.getCodigo())) {
		for (Aeroporto b : GerenciadorAeroportos.listarTodas()) {
		        if(c.getDestino().getCodigo().equals(b.getCodigo())||c.getOrigem().getCodigo().equals(b.getCodigo())) {

		    		lstPoints.add(new MyWaypoint(Color.RED, b.getCodigo(), b.getLocal(), 5));
		    		trpoints1.add(b.getCodigo());
		    		
		        }
		        }
			}
		}
		for (Rota r : GerenciadorRotas.listarTodas()) {
			
		for (String s : trpoints1) {
			
			if (r.getOrigem().getCodigo().equals(s)) {
				
				for (String s2 : trpoints1) {
					if(r.getDestino().getCodigo().equals(s2)) {
						for (Aeroporto ae: GerenciadorAeroportos.listarTodas()) {
							if (ae.getCodigo().equals(s)) {
								origem.add(ae);
							}
						}
						for (Aeroporto ae: GerenciadorAeroportos.listarTodas()) {
							if (ae.getCodigo().equals(s2)) {
								destino.add(ae);
							}
						}
						
					}
				}
			}
		}
		}
		for (int i=0;i<origem.size();i++) {
		Tracado tr = new Tracado();
		tr.setWidth(1);
		tr.setCor(Color.BLUE);
	
		tr.addPonto(origem.get(i).getLocal());
		tr.addPonto(destino.get(i).getLocal());
		gerenciador.addTracado(tr);
		}
		}
	
	static ArrayList<MyWaypoint> lstPoints = new ArrayList<MyWaypoint>();
	static HashSet<String> trpoints1 = new HashSet<String>();
	static ArrayList<Aeroporto> origem = new ArrayList<Aeroporto>();
	static ArrayList<Aeroporto> destino = new ArrayList<Aeroporto>();
	private void consulta1() {

		// Lista para armazenar o resultado da consulta
		

		Aeroporto poa = new Aeroporto("POA", "Salgado Filho", new Geo(-29.9939, -51.1711));
		Aeroporto gru = new Aeroporto("GRU", "Guarulhos", new Geo(-23.4356, -46.4731));
		Aeroporto lis = new Aeroporto("LIS", "Lisbon", new Geo(38.772,-9.1342));
		Aeroporto mia = new Aeroporto("MIA", "Miami International", new Geo(25.7933, -80.2906));
		 CiaAerea a = new CiaAerea("G3");
		gerapontosecaminhos(a);
		
	/*	
		Tracado tr2 = new Tracado();
		tr2.setWidth(5);
		tr2.setCor(Color.BLUE);
		tr2.addPonto(gru.getLocal());
		tr2.addPonto(lis.getLocal());
		gerenciador.addTracado(tr2);
		*/
		// Adiciona os locais de cada aeroporto (sem repetir) na lista de
		// waypoints
		/*
		lstPoints.add(new MyWaypoint(Color.RED, poa.getCodigo(), poa.getLocal(), 5));
		lstPoints.add(new MyWaypoint(Color.RED, gru.getCodigo(), gru.getLocal(), 5));
		lstPoints.add(new MyWaypoint(Color.RED, lis.getCodigo(), lis.getLocal(), 5));
		lstPoints.add(new MyWaypoint(Color.RED, mia.getCodigo(), mia.getLocal(), 5));
*/
		// Para obter um ponto clicado no mapa, usar como segue:
		GeoPosition pos = gerenciador.getPosicao();


		// Informa o resultado para o gerenciador
		gerenciador.setPontos(lstPoints);

		// Quando for o caso de limpar os traçados...
		// gerenciador.clear();

		gerenciador.getMapKit().repaint();
	}

	private class EventosMouse extends MouseAdapter {
		private int lastButton = -1;

		@Override
		public void mousePressed(MouseEvent e) {
			JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
			GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());
			// System.out.println(loc.getLatitude()+", "+loc.getLongitude());
			lastButton = e.getButton();
			// Botão 3: seleciona localização
			if (lastButton == MouseEvent.BUTTON3) {
				gerenciador.setPosicao(loc);
				gerenciador.getMapKit().repaint();
			}
		}
    }

	private void createSwingContent(final SwingNode swingNode) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				swingNode.setContent(gerenciador.getMapKit());
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
