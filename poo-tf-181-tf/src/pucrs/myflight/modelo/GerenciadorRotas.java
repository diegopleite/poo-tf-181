package pucrs.myflight.modelo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class GerenciadorRotas {

    private static HashSet<Rota> rotas;

    public GerenciadorRotas() {
        this.rotas = new HashSet<Rota>();
    }
/*
    public void ordenarCias() {
        Collections.sort(rotas);
    }

    public void ordenarNomesCias() {
        rotas.sort( (Rota r1, Rota r2) ->
          r1.getCia().getNome().compareTo(
          r2.getCia().getNome()));
    }

    public void ordenarNomesAeroportos() {
        rotas.sort( (Rota r1, Rota r2) ->
                r1.getOrigem().getNome().compareTo(
                r2.getOrigem().getNome()));
    }

    public void ordenarNomesAeroportosCias() {
        rotas.sort( (Rota r1, Rota r2) -> {
           int result = r1.getOrigem().getNome().compareTo(
                   r2.getOrigem().getNome());
           if(result != 0)
               return result;
           return r1.getCia().getNome().compareTo(
                   r2.getCia().getNome());
        });
    }
    */
    public void adicionar(Rota r) {
        rotas.add(r);
    }
   

    public static HashSet<Rota> listarTodas() {
        return rotas;
    }

    public void carregaDados(String nomeArq) throws IOException {
        Path path = Paths.get(nomeArq);
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf8")))) {
            sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
            
            String header = sc.nextLine(); // pula cabeçalho
            String cod,from,to,stops,equip;
            int t = 2;

            while (sc.hasNext()) {
                cod = sc.next();
//                from = sc.next();
                from = sc.next();
//              code = sc.next();
                to = sc.next();
                sc.next();
                sc.next();
               equip = sc.next();
               
               ArrayList<String> h = new ArrayList<String>();
               HashSet<Aeronave> ae = new HashSet<Aeronave>();
               HashSet<String> as = new HashSet<String>();
               as.clear();
				for (char c : equip.toCharArray()) {
					String s = "" + c;
					h.add(s);
				}
				equip="";
				for (int i=0;i<h.size();i++) {
                    if(i==h.size()-1) {
                    	equip=equip+h.get(i);
                    	as.add(equip);
                    	break;
                    }
					if(h.get(i).equals(" ")) {
                    	as.add(equip);
                    	equip="";
                    }
					else {
					equip=equip+h.get(i);
					}
				}
				
				
               
	               for (Aeronave aer: GerenciadorAeronaves.listarTodas()) {
	            	   for (String aer2: as) {
	            		   if (aer2.equals(aer.getCodigo())) {
	            			   ae.add(aer);
	            		   }
	            	   }
	               }
	             
                Aeroporto a1 = new Aeroporto(from);
                CiaAerea c = new CiaAerea(cod);
                Aeroporto a2 = new Aeroporto(to);
                
               
                Rota nova = new Rota(c,a1,a2,ae);
                adicionar(nova);
            }
        }
    }

    public ArrayList<Rota> buscarOrigem(String codigo) {
        ArrayList<Rota> result = new ArrayList<>();
        for(Rota r: rotas)
            if(r.getOrigem().getCodigo().equals(codigo))
                result.add(r);
        return result;
    }
}
