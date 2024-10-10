import java.util.Arrays;
import java.util.Objects;
public class G implements IGraph {
    /**
     * Vertex array
     * (public for testing purpose only)
     */
    public V vertices[];
    /**
     * Edge array
     * (public for testing purpose only)
     */
    public E edges[];

    private int numVertices;
    private int numEdges;

    // Creates an empty graph.
    public G() {
        vertices = new V[1];
        edges = new E[1];

        numVertices = 0;
        numEdges = 0;
    }

    private void doubleVertexArraySize() {
        vertices = Arrays.copyOf(vertices, vertices.length * 2);
    }

    private void doubleEdgeArraySize() {
        edges = Arrays.copyOf(edges, edges.length * 2);
    }

    public int getNumberOfVertices() {
        return numVertices;
    }

    public int getNumberOfEdges() {
        return numEdges;
    }

    public V[] getVertices() {
        V[] v = new V[getNumberOfVertices()];
        for(int i = 0; i < getNumberOfVertices(); i++){
            v[i] = vertices[i];
        }
        return v;
    }

    public E[] getEdges() {
        E[] e = new E[getNumberOfEdges()];
        for(int i = 0; i < getNumberOfEdges(); i++){
            e[i] = edges[i];
        }
        return e;
    }

    public V insertVertex(String name) throws IllegalArgumentException {
        if(name == null) throw new IllegalArgumentException("name is null");
        if(vertices[vertices.length-1] == null){
            doubleVertexArraySize();
        }
        if (findVertex(name) != null) {
            return null;
        }
        V v = new V(getNumberOfVertices(), name);
        vertices[getNumberOfVertices()] = v;
        numVertices++;
        return v;
    }

    public V findVertex(String name) throws IllegalArgumentException {
        if(name == null) throw new IllegalArgumentException("name is null");
        for(int i = 0; i < vertices.length; i++){
            if(vertices[i] != null && vertices[i].name == name){
                return vertices[i];
            }
        }
        return null;
    }

    public E insertEdge(String v1, String v2, int weight) throws IllegalArgumentException {
        if(v1 == null || v2 == null || v1.equals(v2)) throw new IllegalArgumentException("v1 is null or v2 is null or v1 and v2 are the same");
        if(edges[edges.length-1] != null){
            doubleEdgeArraySize();
        }
        if(findEdge(v1, v2) != null){
            return null;
        }
        V vertex1 = findVertex(v1);
        V vertex2 = findVertex(v2);
        if(vertex1 == null || vertex2 == null) return null;
        E e = new E(vertex1, vertex2, weight);
        edges[getNumberOfEdges()] = e;
        numEdges++;
        return e;
    }

    public E insertEdge(V v1, V v2, int weight) throws IllegalArgumentException {
        if(v1 == null || v2 == null || v1.equals(v2)) throw new IllegalArgumentException("v1 is null or v2 is null or v1 and v2 are the same");
        if(edges[edges.length-1] != null){
            doubleEdgeArraySize();
        }
        if(findEdge(v1, v2) != null){
            return null;
        }
        E e = new E(v1, v2, weight);
        edges[getNumberOfEdges()] = e;
        numEdges++;
        return e;
    }

    public E findEdge(String v1, String v2) throws IllegalArgumentException {
        if(v1 == null || v2 == null || v1.equals(v2)) throw new IllegalArgumentException("v1 is null or v2 is null or v1 and v2 are the same");
        for(int i = 0; i < edges.length; i++){
            if(edges[i] != null && ((edges[i].first.name.equals(v1) && edges[i].second.name.equals(v2) || (edges[i].second.name.equals(v1) && edges[i].first.name.equals(v2))))){
                return edges[i];
            }
        }
        return null;
    }

    public E findEdge(V v1, V v2) throws IllegalArgumentException {
        if(v1 == null || v2 == null || v1.equals(v2)) throw new IllegalArgumentException("v1 is null or v2 is null or v1 and v2 are the same");
        for (E edge : edges) {
            if (edge != null && ((edge.first.equals(v1) && edge.second.equals(v2) || (edge.second.equals(v1) && edge.first.equals(v2))))) {
                return edge;
            }
        }
        return null;
    }

    public int[][] getAdjacencyMatrix() {
        int[][] matrix = new int[getNumberOfVertices()][getNumberOfVertices()];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix.length; j++){
                if(vertices[i] == null || vertices[j] == null || i == j || findEdge(vertices[i], vertices[j]) == null || findEdge(vertices[j], vertices[i]) == null) {
                    matrix[i][j] = -1;
                    matrix[j][i] = -1;
                } else if(findEdge(vertices[i], vertices[j]) != null){
                    matrix[i][j] = findEdge(vertices[i], vertices[j]).weight;
                    matrix[j][i] = findEdge(vertices[i], vertices[j]).weight;
                } else if(findEdge(vertices[j], vertices[i]) != null){
                    matrix[i][j] = findEdge(vertices[j], vertices[i]).weight;
                    matrix[j][i] = findEdge(vertices[j], vertices[i]).weight;
                }
            }
        }
        return matrix;
    }

    public V[] getAdjacentVertices(String name) throws IllegalArgumentException {
        if(name == null) throw new IllegalArgumentException("the vertex is null");

        V[] v = new V[getNumberOfVertices()];
        int[][] matrix = getAdjacencyMatrix();

        if(findVertex(name) == null) return null;

        V help = findVertex(name);
        int index = 0;
        for(int i = 0; i < v.length; i++){
            if(help != null && matrix[i][help.idx] != -1){
                v[index] = vertices[i];
                index++;
            }
        }
        return Arrays.copyOf(v,index);
    }

    public V[] getAdjacentVertices(V v) throws IllegalArgumentException {
        if(v == null) throw new IllegalArgumentException("the vertex is null");

        V[] adjacentV = new V[getNumberOfVertices()];
        int[][] matrix = getAdjacencyMatrix();
        int index = 0;
        for(int i = 0; i < adjacentV.length; i++){
            if(matrix[i][v.idx] != -1){
                adjacentV[index] = vertices[i];
                index++;
            }
        }
        return Arrays.copyOf(adjacentV,index);
    }}
