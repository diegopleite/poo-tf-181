package pucrs.myflight.modelo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;



public class GerenciadorAeronaves {

    private static HashSet<Aeronave> avioes;
   
    public GerenciadorAeronaves() {
        this.avioes = new HashSet<Aeronave>();
    }

    public static void adicionar(Aeronave aviao) {
        avioes.add(aviao);
    }

    public static HashSet<Aeronave> listarTodas() {
        return avioes;
    }

    public Aeronave buscarCodigo(String codigo) {
        for(Aeronave a: avioes)
            if(a.getCodigo().equals(codigo))
                return a;
        return null;
    }

    public static void carregaDados(String nomeArq) throws IOException {
        Path path = Paths.get(nomeArq);
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf8")))) {
            sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
            String header = sc.nextLine(); // pula cabeçalho
            String cod, des, aux;
            int cap;


            while (sc.hasNext()) {
                cod = sc.next();
                des = sc.next();
                aux = sc.next();
                cap = Integer.parseInt(aux);

                Aeronave nova = new Aeronave(cod,des,cap);
                adicionar(nova);
            }
        }
    }
/*
    public void ordenarDescricao() {
        // Usando Comparable<Aeronave> em Aeronave
        //Collections.sort(avioes);

        // Usando expressão lambda
        //avioes.sort( (Aeronave a1, Aeronave a2) ->
        //    a1.getDescricao().compareTo(a2.getDescricao()));

        // Mesma coisa, usando método static da interface Comparator:
        //avioes.sort(Comparator.comparing(a -> a.getDescricao()));

        // Invertendo o critério de comparação com reversed():
        ((List<Aeronave>) avioes).sort(Comparator.comparing(Aeronave::getDescricao).reversed());
    }

    public void ordenarCodigoDescricao() {
       // Ordenando pelo código e desempatando pela descrição
       ((List<Aeronave>) avioes).sort(Comparator.comparing(Aeronave::getCodigo).
               thenComparing(Aeronave::getDescricao));
    }

    public void ordenarCodigo() {
        ((List<Aeronave>) avioes).sort( (Aeronave a1, Aeronave a2) ->
            a1.getCodigo().compareTo(a2.getCodigo()));
    }
    */
}
