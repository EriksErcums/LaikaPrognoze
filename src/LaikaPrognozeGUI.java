import java.awt.Color;
import java.awt.Image;
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

        //LABELS
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

        //KLUDAS
        JLabel kluda = new JLabel();
        kluda.setBounds(10, 370, 150, 25);
        kluda.setForeground(Color.RED);
        add(kluda);
        
        //TEXT FIELD
        JTextField pilsetaField = new JTextField();
        pilsetaField.setBounds(10, 130, 200, 25);
        add(pilsetaField);

        //BILDE
        JLabel bilde = new JLabel(izveletiesBildi(laikaPrognoze));
        bilde.setBounds(10, 160, 200, 200);
        add(bilde);
        if(bilde.getIcon().getIconWidth() == -1)
            System.out.println("Neizdevas");

        //POGA
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
                bilde.setIcon(izveletiesBildi(laikaPrognoze));
                kluda.setText(laikaPrognoze.kluda);
            }
        });
    }

    //Nomaina scale bildei
    private ImageIcon izmeraMainitajs(String path, int x, int y){
        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage();
        image = image.getScaledInstance(x, y, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    private ImageIcon izveletiesBildi(LaikaPrognoze laikaPrognoze){
        //Default bilde
        ImageIcon ikons = new ImageIcon();
        ikons = izmeraMainitajs("assets/clear.png", 200, 200);

        if(laikaPrognoze.sniegs)
            ikons = izmeraMainitajs("assets/snow.png", 200, 200);
        else if(laikaPrognoze.lietus)
            ikons = izmeraMainitajs("assets/rain.png", 200, 200);
        else if(laikaPrognoze.makonains)
            ikons = izmeraMainitajs("assets/cloudy.png", 200, 200);
        else
            ikons = izmeraMainitajs("assets/clear.png", 200, 200);

        return ikons;
    }
}
