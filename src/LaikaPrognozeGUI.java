import javax.swing.*;

public class LaikaPrognozeGUI extends JFrame{
    public LaikaPrognozeGUI(){
        //Nosaukums
        super("Laika Prognoze");

        //GUI parametri
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        pievienotGUIKomponentes();
    }

    private void pievienotGUIKomponentes(){
        JLabel pilsetasLabel = new JLabel("Pilseta: ");
        pilsetasLabel.setBounds(10, 10, 150, 25);
        add(pilsetasLabel);

        JLabel tempLabel = new JLabel("Temp: ");
        tempLabel.setBounds(10, 40, 150, 25);
        add(tempLabel);

        JLabel mitrumaLabel = new JLabel("Mitrums: ");
        mitrumaLabel.setBounds(10, 70, 150, 25);
        add(mitrumaLabel);
    }
}
