package pucrs.myflight.gui;

import java.awt.Color;
import java.awt.Font;
import javafx.scene.control.TextField;
//import java.awt.TextField;
import java.awt.event.ActionEvent;
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

import javax.swing.JComboBox;
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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pucrs.myflight.modelo.Aeronave;
import pucrs.myflight.modelo.Aeroporto;
import pucrs.myflight.modelo.CiaAerea;
import pucrs.myflight.modelo.Geo;
import pucrs.myflight.modelo.consulta3;
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
	static ArrayList<MyWaypoint> lstPoints = new ArrayList<MyWaypoint>();
	static HashSet<String> trpoints1 = new HashSet<String>();
	static ArrayList<Aeroporto> origem = new ArrayList<Aeroporto>();
	static ArrayList<Aeroporto> destino = new ArrayList<Aeroporto>();
	static String consulta = "0";

	@Override
	public void start(Stage primaryStage) throws Exception {
		GerenciadorCias gerCias = new GerenciadorCias();
		GerenciadorAeroportos gerencAero = new GerenciadorAeroportos();
		GerenciadorRotas gerRotes = new GerenciadorRotas();
		GerenciadorPaises gerPais = new GerenciadorPaises();
		GerenciadorAeronaves gerAeronave = new GerenciadorAeronaves();

		try {
			gerPais.carregaDados("countries.dat");
		} catch (IOException e) {
			System.out.println("Nao foi possivel ler countries.dat!");
			System.exit(1);
		}
		try {
			gerCias.carregaDados("airlines.dat");
		} catch (IOException e) {
			System.out.println("Nao foi possivel ler countries.dat!");
			System.exit(1);
		}
		try {
			gerencAero.carregaDados("airports.dat");
		} catch (IOException e) {
			System.out.println("Nao foi possivel ler countries.dat!");
			System.exit(1);
		}
		try {
			gerAeronave.carregaDados("equipment.dat");
		} catch (IOException e) {
			System.out.println("Nao foi possivel ler countries.dat!");
			System.exit(1);
		}
		try {
			gerRotes.carregaDados("routes.dat");
		} catch (IOException e) {
			System.out.println("Nao foi possivel ler countries.dat!");
			System.exit(1);
		}

		// ArrayList<consulta3> c = new ArrayList<consulta3>();
		// janelaEscolhe(c);
		// aulas();
		GeoPosition poa = new GeoPosition(-30.05, -51.18);
		gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
		mouse = new EventosMouse();
		gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
		gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);

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
		Button btnConsulta3b = new Button("Selecionar rota");
		Button btnConsulta4 = new Button("Consulta 4");
		Button btnlimpar = new Button("Limpar");
		Button btnSair = new Button("Sair");

		leftPane.add(btnConsulta1, 0, 0);
		leftPane.add(btnConsulta2, 2, 0);
		leftPane.add(btnConsulta3, 3, 0);
		leftPane.add(btnConsulta3b, 4, 0);
		leftPane.add(btnConsulta4, 5, 0);
		leftPane.add(btnlimpar, 6, 0);
		leftPane.add(btnSair, 7, 0);
		

		btnConsulta1.setOnAction(e -> {
			consulta1();
		});
		btnConsulta2.setOnAction(e -> {
			consulta2();
		});
		btnConsulta3.setOnAction(e -> {
			consulta3();
		});
		btnConsulta3b.setOnAction(e -> {
			consulta3b();
		});
		btnConsulta4.setOnAction(e -> {
			consulta4();
		});
		btnlimpar.setOnAction(e -> {
			limpar();
		});
		btnSair.setOnAction(e -> {
			System.exit(0);
		});

		pane.setCenter(mapkit);
		pane.setTop(leftPane);

		Scene scene = new Scene(pane, 900, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Mapas com JavaFX");
		primaryStage.show();

	}
public static void limpar () {
	origem.clear();
	destino.clear();
	lstPoints.clear();
	gerenciador.clear();
	gerenciador.setPontos(lstPoints);

	gerenciador.getMapKit().repaint();
	
}
	public static void gerapontosecaminhos(CiaAerea a) {
		gerenciador.clear();
		for (Rota c : GerenciadorRotas.listarTodas()) {
			if (c.getCia().getCodigo().equals(a.getCodigo())) {
				for (Aeroporto b : GerenciadorAeroportos.listarTodas()) {
					if (c.getDestino().getCodigo().equals(b.getCodigo())
							|| c.getOrigem().getCodigo().equals(b.getCodigo())) {

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
						if (r.getDestino().getCodigo().equals(s2)) {
							for (Aeroporto ae : GerenciadorAeroportos.listarTodas()) {
								if (ae.getCodigo().equals(s)) {
									origem.add(ae);
								}
							}
							for (Aeroporto ae : GerenciadorAeroportos.listarTodas()) {
								if (ae.getCodigo().equals(s2)) {
									destino.add(ae);
								}
							}

						}
					}
				}
			}
		}
		for (int i = 0; i < origem.size(); i++) {
			Tracado tr = new Tracado();
			tr.setWidth(1);
			tr.setCor(Color.BLUE);

			tr.addPonto(origem.get(i).getLocal());
			tr.addPonto(destino.get(i).getLocal());

			gerenciador.addTracado(tr);

		}
	}

	static Stage primaryStage = new Stage();
	static Button button;
	static Stage window;

	public static void aulas() {
		/*
		 * JANELA COM BOTAO primaryStage.setTitle("teste"); Button button1 = new
		 * Button(); button1.setText("go to scene 2"); button1.setOnAction(e -> {
		 * window.setScene(scene2); }); StackPane layout = new StackPane();
		 * layout.getChildren().add(button);
		 * 
		 * Scene scene = new Scene(layout,300,250); primaryStage.setScene(scene);
		 * primaryStage.show(); }
		 * 
		 */
		/*
		 * //Troca de janelas
		 * 
		 * Scene scene1,scene2; VBox layout1 = new VBox(20); StackPane layout2 = new
		 * StackPane(); scene1 = new Scene(layout1,200,200); scene2 = new
		 * Scene(layout2,600,300); window = primaryStage;
		 * window.setTitle("troca de janelas"); Label label1 = new
		 * Label("welcome to the first scene"); Button button1 = new Button();
		 * button1.setText("go to scene 2"); button1.setOnAction(e -> {
		 * window.setScene(scene2); });
		 * 
		 * layout1.getChildren().addAll(label1,button1);
		 * 
		 * 
		 * Button button2 = new Button(); button2.setText("go to scene 1");
		 * button2.setOnAction(e -> { window.setScene(scene1); });
		 * 
		 * layout2.getChildren().add(button2);
		 * 
		 * window.setScene(scene1); window.show();
		 */
		/*
		 * //Alert Boxes window = primaryStage; window.setTitle("for alert boxes");
		 * button = new Button ("click me"); button.setOnAction(e -> {
		 * AlertBoxes("Title of the window","wow this alert box is awesome!"); });
		 * StackPane layout = new StackPane(); layout.getChildren().add(button); Scene
		 * scene = new Scene (layout,300,250); window.setScene(scene); window.show();
		 */
		/*
		 * //ConfirmBox window = primaryStage; window.setTitle("ConfirmBox"); button =
		 * new Button ("click me"); button.setOnAction(e -> { boolean result
		 * =ConfirmBox("Title of the window","Are you sure?");
		 * System.out.println(result); }); StackPane layout = new StackPane();
		 * layout.getChildren().add(button); Scene scene = new Scene (layout,300,250);
		 * window.setScene(scene); window.show();
		 */
		/*
		 * //Closing the Program Properly window = primaryStage;
		 * window.setTitle("Closing the Program Properly"); window.setOnCloseRequest((e
		 * -> { e.consume(); closeprogram();
		 * 
		 * })); button = new Button ("Save and Close"); button.setOnAction(e -> {
		 * 
		 * closeprogram();
		 * 
		 * }); StackPane layout = new StackPane(); layout.getChildren().add(button);
		 * Scene scene = new Scene (layout,300,250); window.setScene(scene);
		 * window.show();
		 */
		/*
		 * //Multiple Layouts window = primaryStage;
		 * window.setTitle("Multiple Layouts");
		 * 
		 * HBox topMenu = new HBox (); Button buttonA = new Button ("File"); Button
		 * buttonB = new Button ("Edit"); Button buttonC = new Button ("View");
		 * topMenu.getChildren().addAll(buttonA,buttonB,buttonC);
		 * 
		 * VBox leftMenu = new VBox (); Button buttonD = new Button ("D"); Button
		 * buttonE = new Button ("E"); Button buttonF = new Button ("F");
		 * leftMenu.getChildren().addAll(buttonD,buttonE,buttonF);
		 * 
		 * BorderPane borderpane = new BorderPane(); borderpane.setTop(topMenu);
		 * borderpane.setLeft(leftMenu);
		 * 
		 * 
		 * 
		 * Scene scene = new Scene (borderpane,300,250); window.setScene(scene);
		 * window.show();
		 */
		/*
		 * //GridPane window=primaryStage; window.setTitle("GridPane");
		 * 
		 * GridPane grid = new GridPane(); grid.setPadding(new Insets(10,10,10,10));
		 * grid.setVgap(8); grid.setHgap(10);
		 * 
		 * Label nameLabel = new Label ("Username"); GridPane.setConstraints(nameLabel,
		 * 0, 0);
		 * 
		 * 
		 * TextField nameInput = new TextField("bucky");
		 * GridPane.setConstraints(nameInput, 1, 0);
		 * 
		 * Label nameLabe2 = new Label ("Password"); GridPane.setConstraints(nameLabe2,
		 * 0, 1);
		 * 
		 * TextField passwordInput = new TextField();
		 * passwordInput.setPromptText("password");
		 * GridPane.setConstraints(passwordInput, 1, 1);
		 * 
		 * Button loginButton = new Button ("Log In ");
		 * GridPane.setConstraints(loginButton, 1, 2);
		 * 
		 * grid.getChildren().addAll(nameLabel,
		 * nameInput,nameLabe2,passwordInput,loginButton);
		 * 
		 * Scene scene = new Scene (grid, 300, 200); window.setScene(scene);
		 * window.show();
		 */
		/*
		 * //Extract and Validate Input window = primaryStage;
		 * window.setTitle("Extract and Validate Input");
		 * 
		 * TextField nameimput = new TextField(); button = new Button ("click me");
		 * button.setOnAction(e -> { isInt(nameimput,nameimput.getText());
		 * 
		 * }); VBox layout = new VBox (10); layout.setPadding(new Insets(20,20,20,20));
		 * layout.getChildren().addAll(nameimput,button);
		 * 
		 * Scene scene = new Scene (layout,300,250); window.setScene(scene);
		 * window.show();
		 */
	}

	public static void AlertBoxes(String title, String message) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(350);
		window.setMinHeight(350);
		Label label = new Label();
		label.setMinSize(10, 10);
		label.setText(message);

		// Button closeButton = new Button("Close the window");
		// closeButton.setOnAction(e -> {
		// window.close();
		// });
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}

	static boolean answer;

	public static boolean ConfirmBox(String title, String message) {

		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		Label label = new Label();
		label.setText(message);

		Button yesButton = new Button("yes");
		Button noButton = new Button("no");
		yesButton.setOnAction(e -> {
			answer = true;
			window.close();
		});
		noButton.setOnAction(e -> {
			answer = false;
			window.close();
		});

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, yesButton, noButton);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		return answer;
	}

	private static void closeprogram() {
		Boolean answer = ConfirmBox("Title", "Sure you want to exit?");
		if (answer) {
			System.out.println("File is saved!");
			window.close();
		}
	}

	private static boolean isInt(TextField imput, String message) {
		try {
			int age = Integer.parseInt(imput.getText());
			System.out.println("User is: " + age);
			return true;
		} catch (NumberFormatException e) {
			System.out.println("Error:" + message + " is not a number");
			return false;
		}

	}

	static String resposta = "";

	public static void janelaEscreve(String a) {

		window = primaryStage;
		window.setTitle(a);
		GridPane grid = new GridPane();
		TextField nameimput = new TextField();
		Label userName = new Label("");
		grid.add(userName, 0, 1);
		button = new Button("Pesquisar");
		button.setOnAction(e -> {
			resposta = nameimput.getText();
			window.close();

		});
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20, 20, 20, 20));
		layout.getChildren().addAll(userName,nameimput, button);

		Scene scene = new Scene(layout, 500, 400);
		window.setScene(scene);
		window.showAndWait();

	}

	private void consulta1() {
		consulta = "1";
		janelaEscreve("Digite uma Cia:");
		

		CiaAerea a = new CiaAerea(resposta.toUpperCase());

		gerapontosecaminhos(a);
		System.out.println(resposta);

		// Para obter um ponto clicado no mapa, usar como segue:
		// GeoPosition pos = gerenciador.getPosicao();

		// Informa o resultado para o gerenciador

		// Quando for o caso de limpar os traçados...
		// gerenciador.clear();
		// lstPoints.clear();
		gerenciador.setPontos(lstPoints);

		gerenciador.getMapKit().repaint();
	}

	public static int opcao = 0;

	public static void escolheentre2() {
		window = primaryStage;
		window.setTitle("Consulta 2");
		GridPane grid = new GridPane();
		TextField nameimput = new TextField();
		Label userName = new Label("Estimativa de Trafego Aereo ");
		grid.add(userName, 0, 1);
		Button button1 = new Button();
		button1.setText("Todos Aeroportos");
		button1.setOnAction(e -> {
			opcao = 1;
			window.close();
		});

		Button button2 = new Button();
		button2.setText("Escolha um Pais: ");
		button2.setOnAction(e -> {
			opcao = 2;
			window.close();
		});

		VBox layout = new VBox(10);
		layout.getChildren().addAll(userName,button1, button2);

		Scene scene = new Scene(layout, 300, 100);
		window.setScene(scene);
		window.showAndWait();
		;
	}

	private void consulta2() {
		consulta = "2";
		escolheentre2();
		String teste = "";
		if (opcao == 1) {
			teste = "todos";
		}
		if (opcao == 2) {
			janelaEscreve("Digite o nome do Pais");
			teste = resposta;
			
		}

		int cont = 0;
		int maiorcont = 0;
		for (Aeroporto a : GerenciadorAeroportos.listarTodas()) {
			cont = 0;
			for (Rota r : GerenciadorRotas.listarTodas()) {
				if ((r.getOrigem().getCodigo().equals(a.getCodigo())
						|| (r.getDestino().getCodigo().equals(a.getCodigo())))) {
					cont = cont + 1;
				}
			}
			if (cont > maiorcont) {
				maiorcont = cont;
			}
			int icor = cont / 8;
			int icor2 = 0;
			int icor3 = 0;
			if (icor < 25) {
				icor = 0;
				icor2 = 255;
			}
			if (icor >= 25 && icor <= 100) {
				icor = 0;
				icor3 = 255;

			}
			if (icor > 100) {
				icor = 255;

			}
			Color red = new Color(icor, icor2, icor3);
			if (teste.equals("todos")) {
				lstPoints.add(new MyWaypoint(red, a.getCodigo(), a.getLocal(), cont));
			} else {
				if (a.getPais().getNome().equals(teste)) {
					lstPoints.add(new MyWaypoint(red, a.getCodigo(), a.getLocal(), cont));
				}
			}
		}
		// System.out.println("maiorcont"+maiorcont);
		// Para obter um ponto clicado no mapa, usar como segue:

		GeoPosition pos = gerenciador.getPosicao();

		// Informa o resultado para o gerenciador
		gerenciador.setPontos(lstPoints);

		// Quando for o caso de limpar os traçados...
		// gerenciador.clear();

		gerenciador.getMapKit().repaint();
	}

	public static ArrayList Clone(ArrayList a) {
		ArrayList clone = new ArrayList();
		for (int i = 0; i < a.size(); i++) {
			clone.add(a.get(i));
		}
		return clone;
	}

	public static void janelaEscolhe() {

		window = primaryStage;
		window.setTitle("Escolher");

		button = new Button("Confirmar");

		ComboBox combobox = new ComboBox();

		for (int i = 0; i < cons3.size(); i++) {
			combobox.getItems().add(cons3.get(i));

		}
		button.setOnAction(e -> {
			consulta3 o = new consulta3("", "", "", 0);
			o = (consulta3) combobox.getValue();
			double tempo = 0;
			if (o.getmeio().equals("->")) {
				tempo = 1 + ((o.getdistancia()) / 850);
			} else {
				tempo = 2 + ((o.getdistancia()) / 850);
			}

			for (int i = 0; i < origem.size(); i++) {
				Tracado tr = new Tracado();
				tr.setWidth(1);
				tr.setCor(Color.BLUE);
				if (o.getmeio().equals("->")) {
					if (o.getorigem().equals(origem.get(i).getCodigo())) {
						if (o.getdestino().equals(destino.get(i).getCodigo())) {
							tr.setCor(Color.RED);
							tr.setWidth(5);
						}
					}
				} else {
					if (o.getorigem().equals(origem.get(i).getCodigo())) {
						if (o.getmeio().equals(destino.get(i).getCodigo())) {
							tr.setCor(Color.RED);
							tr.setWidth(5);
						}
					}

					if (o.getmeio().equals(origem.get(i).getCodigo())) {
						if (o.getdestino().equals(destino.get(i).getCodigo())) {
							tr.setCor(Color.RED);
							tr.setWidth(5);
						}
					}
				}
				tr.addPonto(origem.get(i).getLocal());
				tr.addPonto(destino.get(i).getLocal());

				gerenciador.addTracado(tr);

			}

			gerenciador.setPontos(lstPoints);

			gerenciador.getMapKit().repaint();
			window.close();
			tempo = tempo * 10;
			tempo = (int) tempo;
			tempo = tempo / 10;
			int horas = (int) tempo;
			double dminutos = (tempo - (double) horas) * 60;
			int minutos = (int) dminutos;
			AlertBoxes("TEMPO", "O tempo de voo da rota sera de: " + horas + "h " + "e " + minutos + "min");

		});

		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20, 20, 20, 20));
		layout.getChildren().addAll(button, combobox);

		Scene scene = new Scene(layout, 300, 250);
		window.setScene(scene);
		window.showAndWait();

	}

	private static void consulta3real(String origemp, String destinop) {
		ArrayList<ArrayList<Rota>> rotas = new ArrayList<ArrayList<Rota>>();
		ArrayList<Rota> rotas2 = new ArrayList<Rota>();

		String Origem = origemp;
		String Destino = destinop;
		for (Rota r : GerenciadorRotas.listarTodas()) {
			rotas2.clear();
			if ((r.getOrigem().getCodigo().equals(Origem)) && (r.getDestino().getCodigo().equals(Destino))) {
				rotas2.add(r);
				rotas.add(Clone(rotas2));
			} else {
				if (r.getOrigem().getCodigo().equals(Origem)) {
					rotas2.add(r);
					for (Rota r2 : GerenciadorRotas.listarTodas()) {
						if ((r.getDestino().getCodigo().equals(r2.getOrigem().getCodigo()))
								&& (r2.getDestino().getCodigo().equals(Destino))) {
							rotas2.add(r2);
							rotas.add(Clone(rotas2));
							break;
						}
					}
				}
			}
		}
		/*
		 * for (int i=0;i<rotas.size();i++) { for (int j=0;j<rotas.get(i).size();j++) {
		 * if(j==0) { System.out.println("Come�o"); }
		 * System.out.println("Origem:"+rotas.get(i).get(j).getOrigem().getCodigo());
		 * System.out.println("Destino:"+rotas.get(i).get(j).getDestino().getCodigo());
		 * } }
		 */
		for (int i = 0; i < rotas.size(); i++) {
			for (int j = 0; j < rotas.get(i).size(); j++) {
				origem.add(rotas.get(i).get(j).getOrigem());
				destino.add(rotas.get(i).get(j).getDestino());
			}
		}
		for (int i = 0; i < origem.size(); i++) {
			for (Aeroporto a : GerenciadorAeroportos.listarTodas()) {
				if (a.getCodigo().equals(origem.get(i).getCodigo())) {
					origem.set(i, a);
				}
			}
		}
		for (int i = 0; i < destino.size(); i++) {
			for (Aeroporto a : GerenciadorAeroportos.listarTodas()) {
				if (a.getCodigo().equals(destino.get(i).getCodigo())) {
					destino.set(i, a);
				}
			}
		}
		ArrayList<consulta3> con3 = new ArrayList<consulta3>();
		double distancia = 0;
		for (int i = 0; i < origem.size(); i++) {
			if (origem.get(i).getCodigo().equals(Origem)) {
				if (destino.get(i).getCodigo().equals(Destino)) {
					distancia = Geo.distancia(origem.get(i).getLocal(), destino.get(i).getLocal());
					consulta3 con3aux = new consulta3(origem.get(i).getCodigo(), "->", destino.get(i).getCodigo(),
							distancia);
					con3.add(con3aux);
				} else {
					distancia = (Geo.distancia(origem.get(i).getLocal(), destino.get(i).getLocal()))
							+ (Geo.distancia(destino.get(i).getLocal(), destino.get(i + 1).getLocal()));
					consulta3 con3aux = new consulta3(origem.get(i).getCodigo(), destino.get(i).getCodigo(),
							destino.get(i + 1).getCodigo(), distancia);
					con3.add(con3aux);
				}
			}
		}
		System.out.println(con3);
		System.out.println("ORIGEM: " + origem);
		System.out.println("DESTINO: " + destino);

		for (int i = 0; i < origem.size(); i++) {
			lstPoints.add(new MyWaypoint(Color.RED, origem.get(i).getCodigo(), origem.get(i).getLocal(), 5));
		}
		for (int i = 0; i < destino.size(); i++) {
			lstPoints.add(new MyWaypoint(Color.RED, destino.get(i).getCodigo(), destino.get(i).getLocal(), 5));
		}
		for (int i = 0; i < origem.size(); i++) {
			Tracado tr = new Tracado();
			tr.setWidth(1);
			tr.setCor(Color.BLUE);

			tr.addPonto(origem.get(i).getLocal());
			tr.addPonto(destino.get(i).getLocal());

			gerenciador.addTracado(tr);

		}

		gerenciador.setPontos(lstPoints);

		gerenciador.getMapKit().repaint();

		cons3 = con3;

	}

	static ArrayList<consulta3> cons3 = new ArrayList<consulta3>();

	private void consulta3b() {
		janelaEscolhe();
	}

	private void consulta3() {
		consulta = "3";

	}

	private void consulta4() {
		consulta = "4";

		ArrayList<Aeroporto> aeroportos = new ArrayList<Aeroporto>();
		janelaEscreve("Digite o aeroporto de origem");
		String Origem = resposta;
		janelaEscreve("Digite o numero de horas");
		double tempomax = Double.parseDouble(resposta);
		int cont =0;
		ArrayList<consulta3> con3 = new ArrayList<consulta3>();
		
		for(Rota r: GerenciadorRotas.listarTodas()) {
			if(r.getOrigem().getCodigo().equals(Origem)) {
				origem.add(r.getOrigem());
				destino.add(r.getDestino());
				aeroportos.add(r.getDestino());
			}
		}
		System.out.println("Origem inicial"+origem.size());
		for (int i=0;i<aeroportos.size();i++) {
		for(Rota r2: GerenciadorRotas.listarTodas()) {
			if(r2.getOrigem().getCodigo().equals(aeroportos.get(i).getCodigo())) {
				origem.add(r2.getOrigem());
				destino.add(r2.getDestino());
				
			}
		}
		}
				System.out.println("Origem final"+origem.size());
				for (int i=0;i<100;i++) {
			System.out.println(origem.get(i));
				}
		
			
			for (int i = 0; i < origem.size(); i++) {
				for (Aeroporto a : GerenciadorAeroportos.listarTodas()) {
					if (a.getCodigo().equals(origem.get(i).getCodigo())) {
						origem.set(i, a);
					}
				}
			}
			for (int i = 0; i < destino.size(); i++) {
				for (Aeroporto a : GerenciadorAeroportos.listarTodas()) {
					if (a.getCodigo().equals(destino.get(i).getCodigo())) {
						destino.set(i, a);
					}
				}
			}
		
		
			double distancia = 0;
			for (int i = 0; i < origem.size(); i++) {
				if (i<aeroportos.size()) {
					
						distancia = Geo.distancia(origem.get(i).getLocal(), destino.get(i).getLocal());
						consulta3 con3aux = new consulta3(origem.get(i).getCodigo(), "->", destino.get(i).getCodigo(),
								distancia);
						con3.add(con3aux);
				}
				else {
					
						distancia = (Geo.distancia(origem.get(0).getLocal(), origem.get(i).getLocal()))
								+ (Geo.distancia(origem.get(i).getLocal(), destino.get(i).getLocal()));
						consulta3 con3aux2 = new consulta3(Origem, origem.get(i).getCodigo(),
								destino.get(i).getCodigo(), distancia);
						con3.add(con3aux2);
					
				}
			
			}
		
		ArrayList<consulta3> consulta4 = new ArrayList<consulta3>();
		double tempo = 0;
		for (int i = 0; i < con3.size(); i++) {
			if (con3.get(i).getmeio().equals("->")) {
				tempo = 1 + ((con3.get(i).getdistancia()) / 850);
			} else {
				tempo = 2 + ((con3.get(i).getdistancia()) / 850);
			}
			if (tempo <= tempomax) {
				
				consulta4.add(con3.get(i));
			}
		}
System.out.println("con3size "+con3.size());
System.out.println("consulta4 "+consulta4.size());
System.out.println(con3);
System.out.println(consulta4);
		for (int i = 0; i < consulta4.size(); i++) {
			for (int j = 0; j < origem.size(); j++) {
				if ((origem.get(j).getCodigo().equals(consulta4.get(i).getorigem()))
						|| (origem.get(j).getCodigo().equals(consulta4.get(i).getmeio()))
						|| (origem.get(j).getCodigo().equals(consulta4.get(i).getdestino()))) {
					lstPoints.add(new MyWaypoint(Color.RED, origem.get(j).getCodigo(), origem.get(j).getLocal(), 5));
				}
			}
			for (int k = 0; k < destino.size(); k++) {
				if ((destino.get(k).getCodigo().equals(consulta4.get(i).getorigem()))
						|| (destino.get(k).getCodigo().equals(consulta4.get(i).getmeio()))
						|| (destino.get(k).getCodigo().equals(consulta4.get(i).getdestino()))) {
				lstPoints.add(new MyWaypoint(Color.RED, destino.get(k).getCodigo(), destino.get(k).getLocal(), 5));
				}
				}
		}
		consulta3 o = new consulta3("","","",0);
		for (int j=0;j<consulta4.size();j++) {
			o=consulta4.get(j);
		for (int i = 0; i < origem.size(); i++) {
			Tracado tr = new Tracado();
			
			if (o.getmeio().equals("->")) {
				if (o.getorigem().equals(origem.get(i).getCodigo())) {
					if (o.getdestino().equals(destino.get(i).getCodigo())) {
						tr.setCor(Color.RED);
						tr.setWidth(2);
						tr.addPonto(origem.get(i).getLocal());
						tr.addPonto(destino.get(i).getLocal());

						gerenciador.addTracado(tr);
					}
				}
			} else {
				if (o.getorigem().equals(origem.get(i).getCodigo())) {
					if (o.getmeio().equals(destino.get(i).getCodigo())) {
						tr.setCor(Color.RED);
						tr.setWidth(2);
						tr.addPonto(origem.get(i).getLocal());
						tr.addPonto(destino.get(i).getLocal());

						gerenciador.addTracado(tr);
					}
				}

				if (o.getmeio().equals(origem.get(i).getCodigo())) {
					if (o.getdestino().equals(destino.get(i).getCodigo())) {
						tr.setCor(Color.RED);
						tr.setWidth(2);
						tr.addPonto(origem.get(i).getLocal());
						tr.addPonto(destino.get(i).getLocal());

						gerenciador.addTracado(tr);
					}
				}
			}
			

		}
		}
		gerenciador.setPontos(lstPoints);

		gerenciador.getMapKit().repaint();

	}

	public static Boolean raio5km(double a, double b) {
		if (((a < b) && (a >= (b - (5 / 111.12)))) || ((a > b) && (a <= (b + (5 / 111.12))))) {
			return true;

		} else {
			return false;
		}
	}

	static String cosulta0aeroportocodigo = "";

	public static void consulta0(GeoPosition loc) {
		for (Aeroporto a : GerenciadorAeroportos.listarTodas()) {
			if (raio5km(a.getLocal().getLatitude(), loc.getLatitude())) {
				if (raio5km(a.getLocal().getLongitude(), loc.getLongitude())) {
					lstPoints.add(new MyWaypoint(Color.GREEN, a.getCodigo(), a.getLocal(), 1));
					GeoPosition pos = gerenciador.getPosicao();

					// Informa o resultado para o gerenciador
					gerenciador.setPontos(lstPoints);

					gerenciador.getMapKit().repaint();
					System.out.println("ok");
					cosulta0aeroportocodigo = a.getCodigo();
				} else {
					lstPoints.clear();
					gerenciador.clear();
					gerenciador.getMapKit().repaint();
					// return "nao � aeroporto";
				}

			} else {
				lstPoints.clear();
				gerenciador.clear();
				gerenciador.getMapKit().repaint();
				// return "nao � aeroporto";
			}
		}
		// return "nao � aeroporto";
	}

	static int consulta3parte = 1;
	static String Origem, Destino;

	private class EventosMouse extends MouseAdapter {
		private int lastButton = -1;

		@Override
		public void mousePressed(MouseEvent e) {
			JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
			GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());
			System.out.println(loc.getLatitude() + ", " + loc.getLongitude());
			if (consulta.equals("0")) {
				consulta0(loc);
			}
			if (consulta.equals("3")) {
				if (consulta3parte == 1) {
					consulta0(loc);
					if (cosulta0aeroportocodigo.equals("") == false) {
						System.out.println("Entrou no parte 1");
						Origem = cosulta0aeroportocodigo;
						consulta3parte = 2;
						return;
					}
				}

			}
			if (consulta3parte == 2) {
				consulta0(loc);
				if (cosulta0aeroportocodigo.equals("") == false) {
					System.out.println("Entrou no parte 2");
					Destino = cosulta0aeroportocodigo;

					if (Origem != Destino) {
						consulta3parte = 3;

						consulta3real(Origem, Destino);
					}
				}

			}
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
