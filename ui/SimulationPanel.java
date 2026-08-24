package ui;

import simulation.Environment;
import entity.Entity;
import entity.Boid;

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

    public SimulationPanel(Environment env) {
        this.environment = env;
        this.boidRenderer = new BoidRenderer();

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

        for (Entity entity : environment.getEntities()) {
            
            // Cấu trúc kiểm tra đa hình: Nếu nó là cá Boid thì dùng BoidRenderer
            if (entity instanceof Boid) {
                boidRenderer.render(g2d, (Boid) entity);
            }
            // Sau này nếu có Đá (Obstacle) hay Cá Mập (Predator), 
            //chỉ cần thêm lệnh else if (entity instanceof Predator) ở đây.
        }
    }
}