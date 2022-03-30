    import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class Ventana extends JFrame implements MouseListener{
    private JPanel pnlSuperior, pnlInferior, pnlDinero, pnlPasajero, pnlTarjeta;
    private JTextField jtxtBuscador;
    private JButton jbtDinero, jbtPasajero, jbtTarjeta;
    private java.util.List<java.util.List<String>> lsTroncal, lsDual, lsSitp, lsParada, lsEstacion;
    private JTable tblTabla = new JTable();
    private boolean[] columnas = new boolean[11];
    private boolean[] tipos = new boolean[3];
    private JScrollPane tabla;
    private Color base = new Color(110,190,240);

    public Ventana(){

        //Configuracion del JFrame
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(base);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        //Basura
        for(int i=0;i<11;i++)columnas[i]=true;
        for(int i=0;i<3;i++)tipos[i]=false;
        tipos[0]=true;

        //Configuracion Panel Superior
        pnlSuperior = new JPanel();
        pnlSuperior.setMaximumSize(new Dimension(100000,150));
        pnlSuperior.setPreferredSize(new Dimension(1000,150));
        pnlSuperior.setMinimumSize(new Dimension(1,150));
        pnlSuperior.setBackground(base);
        pnlSuperior.setLayout(new BoxLayout(pnlSuperior,BoxLayout.Y_AXIS));

        //Configuracion del panel de busqueda
        JPanel pnlBuscador = new JPanel();
        pnlBuscador.setLayout(new BoxLayout(pnlBuscador,BoxLayout.X_AXIS));
        pnlBuscador.setPreferredSize(new Dimension(10000,15));
        pnlBuscador.setMaximumSize(new Dimension(100000,15));
        pnlBuscador.setBackground(base);
        
        //Jtext del buscador
        jtxtBuscador = new JTextField();

        //Añadir elementos al panel de busqueda
        pnlBuscador.add(Box.createRigidArea(new Dimension(75,0)));
        pnlBuscador.add(jtxtBuscador);
        pnlBuscador.add(Box.createRigidArea(new Dimension(75,0)));

        //Basura
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

        //Configuracion del panel inferior
        pnlInferior = new JPanel();
        pnlInferior.setLayout(new BorderLayout());
        pnlInferior.setBackground(base);

        //Bordes del panel inferior
        JPanel bordeLat = new JPanel();
        bordeLat.setMaximumSize(new Dimension(15,100000));
        bordeLat.setBackground(this.getContentPane().getBackground());
        JPanel bordeLatIzq = new JPanel();
        bordeLatIzq.setMaximumSize(new Dimension(15,100000));
        bordeLatIzq.setBackground(this.getContentPane().getBackground());
        JPanel bordeInf = new JPanel();
        bordeInf.setMaximumSize(new Dimension(1000000,15));
        bordeInf.setBackground(this.getContentPane().getBackground());

        //Basura
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
        
        //tblTabla = new JTable(new ModeloTabla(mostrar(),columnas));
        Actualizar();
        tabla = new JScrollPane(this.tblTabla,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //tabla.setBounds(0, 0, pnlInferior.getWidth()-20, pnlInferior.getHeight()-10);
        //tabla.setSize(this.getWidth()-20, pnlInferior.getHeight()-10);
        //tabla.setViewportView(tblTabla);
        //tabla.setSize(new Dimension(200,200));
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
            for (java.util.List<String> list : lsParada)
                datos.add(list);

        if(tipos[4])
            for (java.util.List<String> list : lsParada)
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
            switch (l.get(1)){
                case "Parada":
                    lsParada.add(l);
                break;
                case "Estacion":
                    lsEstacion.add(l);
                break;
                default:
            }
        }
    }

    /**
	 * Metodo que evalua la cadena.
	 * @param c la columna a evaluar
	 * @param min valor minimo que retorna
	 * @return largo de la columna
	 */
	private int autoAjustado(int c)
	{
		int largo = tblTabla.getColumnName(c).length();;
		for(int i = 0; i < tblTabla.getRowCount(); i++)
		{
            String caden  = (String) tblTabla.getValueAt(i, c);
			try { largo = (caden.length() > largo) ? caden.length() : largo; } catch (Exception e) {}
		}
		largo = largo * 8;
		return largo;
	}

    private void Actualizar(){
        //tabla.removeAll();
        tblTabla.setModel(new ModeloTabla(mostrar(),columnas));
		tblTabla.setAutoResizeMode(0);
        int dimension = 0;
        for(int i = 0; i < tblTabla.getColumnCount(); i++)
            dimension += autoAjustado(i);
        if (dimension > (this.getWidth()-20))
            for(int i = 0; i < tblTabla.getColumnCount(); i++)
                tblTabla.getColumnModel().getColumn(i).setMinWidth(autoAjustado(i));
        else
            for(int i = 0; i < tblTabla.getColumnCount(); i++)
                tblTabla.getColumnModel().getColumn(i).setMinWidth((this.getWidth()-20)/tblTabla.getColumnCount());
        //tabla.setViewportView(tblTabla);
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