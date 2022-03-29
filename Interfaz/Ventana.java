import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class Ventana extends JFrame implements MouseListener{
    private JPanel pnlSuperior, pnlInferior, pnlDinero, pnlPasajero, pnlTarjeta;
    private JTextField jtxtBuscador;
    private JButton jbtDinero, jbtPasajero, jbtTarjeta;
    private java.util.List<java.util.List<String>> lsTroncal, lsDual, lsSitp;
    private JTable tblTabla = new JTable();
    private boolean[] columnas = new boolean[11];
    private boolean[] tipos = new boolean[3];
    private JScrollPane tabla;

    Ventana(java.util.List<java.util.List<String>> cadenas){

        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(110,190,240));
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        this.Cargar(cadenas);
        for(int i=0;i<11;i++)columnas[i]=true;
        for(int i=0;i<3;i++)tipos[i]=false;
        tipos[0]=true;


        pnlSuperior = new JPanel();
        pnlSuperior.setMaximumSize(new Dimension(100000,150));
        pnlSuperior.setPreferredSize(new Dimension(1000,150));
        pnlSuperior.setMinimumSize(new Dimension(1,150));
        pnlSuperior.setBackground(this.getContentPane().getBackground());
        pnlSuperior.setLayout(new BoxLayout(pnlSuperior,BoxLayout.Y_AXIS));

        JPanel pnlBuscador = new JPanel();
        pnlBuscador.setLayout(new BoxLayout(pnlBuscador,BoxLayout.X_AXIS));
        pnlBuscador.setPreferredSize(new Dimension(10000,15));
        pnlBuscador.setMaximumSize(new Dimension(100000,15));
        pnlBuscador.setBackground(this.getContentPane().getBackground());
        
        jtxtBuscador = new JTextField();

        pnlBuscador.add(Box.createRigidArea(new Dimension(75,0)));
        pnlBuscador.add(jtxtBuscador);
        pnlBuscador.add(Box.createRigidArea(new Dimension(75,0)));

        JPanel pnlBotones = new JPanel();
        pnlBotones.setLayout(new BoxLayout(pnlBotones,BoxLayout.X_AXIS));
        pnlBotones.setPreferredSize(new Dimension(10000,40));
        pnlBotones.setBackground(this.getContentPane().getBackground());

        jbtDinero = new JButton();
        jbtDinero.setMaximumSize(new Dimension(40,40));
        jbtDinero.setPreferredSize(new Dimension(40,40));
        jbtDinero.setMinimumSize(new Dimension(40,40));

        jbtPasajero = new JButton();
        jbtPasajero.setMaximumSize(new Dimension(40,40));
        jbtPasajero.setPreferredSize(new Dimension(40,40));
        jbtPasajero.setMinimumSize(new Dimension(40,40));

        jbtTarjeta = new JButton();
        jbtTarjeta.setMaximumSize(new Dimension(40,40));
        jbtTarjeta.setPreferredSize(new Dimension(40,40));
        jbtTarjeta.setMinimumSize(new Dimension(40,40));

        pnlBotones.add(Box.createRigidArea(new Dimension(75,0)));
        pnlBotones.add(jbtDinero);
        pnlBotones.add(Box.createHorizontalGlue());
        pnlBotones.add(jbtPasajero);
        pnlBotones.add(Box.createHorizontalGlue());
        pnlBotones.add(jbtTarjeta);
        pnlBotones.add(Box.createRigidArea(new Dimension(75,0)));

        pnlDinero = new JPanel();

        

        
        pnlSuperior.add(Box.createRigidArea(new Dimension(0,20)));
        pnlSuperior.add(pnlBuscador);
        pnlSuperior.add(Box.createRigidArea(new Dimension(0,20)));
        pnlSuperior.add(pnlBotones);
        pnlSuperior.add(Box.createVerticalGlue());

        pnlInferior = new JPanel();
        pnlInferior.setLayout(new BorderLayout());
        pnlInferior.setBackground(this.getContentPane().getBackground());

        JPanel bordeLat = new JPanel();
        bordeLat.setMaximumSize(new Dimension(15,100000));
        bordeLat.setBackground(this.getContentPane().getBackground());
        JPanel bordeLatIzq = new JPanel();
        bordeLatIzq.setMaximumSize(new Dimension(15,100000));
        bordeLatIzq.setBackground(this.getContentPane().getBackground());
        JPanel bordeInf = new JPanel();
        bordeInf.setMaximumSize(new Dimension(1000000,15));
        bordeInf.setBackground(this.getContentPane().getBackground());

        JPanel pnlPestañas = new JPanel();
        pnlPestañas.setMaximumSize(new Dimension(100000,25));
        pnlPestañas.setPreferredSize(new Dimension(1000,25));
        pnlPestañas.setMinimumSize(new Dimension(1,25));
        pnlPestañas.setLayout(new BoxLayout(pnlPestañas,BoxLayout.X_AXIS));
        pnlPestañas.setBackground(this.getContentPane().getBackground());

        JButton jbtSitp = new JButton("Sitp");
        jbtSitp.setPreferredSize(new Dimension(1000, 25));
        jbtSitp.setBackground(Color.BLUE);

        JButton jbtTroncal = new JButton("Troncal");
        jbtTroncal.setPreferredSize(new Dimension(1000, 25));
        jbtTroncal.setBackground(Color.RED);
        
        JButton jbtDual = new JButton("Dual");
        jbtDual.setPreferredSize(new Dimension(1000, 25));
        jbtDinero.addMouseListener(this);
        jbtDual.setBackground(Color.YELLOW);

        pnlPestañas.add(Box.createRigidArea(new Dimension(10,0)));
        pnlPestañas.add(jbtTroncal);
        pnlPestañas.add(jbtDual);
        pnlPestañas.add(jbtSitp);
        pnlPestañas.add(Box.createRigidArea(new Dimension(10,0)));
        
        tblTabla = new JTable(new ModeloTabla(mostrar(),columnas));
        tabla = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabla.setViewportView(tblTabla);
        //tabla.setBackground(Color.GREEN);

        

        pnlInferior.add(pnlPestañas,BorderLayout.NORTH);
        pnlInferior.add(bordeLat,BorderLayout.EAST);
        pnlInferior.add(bordeLatIzq,BorderLayout.WEST);
        pnlInferior.add(bordeInf,BorderLayout.SOUTH);
        pnlInferior.add(tabla,BorderLayout.CENTER);

        


        this.add(pnlSuperior);
        this.add(pnlInferior);
        this.setVisible(true);
    }

    private java.util.List<java.util.List<String>> mostrar(){
        java.util.List<java.util.List<String>> datos = new ArrayList<java.util.List<String>>();
        if(tipos[0])
            for (java.util.List<String> list : lsTroncal)
                datos.add(list);

        if(tipos[1])
            for (java.util.List<String> list : lsDual)
                datos.add(list);
                
        if(tipos[2])
            for (java.util.List<String> list : lsSitp)
                datos.add(list);

        if(tipos[3])
            for (java.util.List<String> list : lsParadas)
                datos.add(list);
        return datos;
    }


    public void Cargar(java.util.List<java.util.List<String>> cadenas){
        lsDual = new ArrayList<java.util.List<String>>();
        lsTroncal = new ArrayList<java.util.List<String>>();
        lsSitp = new ArrayList<java.util.List<String>>();
        System.out.println(cadenas.size() + "< tamaño");
        for(java.util.List<String> l : cadenas){
            switch (l.get(0)){
                case "Troncal":
                    lsTroncal.add(l);
                break;
                case "Sitp":
                        lsSitp.add(l);
                break;
                case "Dual":
                        lsDual.add(l);
                break;
                default:
            }
        }
        tblTabla = new JTable(new ModeloTabla(mostrar(),columnas));
        repaint();
    }

    /**
	 * Metodo que evalua la cadena.
	 * @param c la columna a evaluar
	 * @param min valor minimo que retorna
	 * @return largo de la columna
	 */
	private int autoAjustado(int c, int min)
	{
		int largo = 0;
        tblTabla.get
		for(int i = 0; i < lista.size(); i++)
		{
			String[] cadena = lista.get(i).getAtributosTabla();
			String caden = cadena[c];
			try { largo = (caden.length() > largo) ? caden.length() : largo; } catch (Exception e) {}
		}
		largo = (min > largo) ? min : largo;
		largo = largo * 9;
		return largo;
	}

    private void Actualizar(){
        //tabla.removeAll();
        tblTabla.setModel(new DefaultTableModel());
        tabla.setViewportView(tblTabla);
        //tabla.add(tblTabla);
        revalidate();
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==jbtDinero){
            Actualizar();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}