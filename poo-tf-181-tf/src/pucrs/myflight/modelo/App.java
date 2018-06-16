package pucrs.myflight.modelo;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import pucrs.myflight.gui.JanelaFX;



public class App {

	public static void main(String[] args) {

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
        System.out.println("ok");
        CiaAerea a = new CiaAerea("3J");
        JanelaFX.gerapontosecaminhos(a);
        
        /*
        for (Aeronave b : GerenciadorAeronaves.listarTodas()) {
        System.out.println(b);
        }
        for (Aeroporto b : GerenciadorAeroportos.listarTodas()) {
            System.out.println(b);
            }
        for (CiaAerea b : GerenciadorCias.listarTodas()) {
            System.out.println(b);
            }
        
      
            System.out.println(GerenciadorPaises.listarTodas());
        
        for (Rota b : GerenciadorRotas.listarTodas()) {
           // System.out.println(b);
            }
   //     ArrayList<Pais> paises = gerPais.listarTodos();
     //   System.out.println("Total rotas:"+ paises.size());
    //   for(Pais p1 : paises)
          //  System.out.println(p1.getCodigo() + " - " + p1.getNome());

		/*
		gerCias.adicionar(new CiaAerea("JJ", "LATAM Linhas Aéreas"));
		gerCias.adicionar(new CiaAerea("G3", "Gol Linhas Aéreas S/A"));
		gerCias.adicionar(new CiaAerea("TP", "TAP Portugal"));
		gerCias.adicionar(new CiaAerea("AD", "Azul Linhas Aéreas"));
		*/

//		GerenciadorAeronaves gerAvioes = new GerenciadorAeronaves();
//
//		gerAvioes.adicionar(new Aeronave("733", "Boeing 737-300", 140));
//		gerAvioes.adicionar(new Aeronave("73G", "Boeing 737-400", 126));
//		gerAvioes.adicionar(new Aeronave("380", "Airbus Industrie A380", 644));
//		gerAvioes.adicionar(new Aeronave("764", "Boeing 767-400", 304));
//		gerAvioes.ordenarDescricao();
////        gerAvioes.ordenarCodigo();
//        // Listando em ordem alfabética de descrição:
//        System.out.println("\nAeronaves:");
//        for(Aeronave av: gerAvioes.listarTodas())
//            System.out.println(av);
//        System.out.println();
//
//        GerenciadorAeroportos gerAero = new GerenciadorAeroportos();
//
//		gerAero.adicionar(new Aeroporto("POA", "Salgado Filho Intl",
//                new Geo(-29.9939, -51.1711)));
//		gerAero.adicionar(new Aeroporto("GRU", "São Paulo Guarulhos Intl",
//                new Geo(-23.4356, -46.4731)));
//		gerAero.adicionar(new Aeroporto("LIS", "Lisbon",
//                new Geo(38.7742, -9.1342)));
//		gerAero.adicionar(new Aeroporto("MIA", "Miami Intl Airport",
//                new Geo(25.7933, -80.2906)));
//		gerAero.ordenarNomes();
//
//        System.out.println("\nAeroportos ordenados por nome:\n");
//        for(Aeroporto a: gerAero.listarTodos())
//            System.out.println(a);
//        System.out.println();
//
//		// Para facilitar a criação de rotas:
//
//        CiaAerea latam = gerCias.buscarCodigo("JJ");
//        CiaAerea gol   = gerCias.buscarCodigo("G3");
//        CiaAerea tap   = gerCias.buscarCodigo("TP");
//        CiaAerea azul  = gerCias.buscarCodigo("AD");
//
//        Aeronave b733 = gerAvioes.buscarCodigo("733");
//        Aeronave b73g = gerAvioes.buscarCodigo("73G");
//        Aeronave a380 = gerAvioes.buscarCodigo("380");
//
//		Aeroporto poa = gerAero.buscarCodigo("POA");
//		Aeroporto gru = gerAero.buscarCodigo("GRU");
//		Aeroporto lis = gerAero.buscarCodigo("LIS");
//		Aeroporto mia = gerAero.buscarCodigo("MIA");
//
//        System.out.println("Distância POA->GRU: "+
//            Geo.distancia(poa.getLocal(), gru.getLocal()));
//
//        System.out.println("Distâcia GRU->POA: " +
//            gru.getLocal().distancia(poa.getLocal()));
//
//		GerenciadorRotas gerRotas = new GerenciadorRotas();
//
//		Rota poagru = new Rota(latam, poa, gru, b733);
//        Rota grupoa = new Rota(latam, gru, poa, b733);
//        Rota grumia = new Rota(tap, gru, mia, a380);
//        Rota grulis = new Rota(tap, gru, lis, a380);
//
//        gerRotas.adicionar(grumia);
//        gerRotas.adicionar(grulis);
//		gerRotas.adicionar(poagru);
//		gerRotas.adicionar(grupoa);
////		gerRotas.ordenarCias();
//		gerRotas.ordenarNomesAeroportosCias();
//
//        System.out.println("\nRotas ordenadas:\n");
//        for(Rota r: gerRotas.listarTodas())
//            System.out.println(r);
//        System.out.println();
//
//		LocalDateTime manhacedo = LocalDateTime.of(2018, 3, 29, 8, 0);
//        LocalDateTime manhameio = LocalDateTime.of(2018, 4, 4, 10, 0);
//        LocalDateTime tardecedo = LocalDateTime.of(2018, 4, 4, 14, 30);
//        LocalDateTime tardetarde = LocalDateTime.of(2018, 4, 5, 17, 30);
//
//        Duration curto = Duration.ofMinutes(90);
//        Duration longo1 = Duration.ofHours(12);
//        Duration longo2 = Duration.ofHours(14);
//
//        GerenciadorVoos gerVoos = new GerenciadorVoos();
//
//        gerVoos.adicionar(new Voo(poagru, curto)); // agora!
//        gerVoos.adicionar(new Voo(grulis, tardecedo, longo2));
//        gerVoos.adicionar(new Voo(grulis, tardetarde, longo2));
//        gerVoos.adicionar(new Voo(poagru, manhacedo, curto));
//        gerVoos.adicionar(new Voo(grupoa, manhameio, curto));
//        gerVoos.adicionar(new Voo(grumia, manhacedo, longo1));
//
//        // Vôo com várias escalas
//        VooEscalas vooEsc = new VooEscalas(poagru,
//            manhacedo, longo2);
//        vooEsc.adicionarRota(grulis);
//
//        gerVoos.adicionar(vooEsc);
//
//        // O toString vai usar o método implementado
//        // em VooEscalas, mas reutilizando (reuso) o método
//        // original de Voo
//        System.out.println(vooEsc.toString());
//
////        gerVoos.ordenarDataHoraDuracao();
//        gerVoos.ordenarDataHoraDuracao();
//        System.out.println("Todos os vôos:\n");
//        for(Voo v: gerVoos.listarTodos())
//        {
//            if(v instanceof VooEscalas) {
//                System.out.println(">>> Vôo com escalas!");
//                VooEscalas vaux = (VooEscalas) v;
//                System.out.println("Escalas: "+vaux.getTotalRotas());
//            }
//            System.out.println(v);
//        }
//
//        // Tarefa 1: listar os vôos de determinada origem
//
//        System.out.println("\nVôos cuja origem é Guarulhos (gru)\n");
//        for(Voo v: gerVoos.buscarOrigem("GRU"))
//            System.out.println(v);
//
//        // Tarefa 2: mostrar a localização dos aeroportos que operam em determinado período do dia
//
//        LocalTime inicio = LocalTime.of(8, 0);
//        LocalTime fim    = LocalTime.of(9, 0);
//
//        System.out.println("\nVôos que ocorrem entre 7h e 9h\n");
//        for(Voo v: gerVoos.buscarPeriodo(inicio, fim)) {
////            System.out.println(v);
//            Aeroporto origem = v.getRota().getOrigem();
//            System.out.println(origem.getNome() + ": " +origem.getLocal());
//        }
//
//        LocalTime inicio2 = LocalTime.of(9, 0);
//        LocalTime fim2    = LocalTime.of(16, 0);
//
//        System.out.println("\nVôos que ocorrem entre 9h e 16h\n");
//        for(Voo v: gerVoos.buscarPeriodo(inicio2, fim2)) {
////            System.out.println(v);
//            Aeroporto origem = v.getRota().getOrigem();
//            System.out.println(origem.getNome() + ": " + origem.getLocal());
//        }
	}
}
