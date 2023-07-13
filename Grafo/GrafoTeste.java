package grafos;

public class GrafoTeste {

    public static void main(String[] args) {

        Grafo g1 = new Grafo();
        Vertice v0 = new Vertice(1);
        Vertice v1 = new Vertice(2);
        Vertice v2 = new Vertice(3);
        Vertice v4 = new Vertice(4);
        Vertice v5 = new Vertice(5);
        g1.adicionarVertice(v0);
        g1.adicionarVertice(v1);
        g1.adicionarVertice(v2);
        g1.adicionarVertice(v4);
        g1.adicionarVertice(v5);
        g1.inserirAresta(v0,v1,5);
        g1.inserirAresta(v1,v2,10);
        g1.inserirAresta(v2,v0,15);

        g1.listarArestas();
        g1.listarVertices();




    }
}