import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        LaikaPrognoze laikaPrognoze = new LaikaPrognoze();
        laikaPrognoze.iegutDatus();

        JLabel pilsetasLabel = new JLabel("City: " + laikaPrognoze.pilseta);
        pilsetasLabel.setBounds(10, 10, 150, 25);
        add(pilsetasLabel);

        JLabel tempLabel = new JLabel("Temp: " + laikaPrognoze.temp);
        tempLabel.setBounds(10, 40, 150, 25);
        add(tempLabel);

        JLabel mitrumaLabel = new JLabel("Moist: " + laikaPrognoze.mitrums);
        mitrumaLabel.setBounds(10, 70, 150, 25);
        add(mitrumaLabel);

        JLabel vejaAtrumsLabel = new JLabel("Wind speed: " + laikaPrognoze.vejaAtrums);
        vejaAtrumsLabel.setBounds(10, 100, 150, 25);
        add(vejaAtrumsLabel);

        JTextField pilsetaField = new JTextField();
        pilsetaField.setBounds(10, 130, 200, 25);
        add(pilsetaField);

        JButton mekletButton = new JButton();
        mekletButton.setBounds(210, 130, 25, 25);
        add(mekletButton);

        mekletButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(pilsetaField.getText().isEmpty())
                    laikaPrognoze.iegutDatus();
                else
                    laikaPrognoze.iegutDatus(pilsetaField.getText());
                    
                pilsetaField.setText(null);

                pilsetasLabel.setText("City: " + laikaPrognoze.pilseta);
                tempLabel.setText("Temp: " + laikaPrognoze.temp);
                mitrumaLabel.setText("Moist: " + laikaPrognoze.mitrums);
                vejaAtrumsLabel.setText("Wind speed: " + laikaPrognoze.vejaAtrums);
            }
        });
    }
}
