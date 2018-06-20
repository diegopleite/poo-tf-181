package pucrs.myflight.modelo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class GerenciadorPaises {
    private static HashSet<Pais> paises= new HashSet<Pais>();

    public GerenciadorPaises(){
        //this.paises = new HashMap<>();
    }
    public static HashSet<Pais> listarTodas(){
        return paises;
    }
    public void carregaDados(String nomeArq) throws IOException {
        Path path = Paths.get(nomeArq);
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf8")))) {
            sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
            String header = sc.nextLine(); // pula cabeçalho
            String cod, nome;
            while (sc.hasNext()) {
                nome = sc.next();
                cod = sc.next();
                Pais novo = new Pais(cod, nome);
                adicionar(novo);
                //System.out.format("%s - %s (%s)%n", nome, data, cpf);
            }
        }
    }
    public void adicionar(Pais pais) {
        paises.add(pais);
    }
 /*   public Pais buscarCodigo(String cod) {
        return paises.get(cod);
    }
    public Pais buscarNome(String nome) {
        return paises.get(nome);
    }
    */
}
