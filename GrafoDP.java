import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class GrafoDP {
	private int n;
	private int capacidad;
	private String[] id;
	private boolean[][] ma;

	public GrafoDP(int capacidad) {
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
	boolean get(int i,int j) {
		return ma[i][j];
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
	boolean visited[];
	void buildVisited() {
		visited = new boolean[capacidad];
	}
	void initVisited() {
		Arrays.fill(visited, false);
	}
	public boolean esAciclico() {
		initVisited();
		 for (int u = 0; u < this.n; u++){
            if (!visited[u])  
                if( esAciclicoUtil(u, visited, -1) ) 
                    return true; 
        }
	  
		return false;
	}
	public boolean esAciclicoUtil(int vertex,boolean[] visited,int parent) {
		 // Mark the current node as visited 
        visited[vertex] = true; 
        Integer i; 
  
        // Recur for all the vertices  
        // adjacent to this vertex 
        
        for (int adj = 0; adj < this.n ; adj++) {
			if( get(vertex,adj) ) {
				if (!visited[adj]) 
	            { 
	                if (esAciclicoUtil(adj, visited, vertex)) 
	                    return true; 
	            }
				else if (adj != parent) 
	                return true; 
			}
		}
        return false;
	}
	public boolean esConvexo() {
		initVisited();
		int subgrafos=0;
		for (int v = 0; v < this.n; v++) {
			if( !visited[v] ) {
				dfs(v,visited);
				subgrafos++;
			}
		}
		return (subgrafos==1);
	}
	public void dfs(int vertex,boolean visited[]) {
		visited[vertex]=true;
		for (int adj = 0; adj < this.n; adj++) {
			if( vertex!=adj && get(vertex,adj) ) {
				if( !visited[adj] ) {
					dfs(adj,visited);
				}
			}
		}
		
	}
	public int numSubgrafosNoConexos() {
		initVisited();
		int subgrafos=0;
		for (int v = 0; v < this.n; v++) {
			if( !visited[v] ) {
				dfs(v,visited);
				subgrafos++;
			}
		}
		return subgrafos;
	}
	public boolean debilmenteConexo() {
		if(n<=1)return false;
		boolean vis[][]=new boolean[this.capacidad][this.capacidad];
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				if(vis[i][j]) {
					ma[i][j] = false;
					ma[j][i] = false;
					if( !esConvexo() )
						return true;
					ma[i][j] = true;
					ma[j][i] = true;
				}
				vis[i][j]=vis[j][i]=true;
			}
		}
		return false;
	}
	public void ordenacionVertices() {
		Arrays.sort(this.id,0,n);
	}
	public void ordenacionGrados() {
		Arrays.sort(this.id,0,n,new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				int gradoU=gradoVertice(o1);
				int gradoV=gradoVertice(o2);
				if(gradoU>gradoV)
					return -1;
				else if(gradoU==gradoV) {
					return o1.compareTo(o2);
				}
				return 1;
			}
		});
	}
	
	
	
	public boolean esCircular() {
		if(this.n<=2)return false;
		int ciclos = contarCiclos(this.n-1);
		return (ciclos==1);
	}
	public static final int V =5; 
    public int count = 0; 
      
    public void DFS( boolean marked[], int length, int vert, int start) { 
        
        // mark the vertex vert as visited 
        marked[vert] = true; 
          
        // if the path of length (n-1) is found 
        if (length == 0) { 
              
            // mark vert as un-visited to  
            // make it usable again 
            marked[vert] = false; 
              
            // Check if vertex vert end  
            // with vertex start 
            if (ma[vert][start] == true) { 
                count++; 
                return; 
            } else
                return; 
        }
          
        // For searching every possible
        // path of length (n-1)
        for (int i = 0; i < this.n; i++)
            if (!marked[i] && ma[vert][i] == true)
                 // DFS for searching path by 
                // decreasing length by 1 
                DFS( marked, length-1, i, start); 
          
        // marking vert as unvisited to make it 
        // usable again 
        marked[vert] = false; 
    } 
      
    // Count cycles of length N in an  
    // undirected and connected graph. 
    public int contarCiclos( int length) {
        // all vertex are marked un-visited 
        // initially. 
        boolean marked[] = new boolean[this.n];
        // Searching for cycle by using  
        // v-n+1 vertices 
        for (int i = 0; i < this.n - (length - 1); i++) {
            DFS( marked, length-1, i, i);
            // ith vertex is marked as visited 
            // and will not be visited again 
            marked[i] = true;
        }
        return count / 2;  
    }
	
//	verificar si el grafo tiene una estrucutra en estrella
//	estrella() donde n= a la cantidad de vertices
	public boolean esEstrella(){ 
        // initialize number of  
        // vertex with deg 1 and n-1 
        int vertexD1 = 0,  
            vertexDn_1 = 0; 
        if(n==0)return true;
        // check for S1 
        if (n == 1) 
            return (!ma[0][0]);
          
        // check for S2 
        if (n == 2)
        return (ma[0][0] == false &&  
                ma[0][1] == true &&  
                ma[1][0] == true && 
                ma[1][1] == false); 
      
        // check for Sn (n>2) 
        for (int i = 0; i < n; i++) { 
            int degreeI = 0; 
            for (int j = 0; j < n; j++) 
                if (ma[i][j] == true) 
                    degreeI++; 
      
            if (degreeI == 1) 
                vertexD1++; 
            else if (degreeI == n - 1) 
                vertexDn_1++; 
        } 
          
        return (vertexD1 == (n - 1) &&  
                vertexDn_1 == 1); 
    }
	
	public boolean esCompleto() {
		if(this.n==0)return true;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if(get(i, j) == false)return false;
		return true;
	}
	public GrafoDP getComplemento() {
		GrafoDP grafoComp= new GrafoDP(this.n);
		for (int i = 0; i < this.n; i++) {
			grafoComp.insertarVertice( this.id[i] );
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if(get(i,j)==false) {
					grafoComp.ma[i][j]=true;
				}
			}
		}
		return grafoComp;
	}
//	
//	public GrafoDP subgrafo(List<String> vertices) {
//		for (int i = 0; i < vertices.size(); i++) {
//			String vx= vertices.get(i);
//			
//		}
//	}
}
