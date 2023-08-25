package hilos;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.sound.sampled.*;

public class Hilos{

    public static void main(String[] args) {
         JFrame frame = new JFrame("ICONO Y MUSICA UTILIZANDO HILOS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setBounds(350,100,500,500); 
        
        ImgPanel imagenPanel = new ImgPanel("rock.gif");
        frame.add(imagenPanel);

        ReproductorMusic reproductorMusica = new ReproductorMusic("rocanrol.wav");
        Thread musicaThread = new Thread(reproductorMusica);

        frame.setVisible(true);
        musicaThread.start();

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                reproductorMusica.detener();
            }
        });
    }
}
    class ImgPanel extends JPanel {
    private Image imagen;

    public ImgPanel(String imagePath) {
        imagen = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
}
class ReproductorMusic implements Runnable {
    private String filePath;
    private volatile boolean detenerReproduccion = false;

    public ReproductorMusic(String filePath) {
        this.filePath = filePath;
    }

    public void detener() {
        detenerReproduccion = true;
    }
    @Override
    public void run() {
        try {
            File file = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

            while (!detenerReproduccion && clip.isActive()) {
                Thread.sleep(100);
               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
