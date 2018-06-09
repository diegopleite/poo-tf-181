package pucrs.myflight.modelo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class GerenciadorAeroportos {

    private ArrayList<Aeroporto> aeroportos;


    public GerenciadorAeroportos() {
        this.aeroportos = new ArrayList<>();

    }

    public void ordenarNomes() {

        Collections.sort(aeroportos);


    }

    public void adicionar(Aeroporto aero) {
        aeroportos.add(aero);
    }

    public ArrayList<Aeroporto> listarTodos() {
        return new ArrayList<>(aeroportos);
    }

    public void carregaDados(String nomeArq) throws IOException {
        Path path = Paths.get(nomeArq);
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf8")))) {
            sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
            String header = sc.nextLine(); // pula cabeçalho
            String cod, nome,lt,lg,pais;
            Double lat, lon;


            while (sc.hasNext()) {
                cod = sc.next();
                lt = sc.next();
                lg = sc.next();
                nome = sc.next();
                pais = sc.next();
                lat = Double.parseDouble(lt);
                lon = Double.parseDouble(lg);

                Aeroporto novo = new Aeroporto(cod, nome,new Geo(lat,lon));
                aeroportos.add(novo);
            }
        }
    }

    public Aeroporto buscarCodigo(String codigo) {
        for(Aeroporto a: aeroportos)
            if(a.getCodigo().equals(codigo))
                return a;
        return null;
    }
}
