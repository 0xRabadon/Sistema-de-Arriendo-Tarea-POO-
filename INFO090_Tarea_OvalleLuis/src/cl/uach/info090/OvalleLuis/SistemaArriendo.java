package cl.uach.info090.OvalleLuis;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Sistema de arriendo de vehiculos, tiene funciones como ver el tiempo prestado de un vehiculo
 * generar boletas y ver el estado de este. 
 * @author Luis Ovalle
 * 
 */
public class SistemaArriendo extends JFrame implements ActionListener {
    
    private static SistemaArriendo sistema = new SistemaArriendo();
    private CreadorBoletaCL creadorBoleta;
    private Item itemActual;
    private ArrayList<Boleta> listaBoletas;
    private JPanel panelBotonesItems;
    private JTextField identificador, desc, valorBaseTF, valorHoraTF, estado, clienteTF, inicioArriendo;
    private JButton arriendoBoton, exportar;
    private JList<Boleta> jListBoletas;
    private DefaultListModel<Boleta> listModelBoletas;

    private SistemaArriendo() {
        creadorBoleta = new CreadorBoletaCL();
        listaBoletas = new ArrayList<>();
        itemActual = null;

        setTitle("Sistema de Arriendo");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout(10, 10));

        initComponents();
        cargarCSV();
    }

    public static SistemaArriendo getInstance() {
        return sistema;
    }

    private void initComponents() {
        // PANEL DE ITEMS, LA CUADRICULA
        JPanel panelIzquierdo = new JPanel(new BorderLayout(5, 5));
        panelBotonesItems = new JPanel(new GridLayout(4, 4, 8, 8));
        panelBotonesItems.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelIzquierdo.add(panelBotonesItems, BorderLayout.CENTER);
        
        // HISTORIAL DE BOLETAS	
        JPanel panelBoletas = new JPanel(new BorderLayout());
        panelBoletas.setBorder(BorderFactory.createTitledBorder("Últimas boletas"));
        listModelBoletas = new DefaultListModel<>();
        jListBoletas = new JList<>(listModelBoletas);
        panelBoletas.add(new JScrollPane(jListBoletas), BorderLayout.CENTER);
        panelBoletas.setPreferredSize(new Dimension(400, 150));
        panelIzquierdo.add(panelBoletas, BorderLayout.SOUTH);
        getContentPane().add(panelIzquierdo, BorderLayout.CENTER);

        // PANEL DE DETALLE
        JPanel panelDerecho = new JPanel(null);
        panelDerecho.setPreferredSize(new Dimension(420, 500));
        panelDerecho.setBorder(BorderFactory.createEtchedBorder());
        int xLabel = 20, xField = 140, ancho = 240, alto = 28, separacionY = 35;
        int y = 20;

        identificador = crearCampo(panelDerecho, "Serie :", xLabel, xField, y, ancho, alto);
        y += separacionY;
        desc = crearCampo(panelDerecho, "Descripcion :", xLabel, xField, y, ancho, alto);
        y += separacionY;
        valorBaseTF = crearCampo(panelDerecho, "Valor base :", xLabel, xField, y, ancho, alto);
        y += separacionY;
        valorHoraTF = crearCampo(panelDerecho, "Valor hora :", xLabel, xField, y, ancho, alto);
        y += separacionY;
        
        JLabel lbEstado = new JLabel("Estado :");
        lbEstado.setBounds(xLabel, y, 100, alto);
        panelDerecho.add(lbEstado);
        estado = new JTextField();
        estado.setBounds(xField, y, 110, alto);
        estado.setEditable(false);
        panelDerecho.add(estado);

        arriendoBoton = new JButton("Arrendar");
        arriendoBoton.setBounds(xField + 120, y, 120, alto);
        arriendoBoton.addActionListener(this);
        panelDerecho.add(arriendoBoton);
        y += separacionY;

        clienteTF = crearCampo(panelDerecho, "Cliente :", xLabel, xField, y, ancho, alto);
        y += separacionY;
        inicioArriendo = crearCampo(panelDerecho, "Inicio :", xLabel, xField, y, ancho, alto);

        // BOTON PARA EXPORTAR BOLETAS
        exportar = new JButton("Exportar boletas");
        exportar.setBounds(50, 450, 300, 35);
        exportar.addActionListener(this);
        panelDerecho.add(exportar);

        getContentPane().add(panelDerecho, BorderLayout.EAST);
    }
    
/**
 * Funcion para automatizar la creacion de los TextFields
 * @param panel Panel donde ira el TextField
 * @param texto Texto del label
 * @param xl Coordenada X del Label
 * @param xTf Coordenada X del TextField
 * @param y Coordenada Y (tando del Label y del TextField)
 * @param ancho Ancho del TextField
 * @param alto Alto del TextField
 * @return
 */
    private JTextField crearCampo(JPanel panel, String texto, int xl, int xTf, int y, int ancho, int alto) {
        JLabel lb = new JLabel(texto);
        lb.setBounds(xl, y, 100, alto);
        panel.add(lb);
        JTextField tf = new JTextField();
        tf.setBounds(xTf, y, ancho, alto);
        tf.setEditable(false);
        panel.add(tf);
        return tf;
    }

    /**
     * Carga el CSV con los items para luego cargarlo en una cuadricula con solo su ID
     */
    private void cargarCSV() {
        File csvFile = new File("data/item_arriendo.csv");
        if (!csvFile.exists()) {
            JOptionPane.showMessageDialog(this, "No se encontró el archivo de datos en data/items_arriendo.csv", "Error de archivo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] tokens = linea.split(",");
                if (tokens.length < 5) continue;

                String id = tokens[0].trim();
                String tipo = tokens[1].trim().toLowerCase();
                String desc = tokens[2].trim();
                double base = Double.parseDouble(tokens[3].trim());
                double hora = Double.parseDouble(tokens[4].trim());
                boolean arrendado = false;

                Item nuevoItem;
                switch (tipo) {
                    case "bicicleta": nuevoItem = new Bicicleta(id, desc, base, hora, arrendado, creadorBoleta);
                    break;
                    case "kayak": nuevoItem = new Kayak(id, desc, base, hora,arrendado, creadorBoleta);
                    break;
                    case "segway": nuevoItem = new Segway(id, desc, base, hora, arrendado, creadorBoleta);
                    break;
                    default: continue;
                }

                nuevoItem.addActionListener(this);
                panelBotonesItems.add(nuevoItem);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al interpretar el CSV: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra las propiedades del objeto Item usado
     * @param r Objeto de tipo Item seleccionado
     */
    public void mostrarDetallesItem(Item r) {
        if (r == null) {
            identificador.setText(""); desc.setText(""); valorBaseTF.setText("");
            valorHoraTF.setText(""); estado.setText(""); clienteTF.setText(""); inicioArriendo.setText("");
            arriendoBoton.setVisible(false);
            return;
        }

        identificador.setText(r.getItemId());
        desc.setText(r.getItemDescription());
        valorBaseTF.setText(String.format("%.0f", r.getValorBase()));
        valorHoraTF.setText(String.format("%.0f", r.getValorHora()));

        arriendoBoton.setVisible(true);
        if (r.enArriendo()) {
            estado.setText("arrendado");
            estado.setForeground(Color.RED);
            clienteTF.setText(r.getCliente());
            inicioArriendo.setText(r.getFechaInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            arriendoBoton.setText("Finalizar");
            arriendoBoton.setBackground(new Color(230, 80, 80));
        } else {
            estado.setText("disponible");
            estado.setForeground(new Color(34, 139, 34));
            clienteTF.setText("");
            inicioArriendo.setText("");
            arriendoBoton.setText("Arrendar");
            arriendoBoton.setBackground(new Color(76, 175, 80));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource();
        if (origen instanceof Item) {
            if (itemActual != null) itemActual.setBorder(BorderFactory.createEmptyBorder());
            
            itemActual = (Item) origen;
            itemActual.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            mostrarDetallesItem(itemActual);
            return;
        }

        if (origen == arriendoBoton && itemActual != null) {
            if (!itemActual.enArriendo()) {
                String clienteIngresado = JOptionPane.showInputDialog(this, "Arrendar item " + itemActual.getItemId() + "\n\nCliente:", "Ingreso de Arriendo", JOptionPane.PLAIN_MESSAGE);
                if (clienteIngresado != null && !clienteIngresado.trim().isEmpty()) {
                    itemActual.arrendar(clienteIngresado.trim());
                    mostrarDetallesItem(itemActual);
                }
            } else {
                Boleta b = itemActual.devolver();
                if (b != null) {
                    listaBoletas.add(b);
                    listModelBoletas.insertElementAt(b, 0);
                    JOptionPane.showMessageDialog(this, b.detalle(), "Boleta Generada", JOptionPane.INFORMATION_MESSAGE);
                }
                mostrarDetallesItem(itemActual);
            }
            return;
        }

        if (origen == exportar) {
            if (listaBoletas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay boletas generadas para exportar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            File dir = new File("boletas_exportadas");
            if (!dir.exists()) dir.mkdir();

            for (Boleta b : listaBoletas) {
                BoletaCL bcl = (BoletaCL) b;
                String fechaStr = bcl.getFecha();
                String nombreArchivo = "boletas_exportadas/" + fechaStr + "_" + bcl.getCliente().toLowerCase() + ".txt";
                
                try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
                    pw.print(b.detalle());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al escribir archivo: " + nombreArchivo, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            JOptionPane.showMessageDialog(this, "Boletas exportadas exitosamente en la carpeta 'boletas_exportadas/'.", "Exito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
    	SistemaArriendo.getInstance().setVisible(true);
    }
}

