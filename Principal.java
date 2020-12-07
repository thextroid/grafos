import java.util.LinkedList;

public class Principal {
	public static void main(String[] args) throws Exception {
		/*
		 * int capacidadMaxima = 200; for (int i = 0; i < 100; i++) { int capacidad =
		 * (int) (Math.random() * capacidadMaxima + 1); int n = (int) (Math.random() *
		 * capacidad + 1); System.out.println(n); GrafoNDNP g = new
		 * GrafoNDNP(capacidad); g.testCoherenciaDeAristasAlEliminarUnNodo(n);
		 * System.out.println(i + " " + g.esNulo()); }
		 */

		GrafoNDNP g = new GrafoNDNP(10);
		g.insertarVertice("a");
		g.insertarVertice("b");
		g.insertarVertice("c");
		g.insertarVertice("d");
		g.insertarVertice("e");

		g.insertarArista("a", "b");
		g.insertarArista("a", "c");
		g.insertarArista("b", "c");
		g.insertarArista("c", "d");
		g.insertarArista("a", "e");
		g.insertarArista("d", "e");
		
		g.dijkstra("e", "b");
		System.out.println(g.rutaMasCorta("b", "e"));
		System.out.println(g.rutaMasCorta("e", "b"));
		System.out.println(g.rutaMasCorta("a", "d"));
		System.out.println(g.rutaMasCorta("d", "a"));
		System.out.println(g.rutaMasCorta("c", "b"));
		System.out.println(g.rutaMasCorta("b", "c"));
		System.out.println(g.rutaMasCorta("d", "d"));
		
	}
}