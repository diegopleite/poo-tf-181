package pucrs.myflight.modelo;

import java.util.HashSet;

public class Rota implements Comparable<Rota> {
	private CiaAerea cia;
	private Aeroporto origem;
	private Aeroporto destino;
	private HashSet <Aeronave> aeronave = new HashSet <Aeronave>();
	
	public Rota(CiaAerea cia, Aeroporto origem, Aeroporto destino, HashSet <Aeronave> aeronave) {
		this.cia = cia;
		this.origem = origem;
		this.destino = destino;
		this.aeronave = aeronave;		
	}	
	
	public CiaAerea getCia() {
		return cia;
	}
	
	public Aeroporto getDestino() {
		return destino;
	}
	
	public Aeroporto getOrigem() {
		return origem;
	}
	
	public HashSet <Aeronave> getAeronave() {
		return aeronave;
	}

   

	@Override
	public String toString() {
		return "Rota [cia=" + cia.getCodigo() + ", origem=" + origem.getCodigo() + ", destino=" + destino.getCodigo() + ", aeronaves=" + aeronave + "]";
	}

	@Override
	public int compareTo(Rota rota) {
		return this.cia.getNome().compareTo(
				rota.cia.getNome());
	}
}
