package grafos;

import java.util.ArrayList;
import java.util.List;

public class Grafo {
    private List<Vertice> vertices;
    private List<Aresta> arestas;

    public Grafo() {
        this.vertices = new ArrayList<>();
        this.arestas = new ArrayList<>();
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    public List<Aresta> getArestas() {
        return arestas;
    }

    public void adicionarVertice(Vertice vertice) {
        vertices.add(vertice);
    }

    public void adicionarAresta(Aresta aresta) {
        arestas.add(aresta);
    }

    public void removeVertice(Vertice vertice) {
        List<Aresta> adjacencias = vertice.getAdjacencias();

        for (int i = 0; i <= adjacencias.size(); i++) {
            Aresta arestaAux = adjacencias.get(i);
            Vertice verticeAuxOrigem = arestaAux.getOrigem();
            Vertice verticeAuxDestino = arestaAux.getDestino();

            for (int j = 0; j <= verticeAuxOrigem.getAdjacencias().size(); j++) {
                if (verticeAuxOrigem.getAdjacencias().get(j) == arestaAux) {
                    verticeAuxOrigem.getAdjacencias().remove(j);
                }
            }

            for (int k = 0; k <= verticeAuxDestino.getAdjacencias().size(); k++) {
                if (verticeAuxDestino.getAdjacencias().get(k) == arestaAux) {
                    verticeAuxDestino.getAdjacencias().remove(k);
                }
            }
        }

        for (int i = 0; i <= vertices.size(); i++) {
            if (vertices.get(i) == vertice) {
                vertices.remove(i);
            }
        }

        System.out.println("VERTICE: " + vertice + " REMOVIDO COM SUCESSO!");

    }

    public void removeAresta(Aresta aresta) {
        Vertice verticeOrigem = aresta.getOrigem();
        Vertice verticeDestino = aresta.getDestino();

        for (int i = 0; i <= verticeOrigem.getAdjacencias().size(); i++) {
            if (aresta == verticeOrigem.getAdjacencias().get(i)) {
                verticeOrigem.getAdjacencias().remove(i);
            }
        }
        for (int i = 0; i <= verticeDestino.getAdjacencias().size(); i++) {
            if (aresta == verticeDestino.getAdjacencias().get(i)) {
                verticeDestino.getAdjacencias().remove(i);
            }
        }

        for (int i = 0; i <= verticeDestino.getVerticesAdjacentes().size(); i++) {
            if (verticeOrigem == verticeDestino.getVerticesAdjacentes().get(i)) {
                verticeDestino.getVerticesAdjacentes().remove(i);
            }
        }

        for (int i = 0; i <= verticeOrigem.getVerticesAdjacentes().size(); i++) {
            if (verticeDestino == verticeOrigem.getVerticesAdjacentes().get(i)) {
                verticeOrigem.getVerticesAdjacentes().remove(i);
            }
        }

    }

    public List<Vertice> finalVertices(Aresta aresta) {
        List<Vertice> vertices = new ArrayList<Vertice>();
        vertices.add(aresta.getOrigem());
        vertices.add(aresta.getDestino());
        return vertices;
    }

    public Vertice oposto(Aresta aresta, Vertice vertice) throws Exception {
        if (vertice != aresta.getDestino() && vertice != aresta.getOrigem()) {
            throw new Exception("o vertice nao pertence a aresta informada.");
        } else if (vertice == aresta.getDestino()) {
            return aresta.getOrigem();
        } else {
            return aresta.getDestino();
        }
    }

    public Boolean ehAdjacente(Vertice vertice1, Vertice vertice2) {
        Boolean aux = false;
        int lengthVertice1 = vertice1.getAdjacencias().size();
        int lengthVertice2 = vertice2.getAdjacencias().size();

        for (int i = 0; i <= lengthVertice1; i++) {
            for (int j = 0; j <= lengthVertice2; j++) {
                if (vertice1.getAdjacencias().get(i) == vertice2.getAdjacencias().get(j)) {
                    aux = true;
                }

            }
        }
        return aux;
    }

    public void substituirVertice(Vertice vertice, int valor) {
        vertice.setValor(valor);
    }

    public void substituirAresta(Aresta aresta, int peso) {
        aresta.setPeso(peso);
    }

    public Vertice inserirVertice(int valor) {
        Vertice verticeAux = new Vertice(valor);
        vertices.add(verticeAux);
        return verticeAux;
    }

    public Aresta inserirAresta(Vertice verticeOrigem, Vertice verticeDestino, int peso) {
        Aresta arestaAux = new Aresta(verticeOrigem, verticeDestino, peso);
        arestas.add(arestaAux);
        verticeOrigem.adicionarAresta(arestaAux);
        verticeDestino.adicionarAresta(arestaAux);
        verticeOrigem.getVerticesAdjacentes().add(verticeDestino);
        verticeDestino.getVerticesAdjacentes().add(verticeOrigem);
        return arestaAux;
    }

    public List<Aresta> arestasIncidentes(Vertice vertice) {
        List<Aresta> arestasIncidentes = new ArrayList<Aresta>();
        for(int i=0;i<=arestas.size();i++) {
            if(vertice == arestas.get(i).getDestino() || vertice == arestas.get(i).getOrigem()) {
                arestasIncidentes.add(arestas.get(i));
            }
        }
        return arestasIncidentes;
    }

    public List<Aresta> arestas(){
        return arestas;
    }

    public List<Vertice> vertices(){
        return vertices;
    }


    public void listarArestas(){
        System.out.print("[");
        for (int i = 0; i < arestas.size(); i++) {
            int origem = arestas.get(i).getOrigem().getValor();
            int destino = arestas.get(i).getDestino().getValor();
            int peso = arestas.get(i).getPeso();

            System.out.print("V"+origem + "," + "V"+destino + "{");
            System.out.print(peso);
            System.out.print("}");

            if (i < arestas.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }


    public void listarVertices(){
        System.out.print("[");
        for (int i = 0; i < vertices.size(); i++) {
            int valor = vertices.get(i).getValor();
            System.out.print("v" + valor);
            if (i < vertices.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

}