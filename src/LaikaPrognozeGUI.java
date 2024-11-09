import java.awt.*;
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
        ImageIcon ikona = new ImageIcon("assets/icon.png");
        setIconImage(ikona.getImage());
    }

    private void pievienotGUIKomponentes(){
        LaikaPrognoze laikaPrognoze = new LaikaPrognoze();
        laikaPrognoze.iegutDatus();

        //LABELS
        JLabel pilsetasLabel = new JLabel(laikaPrognoze.pilseta);
        pilsetasLabel.setBounds(100, 60, 200, 40);
        pilsetasLabel.setFont(new Font("Arial", Font.BOLD, 30));
        pilsetasLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        pilsetasLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        add(pilsetasLabel);

        JLabel tempLabel = new JLabel(laikaPrognoze.temp + " C");
        tempLabel.setBounds(150, 340, 100, 25);
        tempLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tempLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        tempLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        add(tempLabel);

        //HUMIDITY
        JLabel humLabel = new JLabel("Humidiy");
        humLabel.setBounds(30, 390, 200, 20);
        humLabel.setFont(new Font("Arial", Font.BOLD, 15));
        humLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        humLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        add(humLabel);

        JLabel mitrumaLabel = new JLabel(laikaPrognoze.mitrums + "%");
        mitrumaLabel.setBounds(60, 420, 200, 20);
        mitrumaLabel.setFont(new Font("Arial", Font.BOLD, 15));
        mitrumaLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        mitrumaLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        add(mitrumaLabel);

        JLabel humBilde = new JLabel(izmeraMainitajs("assets/humidity.png", 30, 30));
        humBilde.setBounds(25, 410, 30, 30);
        add(humBilde);

        //WIND SPEED
        JLabel vejaLabel = new JLabel("Wind speed");
        vejaLabel.setBounds(200, 390, 200, 20);
        vejaLabel.setFont(new Font("Arial", Font.BOLD, 15));
        vejaLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        vejaLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        add(vejaLabel);

        JLabel vejaAtrumsLabel = new JLabel(laikaPrognoze.vejaAtrums + "km/h");
        vejaAtrumsLabel.setBounds(240, 420, 200, 20);
        vejaAtrumsLabel.setFont(new Font("Arial", Font.BOLD, 15));
        vejaAtrumsLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        vejaAtrumsLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        add(vejaAtrumsLabel);

        JLabel vejaBilde = new JLabel(izmeraMainitajs("assets/windspeed.png", 30, 30));
        vejaBilde.setBounds(200, 410, 30, 30);
        add(vejaBilde);

        //KLUDAS
        JLabel kluda = new JLabel();
        kluda.setBounds(10, 370, 150, 25);
        kluda.setForeground(Color.RED);
        add(kluda);
        
        //TEXT FIELD
        JTextField pilsetaField = new JTextField();
        pilsetaField.setBounds(15, 15, 320, 25);
        add(pilsetaField);

        //BILDE
        JLabel bilde = new JLabel(izveletiesBildi(laikaPrognoze));
        bilde.setBounds(80, 120, 200, 200);
        add(bilde);

        //POGA
        JButton mekletButton = new JButton(izmeraMainitajs("assets/search.png", 25, 25));
        mekletButton.setBounds(340, 15, 25, 25);
        mekletButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(mekletButton);

        mekletButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(pilsetaField.getText().isEmpty())
                    laikaPrognoze.iegutDatus();
                else
                    laikaPrognoze.iegutDatus(pilsetaField.getText());

                pilsetaField.setText(null);

                pilsetasLabel.setText(laikaPrognoze.pilseta);
                tempLabel.setText(laikaPrognoze.temp + " C");
                mitrumaLabel.setText(laikaPrognoze.mitrums + "%");
                vejaAtrumsLabel.setText(laikaPrognoze.vejaAtrums + "km/h");
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
