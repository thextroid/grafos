import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class GrafoNDNP {
	private int n;
	private int capacidad;
	private String[] id;
	private boolean[][] ma;

	public GrafoNDNP(int capacidad) {
		this.n = 0;
		this.capacidad = capacidad;
		id = new String[capacidad];
		ma = new boolean[capacidad][capacidad];
	}

	// método solo de prueba; debe ser eliminado cuando
	// la clase se entregue para producción.

	public void verificacionMatrizAdyacencia() {
		for (int i = 0; i < capacidad; i++) {
			for (int j = 0; j < capacidad; j++)
				System.out.println();
		}
	}

	
	public void imprimirArreglos() {
		for (int i = 0; i < capacidad; i++) {
			System.out.print(id[i] + " ");
		}
		for (int i = 0; i < capacidad; i++) {
			for (int j = 0; j < capacidad; j++)
				System.out.print(ma[i][j] + " ");
			System.out.println();
		}
	}

	public int capacidad() {
		return this.capacidad;
	}

	public int orden() {
		return this.n;
	}

	public boolean esNulo() throws Exception {
		boolean correcto = true;
		for (int i = 0; i < capacidad; i++) {
			for (int j = 0; j < capacidad; j++)
				if (ma[i][j] == true)
					correcto = false;
		}
		if (!correcto)
			throw new Exception();
		else
			return n == 0 && correcto;
	}

	public boolean esVacio() {
		boolean esVacio = true;
		for (int i = 0; i < capacidad; i++) {
			for (int j = 0; j < capacidad; j++)
				if (ma[i][j] == true)
					esVacio = false;
		}
		return esVacio;
	}

	public boolean esLleno() {
		return n == capacidad;
	}

	public boolean existeVertice(String vertice) {
		vertice = vertice.toUpperCase();
		boolean existeVertice = false;
		for (int i = 0; i < this.n && !existeVertice; i++)
			if (vertice.equals(id[i]))
				existeVertice = true;
		return existeVertice;
	}

	public boolean insertarVertice(String vertice) {
		if (esLleno() || existeVertice(vertice))
			return false;
		else {
			id[n] = vertice.toUpperCase();
			n++;
			return true;
		}
	}

	private int indiceVertice(String vertice) {
		vertice = vertice.toUpperCase();
		int i = 0;
		while (i < this.n) {
			if (vertice.equals(id[i]))
				return i;
			i++;
		}
		return -1;
	}

	public boolean reemplazarVertice(String anterior, String nuevo) {
		int i = indiceVertice(anterior);
		if (i >= 0) {
			id[i] = nuevo.toUpperCase();
			return true;
		} else
			return false;
	}

	public boolean eliminarVertice(String vertice) {
		if (!existeVertice(vertice))
			return false;
		else {
			int ind = indiceVertice(vertice);
			for (int j = ind; j < n - 1; j++)
				for (int i = 0; i < n; i++)
					ma[i][j] = ma[i][j + 1];
			for (int i = ind; i < n - 1; i++)
				for (int j = 0; j < n; j++)
					ma[i][j] = ma[i + 1][j];
			for (int i = 0; i < n; i++) {
				ma[n - 1][i] = false;
				ma[i][n - 1] = false;
			}
			for (int i = ind; i < n - 1; i++)
				id[i] = id[i + 1];
			n--;
			return true;
		}
	}

	public boolean insertarArista(String origen, String destino) {
		if (!existeVertice(origen) || !existeVertice(destino))
			return false;
		else {
			ma[indiceVertice(origen)][indiceVertice(destino)] = true;
			ma[indiceVertice(destino)][indiceVertice(origen)] = true;

			return true;
		}
	}

	public boolean existeArista(String origen, String destino) {
		if (!existeVertice(origen) || !existeVertice(destino))
			return false;
		else {
			return ma[indiceVertice(origen)][indiceVertice(destino)];
		}
	}

	public int gradoVertice(String vertice) {
		if (!existeVertice(vertice))
			return -1;
		else {
			int aristas = 0;
			for (int j = 0; j < n; j++)
				if (ma[indiceVertice(vertice)][j])
					aristas++;
			return aristas;
		}
	}

	public int gradoGrafo(){
		int grado=0;
		for(int i=0; i<n; i++) {
			int gradovertice=gradoVertice(id[i]);
			if(gradovertice>grado)
				grado=gradovertice;
		}
		return grado;
	}
	
	public LinkedList verticesAdyacentes(String vertice) {
		if (!existeVertice(vertice))
			return null;
		else {
			LinkedList<String> l = new LinkedList<String>();
			for (int j = 0; j < n; j++)
				if (ma[indiceVertice(vertice)][j])
					l.add(id[j]);
			return l;
		}

	}

	public boolean eliminarArista(String origen, String destino) {
		if (!existeVertice(origen) || !existeVertice(destino))
			return false;
		else {
			if (!ma[indiceVertice(origen)][indiceVertice(destino)])
				return false;
			else {
				ma[indiceVertice(origen)][indiceVertice(destino)] = false;
				ma[indiceVertice(destino)][indiceVertice(origen)] = false;
				return true;
			}
		}
	}

	public String toString() {
		String cad="";
		for (int i = 0; i < n; i++) {
			cad=cad+id[i] + " ";
		}
		cad=cad+"\n\n";
		for (int i = 0; i < n; i++) {
			for(int j=i; j<n; j++) {
				if(ma[i][j])
					cad=cad+"("+id[i]+", "+id[j]+") ";
			}
			cad=cad+"\n";
		}
		
		return cad;
	}
	
	public void profundidad(String vertice) {
		vertice=vertice.toUpperCase();
		if(existeVertice(vertice)) {
			Stack pila=new Stack();
			boolean visitados[]=new boolean[n];
			pila.push(vertice);
			visitados[indiceVertice(vertice)]=true;
			while(!pila.empty()) {
				vertice=(String) pila.pop();
				System.out.println(vertice);
				LinkedList l=verticesAdyacentes(vertice);
				while(!l.isEmpty()) {
					vertice=(String) l.remove();
					if(!visitados[indiceVertice(vertice)]) {
						pila.push(vertice);
						visitados[indiceVertice(vertice)]=true;
					}
				}
			}
		}
	}
	
	public void amplitud(String vertice) {
		vertice=vertice.toUpperCase();
		if(existeVertice(vertice)) {
			Queue cola=new LinkedList();
			boolean visitados[]=new boolean[n];
			cola.offer(vertice);
			visitados[indiceVertice(vertice)]=true;
			while(!cola.isEmpty()) {
				vertice=(String) cola.poll();
				System.out.println(vertice);
				LinkedList l=verticesAdyacentes(vertice);
				while(!l.isEmpty()) {
					vertice=(String) l.remove();
					if(!visitados[indiceVertice(vertice)]) {
						cola.offer(vertice);
						visitados[indiceVertice(vertice)]=true;
					}
				}
			}
		}
	}
	
	public int distanciaMinima(String origen, String destino) {
		if(!existeVertice(origen) || !existeVertice(destino)) {
			return -1;
		}else {
			int[] distancias=new int[n];
			boolean[] visitados=new boolean[n];
			int[] rutas=new int[n];
			for(int i=0; i<n; i++)
				distancias[i]=Integer.MAX_VALUE;
			int u=indiceVertice(origen);
			visitados[u]=true;
			distancias[u]=0;
			rutas[u]=u;
			for(int i=0; i<n-1; i++) {
				calculoDistanciasAdyacentes(u, distancias, visitados, rutas);
				u=distanciaMinima(distancias, visitados);
				visitados[u]=true;
			}
			return distancias[indiceVertice(destino)];
		}
	}
	
	public LinkedList rutaMasCorta(String origen, String destino) {
		if(!existeVertice(origen) || !existeVertice(destino)) {
			return null;
		}else {
			LinkedList l=new LinkedList();
			int[] distancias=new int[n];
			boolean[] visitados=new boolean[n];
			int[] rutas=new int[n];
			for(int i=0; i<n; i++)
				distancias[i]=Integer.MAX_VALUE;
			int u=indiceVertice(origen);
			visitados[u]=true;
			distancias[u]=0;
			rutas[u]=u;
			for(int i=0; i<n-1; i++) {
				calculoDistanciasAdyacentes(u, distancias, visitados, rutas);
				u=distanciaMinima(distancias, visitados);
				visitados[u]=true;
			}
			l.add(destino.toUpperCase());
			int indice=indiceVertice(destino);
			while(indice!=indiceVertice(origen)) {
				indice=rutas[indice];
				l.add(0, id[indice]);
			}
			return l;
		}
	}
	
	
	public void dijkstra(String origen, String destino) {
		if(!existeVertice(origen) || !existeVertice(destino)) {
			System.out.println("vétice inexistente");
		}else {
			int[] distancias=new int[n];
			boolean[] visitados=new boolean[n];
			int[] rutas=new int[n];
			for(int i=0; i<n; i++)
				distancias[i]=Integer.MAX_VALUE;
			int u=indiceVertice(origen);
			visitados[u]=true;
			distancias[u]=0;
			rutas[u]=u;
			for(int i=0; i<n-1; i++) {
				calculoDistanciasAdyacentes(u, distancias, visitados, rutas);
				u=distanciaMinima(distancias, visitados);
				visitados[u]=true;
			}
			for(int i=0; i<n; i++)
				System.out.println(i+" dist "+distancias[i]);
			System.out.println();
			for(int i=0; i<n; i++)
				System.out.println(i+" ruta "+rutas[i]);
			
		}
	}
	
	private int distanciaMinima(int distancias[], boolean[] visitados) {
		int ind=-1, distancia=Integer.MAX_VALUE;
		for(int i=0; i<n; i++) {
			if(!visitados[i] && distancias[i]<distancia) {
				ind=i;
				distancia=distancias[i];
			}
		}
		return ind;
	}
	private void calculoDistanciasAdyacentes(int u, int[] distancias, boolean[] visitados, int[] rutas) {
		LinkedList l=verticesAdyacentes(id[u]);
		while(!l.isEmpty())	{
			int v=indiceVertice((String) l.remove());
			if(!visitados[v] && ma[u][v] && distancias[u]+1<distancias[v]) {
				distancias[v]=distancias[u]+1;
				rutas[v]=u;
			}
		}
	}
	
	
	// Método de test que deberá ser eliminado durante su entrega final
	public String testInsertarEliminarNodos(int n) throws Exception {
		int vertices = 0;
		while (vertices < n) {
			insertarVertice(identificadorAleatorio());
			vertices++;
		}
		int aristas = 0, maxAristas = (int) (Math.random() * n * (n + 1) / 2);
		while (aristas <= maxAristas) {
			String origen = seleccionId();
			String destino = seleccionId();
			if (!existeArista(origen, destino)) {
				insertarArista(origen, destino);
				aristas++;
			}
		}
		while (orden() > 0) {
			eliminarVertice(seleccionId());
		}

		return "Test concluida exitosamente";
	}

	private String identificadorAleatorio() {
		String id = "";
		for (int i = 0; i < 5; i++) {
			id = id + caracterAleatorio();
		}
		return id;
	}

	private char caracterAleatorio() { // 65 90
		return (char) ((int) (Math.random() * (91 - 65)) + 65);
	}

	private String seleccionId() {
		String aux = id[(int) (Math.random() * (n))];
		return aux;
	}

	private int numeroAristas(String vertice) {
		if (!existeVertice(vertice))
			return -1;
		else {
			int aristas = 0;
			for (int i = 0; i < capacidad; i++) {
				if (ma[i][indiceVertice(vertice)])
					aristas++;
			}
			return aristas;
		}
	}

	public String testCoherenciaDeAristasAlEliminarUnNodo(int n) throws Exception {
		int vertices = 0;
		while (vertices < n) {
			insertarVertice(identificadorAleatorio());
			vertices++;
		}
		int aristas = 0, maxAristas = (int) (Math.random() * n * (n + 1) / 2);
		while (aristas <= maxAristas) {
			String origen = seleccionId();
			String destino = seleccionId();
			if (!existeArista(origen, destino)) {
				insertarArista(origen, destino);
				aristas++;
			}
		}
		while (orden() > 0) {
			eliminarVertice(seleccionId());
			for (int i = 0; i < n; i++)
				if (gradoVertice(id[i]) != numeroAristas(id[i]))
					System.out.println("error en la cantidad de aristas");
		}

		return "Test concluida exitosamente";
	}

	/////////////////////////////////////////////////////////

}
