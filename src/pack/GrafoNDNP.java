package pack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class GrafoNDNP {

	private int matriz[][];
	private ArrayList<Nodo> nodos;
	private int[] nodosColoreados; // el indice coincide con el numero de nodo y el valor que guarda es el color
	private int[] gradosNodos;
	private int cantNodos;
	private int cantAristas;
	private int colorMax;
	private int mejorColor;


	public GrafoNDNP(String path) throws FileNotFoundException {
		File file = new File(path);
		Scanner scan = new Scanner(file);
	

		this.cantNodos = scan.nextInt();
		this.colorMax = 1;
		nodos = new ArrayList<Nodo>();
		nodosColoreados = new int[this.cantNodos];
		gradosNodos = new int[this.cantNodos];
		matriz = new int [this.cantNodos][this.cantNodos];


		int i = 0;
		int j = 0;
		scan.nextInt();

		for (i = 0; i < cantNodos; i++) {

			j = scan.nextInt();
			while (j != -1) {
				gradosNodos[i]++;
				gradosNodos[j - 1]++;
				matriz[i][j-1]=1;
				matriz[j-1][i]=1;
				j = scan.nextInt();

			}

			if (i < cantNodos - 1)
				scan.nextInt();

		}

		for (i = 0; i < this.cantNodos; i++) {
			Nodo nodo = new Nodo(i);
			nodo.setGrado(this.gradosNodos[i]);
			this.nodos.add(nodo);
		}

		
	}

	private void colorear() {
		int color = 1;
		int nodo;
		int j;

		this.colorMax = 1;
		for (int i = 0; i < this.cantNodos; i++)
			this.nodosColoreados[i] = 0;

		nodosColoreados[this.nodos.get(0).getNroNodo()] = color; // coloreo el
																	// primer
																	// nodo de
																	// la lista

		for (int i = 1; i < this.cantNodos; i++) {
			nodo = this.nodos.get(i).getNroNodo();
			this.nodosColoreados[nodo] = color;
			j = 0;
			while (j < this.cantNodos) {
				if (nodo != j) {
					if (matriz[nodo][j]==1 && this.nodosColoreados[nodo] == this.nodosColoreados[j]) {
						color++;
						if (color > colorMax)
							colorMax = color;
						this.nodosColoreados[nodo] = color;
				
					}
				}
				j++;
			}
			color = 1;
		}

	}

	public void coloreoSecuencial() throws IOException {

		this.colorear();
		this.escribirSolucion("SECUENCIAL.out");

	

	}

	public void coloreoWelshPowell() throws IOException {

		Collections.sort(this.nodos, new Comparator<Nodo>() {
			@Override
			public int compare(Nodo nodo1, Nodo nodo2) {
				return nodo2.getGrado() - nodo1.getGrado(); // ordeno por
															// grado
															// decreciente
			}
		});
		this.colorear();
		
		this.escribirSolucion("WELSH-POWELL.out");

	}

	public void coloreoMatula() throws IOException {

		
			Collections.sort(this.nodos, new Comparator<Nodo>() {
				@Override
				public int compare(Nodo nodo1, Nodo nodo2) {
					return nodo1.getGrado() - nodo2.getGrado(); // ordeno por
																// grado
																// creciente
				}
			});
			this.colorear();


		this.escribirSolucion("MATULA.out");
	
		
	}

	private void escribirSolucion(String path) throws IOException {

		PrintWriter salida = new PrintWriter(new File(path));

		int max = 0;
		int cantcolores = this.colorMax;
		int auxcolormax = 0;
		int j = 0;

		for (int i = 1; i <= cantcolores; i++) {
			while (j < this.cantNodos) {
				if (this.nodosColoreados[j] == i) {
					auxcolormax++;
					if (max < auxcolormax) {
						mejorColor = this.nodosColoreados[j];
						max = auxcolormax;
					}
				}
				j++;
			}
			auxcolormax = 0;
			j = 0;
		}
		salida.println(max);

		for (int i = 0; i < this.cantNodos; i++) {
			if (this.nodosColoreados[i] == mejorColor) {
				salida.print(i + 1 + " ");

			}

		}
		salida.close();

		mejorColor = 0;

	}

	public int getCantNodos() {
		return cantNodos;
	}



	

}
