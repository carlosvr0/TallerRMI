//GUIServidor.java
import javax.swing.*;

public class GUIServidor extends JFrame {

	private JTextArea areaTexto;

	public GUIServidor() {
		super("Servidor RMI");
		areaTexto = new JTextArea();
		areaTexto.setEditable(false);
		getContentPane().add(new JScrollPane(areaTexto));

		setSize(600, 400);
		setVisible(true);
	}

	public void anadirEntradas(String texto) {
		areaTexto.append(texto + "\n");
	}
}
