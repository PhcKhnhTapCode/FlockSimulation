package ui;

import simulation.Environment;
import entity.Boid;
import entity.Shark;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationPanel extends JPanel implements ActionListener {
    
    private Environment environment;
    private Timer timer;
    private BoidRenderer boidRenderer;
    private SharkRenderer sharkRenderer;

    public SimulationPanel(Environment env) {
        this.environment = env;
        this.boidRenderer = new BoidRenderer();
        this.sharkRenderer = new SharkRenderer();

        int width = env.getWorldConfig().getWidth();
        int height = env.getWorldConfig().getHeight();
        
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK); 

        // Khởi tạo Game Loop: Cứ 16ms (~60 FPS) thì gọi hàm actionPerformed một lần
        this.timer = new Timer(16, this);
        this.timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Kích hoạt logic vật lý và bầy đàn 
        environment.update(); 
        
        //Yêu cầu vẽ lại toàn bộ màn hình 
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        Graphics2D g2d = (Graphics2D) g;

        for (Boid boid : environment.getBoids()) {
            boidRenderer.render(g2d, boid);
        }
        for (Shark shark : environment.getSharks()) {
            sharkRenderer.render(g2d, shark, environment.getSharkConfig());
        }
    }
}
