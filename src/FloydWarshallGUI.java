import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class FloydWarshallGUI extends JFrame {

    static final int INF = Integer.MAX_VALUE;
    private Grafo grafo;
    private JTextArea outputArea;
    private JTextField[][] distanceFields;
    private JComboBox<String> startCityBox;
    private JComboBox<String> endCityBox;

    public FloydWarshallGUI() {
        setTitle("Floyd-Warshall - Rutas más cortas");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] ciudades = {"Quito", "Guayaquil", "Cuenca", "Esmeraldas", "Napo"};
        grafo = new Grafo(ciudades);

        JPanel inputPanel = new JPanel(new GridLayout(ciudades.length + 1, ciudades.length + 1));
        distanceFields = new JTextField[ciudades.length][ciudades.length];

        for (int i = 0; i <= ciudades.length; i++) {
            for (int j = 0; j <= ciudades.length; j++) {
                if (i == 0 && j == 0) {
                    inputPanel.add(new JLabel(""));
                } else if (i == 0) {
                    inputPanel.add(new JLabel(ciudades[j - 1]));
                } else if (j == 0) {
                    inputPanel.add(new JLabel(ciudades[i - 1]));
                } else if (i == j) {
                    distanceFields[i - 1][j - 1] = new JTextField("0");
                    distanceFields[i - 1][j - 1].setEditable(false);
                    inputPanel.add(distanceFields[i - 1][j - 1]);
                } else {
                    distanceFields[i - 1][j - 1] = new JTextField();
                    inputPanel.add(distanceFields[i - 1][j - 1]);
                }
            }
        }

        JPanel controlPanel = new JPanel(new GridLayout(4, 2));
        startCityBox = new JComboBox<>(ciudades);
        endCityBox = new JComboBox<>(ciudades);
        JButton calculateButton = new JButton("Calcular Ruta Más Corta");
        JButton showMatrixButton = new JButton("Mostrar Matriz de Adyacencia");

        controlPanel.add(new JLabel("Ciudad de Inicio:"));
        controlPanel.add(startCityBox);
        controlPanel.add(new JLabel("Ciudad de Destino:"));
        controlPanel.add(endCityBox);
        controlPanel.add(calculateButton);
        controlPanel.add(showMatrixButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        add(inputPanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.CENTER);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularRutaMasCorta();
            }
        });

        showMatrixButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarMatrizDeAdyacencia();
            }
        });

        setVisible(true);
    }

    private void calcularRutaMasCorta() {
        try {
            for (int i = 0; i < grafo.getCiudades().length; i++) {
                for (int j = i + 1; j < grafo.getCiudades().length; j++) {
                    String input = distanceFields[i][j].getText().trim();
                    int distancia = input.equalsIgnoreCase("INF") ? INF : Integer.parseInt(input);
                    grafo.agregarArista(grafo.getCiudades()[i], grafo.getCiudades()[j], distancia);
                }
            }

            grafo.floydWarshall();
            String ciudadInicio = (String) startCityBox.getSelectedItem();
            String ciudadDestino = (String) endCityBox.getSelectedItem();
            outputArea.setText(grafo.obtenerRutaMasCorta(ciudadInicio, ciudadDestino));

        } catch (Exception e) {
            outputArea.setText("Error en la entrada de datos. Asegúrese de ingresar solo números o 'INF'.");
        }
    }

    private void mostrarMatrizDeAdyacencia() {
        outputArea.setText(grafo.imprimirMatrizDeAdyacencia());
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FloydWarshallGUI();
            }
        });
    }
}
