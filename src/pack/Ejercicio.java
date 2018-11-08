package pack;

import java.io.IOException;

public class Ejercicio {

	public static void main(String[] args) throws IOException{
		GrafoNDNP grafo = new GrafoNDNP("entrada.in");
		grafo.coloreoSecuencial();
		grafo.coloreoWelshPowell();
		grafo.coloreoMatula();
		
		System.out.println("probando");

	}

}
