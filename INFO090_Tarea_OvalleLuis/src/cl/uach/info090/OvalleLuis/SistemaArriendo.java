package cl.uach.info090.OvalleLuis;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SistemaArriendo extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = -8266379086990121043L;

	public SistemaArriendo getInstance() {
		return null;
	}
	
	public void main(String args[]) {
		setTitle("Sistema Arriendo");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//JLabel ultBoletas = new JLabel("Ultimas Boletas");
		
		// Cuadricula grid idk
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(1600,900);
        // Establecer el GridLayout: 3 filas, 3 columnas, con 5px de separación
        frame.setLayout(new GridLayout(3, 3, 5, 5));

        // Agregar 9 botones (se acomodarán automáticamente en la cuadrícula)
        for (int i = 1; i <= 9; i++) {
            frame.add(new JButton("Botón " + i));
        }

        // Mostrar la ventana
        frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
