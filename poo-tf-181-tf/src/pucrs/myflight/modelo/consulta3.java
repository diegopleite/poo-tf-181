package pucrs.myflight.modelo;


	public class consulta3 implements Comparable<consulta3> {
		private String origem;
		private String meio;
		private String destino;
		private double distancia;
		
		
		public consulta3(String origem, String meio, String destino, double distancia) {
			this.origem = origem;
			this.meio = meio;
			this.destino = destino;
			this.distancia = distancia;
			
		}
		
		public String getorigem() {
			return origem;
		}
		
		public String getmeio() {
			return meio;
		}
		
		public String getdestino() {
			return destino;
		}
		public double getdistancia() {
			return distancia;
		}

		@Override
		public String toString() {
			return origem + "->" + meio + "->" + destino;
		}

		@Override
		public int compareTo(consulta3 arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

	    

		
		
	
}
