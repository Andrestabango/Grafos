import java.util.Arrays;

public class Grafo {
    static final int INF = Integer.MAX_VALUE;
    private int numVertices;
    private int[][] distancias;
    private int[][] predecesores;
    private String[] ciudades;

    public Grafo(String[] ciudades) {
        this.ciudades = ciudades;
        this.numVertices = ciudades.length;
        distancias = new int[numVertices][numVertices];
        predecesores = new int[numVertices][numVertices];

        for (int i = 0; i < numVertices; i++) {
            Arrays.fill(distancias[i], INF);
            Arrays.fill(predecesores[i], -1);
            distancias[i][i] = 0;
            predecesores[i][i] = i;
        }
    }

    public void agregarArista(String ciudad1, String ciudad2, int distancia) {
        int i = indiceDe(ciudad1);
        int j = indiceDe(ciudad2);
        if (i != -1 && j != -1) {
            distancias[i][j] = distancia;
            distancias[j][i] = distancia; // Hacer la distancia simétrica
            predecesores[i][j] = i;
            predecesores[j][i] = j;
        }
    }

    private int indiceDe(String ciudad) {
        for (int i = 0; i < ciudades.length; i++) {
            if (ciudades[i].equals(ciudad)) {
                return i;
            }
        }
        return -1;
    }

    public void floydWarshall() {
        for (int k = 0; k < numVertices; k++) {
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    if (distancias[i][k] != INF && distancias[k][j] != INF && distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        predecesores[i][j] = predecesores[k][j];
                    }
                }
            }
        }
    }

    public String obtenerRutaMasCorta(String ciudad1, String ciudad2) {
        int i = indiceDe(ciudad1);
        int j = indiceDe(ciudad2);
        if (i == -1 || j == -1) {
            return "Ciudad no encontrada.";
        }
        if (distancias[i][j] == INF) {
            return "No hay ruta disponible entre " + ciudad1 + " y " + ciudad2;
        } else {
            StringBuilder ruta = new StringBuilder();
            ruta.append("La distancia más corta entre ").append(ciudad1).append(" y ").append(ciudad2).append(" es ").append(distancias[i][j]).append(".\n");
            ruta.append("La ruta es: ");
            imprimirRuta(i, j, ruta);
            ruta.append(ciudades[j]);
            return ruta.toString();
        }
    }

    private void imprimirRuta(int i, int j, StringBuilder ruta) {
        if (i != j) {
            imprimirRuta(i, predecesores[i][j], ruta);
        }
        ruta.append(ciudades[j]).append(" -> ");
    }

    public String imprimirMatrizDeAdyacencia() {
        StringBuilder matriz = new StringBuilder();
        matriz.append("Matriz de Adyacencia:\n");
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (distancias[i][j] == INF) {
                    matriz.append("INF ");
                } else {
                    matriz.append(distancias[i][j]).append(" ");
                }
            }
            matriz.append("\n");
        }
        return matriz.toString();
    }

    public Grafo(int numVertices, int[][] distancias, int[][] predecesores, String[] ciudades) {
        this.numVertices = numVertices;
        this.distancias = distancias;
        this.predecesores = predecesores;
        this.ciudades = ciudades;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public int[][] getDistancias() {
        return distancias;
    }

    public void setDistancias(int[][] distancias) {
        this.distancias = distancias;
    }

    public int[][] getPredecesores() {
        return predecesores;
    }

    public void setPredecesores(int[][] predecesores) {
        this.predecesores = predecesores;
    }

    public String[] getCiudades() {
        return ciudades;
    }

    public void setCiudades(String[] ciudades) {
        this.ciudades = ciudades;
    }
}