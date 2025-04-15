import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.*;
import java.util.ArrayList;
import java.util.List; 

public class ConsultarInscripciones extends JFrame{
    
    private JTextField campoIdOferta;
    private JTextArea areaInscripciones;
    private JButton botonBuscar;

    private InterfazBolsaEmpleos servidor;

    private void buscarInscripciones() {
        
        String id = campoIdOferta.getText();
        areaInscripciones.setText("");
        try{
            List<Inscripcion> lista = servidor.consultarInscripcionesPorOferta(id);
            if (lista.isEmpty()) {
                areaInscripciones.append("No hay inscripciones para esta oferta");
            }else{
                for(Inscripcion i: lista){
                    areaInscripciones.append(
                        "Nombre: " + i.getNombreAspirante() + "\n" + 
                        "Cedula: " + i.getCedula() + "\n" +
                        " -------------------------------------\n "
                    );
                }
            }
        }catch(RemoteException e){
            areaInscripciones.setText("Error al consultar inscripciones");
            e.printStackTrace();
        }
    }

    public ConsultarInscripciones(InterfazBolsaEmpleos servidor){
        super("Consultar inscripciones");
        this.servidor = servidor;

        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.add(new JLabel("ID de la oferta"));
        campoIdOferta = new JTextField(10);
        panelSuperior.add(campoIdOferta);

        botonBuscar = new JButton("Buscar");
        panelSuperior.add(botonBuscar);

        areaInscripciones = new JTextArea(15,40);
        areaInscripciones.setEditable(false);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(areaInscripciones), BorderLayout.CENTER);
        botonBuscar.addActionListener(e -> buscarInscripciones());
        
        setSize(500, 400);
        setVisible(true);
    }
    
}
