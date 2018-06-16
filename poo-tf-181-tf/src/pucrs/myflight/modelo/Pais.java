package pucrs.myflight.modelo;

public class Pais {
    private String nome;
    private String codigo;
    public Pais(String n, String c){
        this.nome = n;
        this.codigo = c;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

	@Override
	public String toString() {
		return "Pais [nome=" + nome + ", codigo=" + codigo + "]\n";
	}

  
}
