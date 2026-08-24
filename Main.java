import simulation.*;
import ui.SimulationPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        //Initialize Config & Environment
        WorldConfig world = new WorldConfig(800, 600, 50, true);
        BoidConfig boids = new BoidConfig();
        Environment env = new Environment(world, boids);

        //Initialize the Frame.
        JFrame frame = new JFrame("OOP Boids Flocking Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //Initialize the Simulation Table
        SimulationPanel simPanel = new SimulationPanel(env);
        
        //Initialize the Control Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 4, 20, 15)); // 1 hàng, 4 cột
        // controlPanel.setPreferredSize(new Dimension(0, 100)); 
        
        // TẠO LỀ (PADDING): Cách viền trên 10px, viền dưới 10px, trái phải 20px cho thoáng
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Create sliders to adjust the weights
        JSlider sepSlider = createSlider("Separation", 0, 100, 0, boids);
        JSlider aliSlider = createSlider("Alignment", 0, 100, 0, boids);
        JSlider cohSlider = createSlider("Cohesion", 0, 100, 0, boids);

        // "Add Boid" button
        JButton addBtn = new JButton("Add Boid");
        addBtn.addActionListener(e -> {
            boids.addBoid();
            env.getEntities().add(new entity.Boid(Math.random() * world.getWidth(), Math.random() * world.getHeight()));
        });

        // Add to ControlPanel
        controlPanel.add(sepSlider);
        controlPanel.add(aliSlider);
        controlPanel.add(cohSlider);
        controlPanel.add(addBtn);

        // 5. Add to Frame
        frame.add(simPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null); // Hiển thị ở giữa màn hình
        frame.setVisible(true);
    }

    // Hàm phụ trợ để tạo thanh trượt Slider nhanh gọn hơn
    private static JSlider createSlider(String name, int min, int max, int init, BoidConfig config) {
        JSlider slider = new JSlider(min, max, init);
        slider.setBorder(BorderFactory.createTitledBorder(name + " Weight: " + (init / 10.0)));
        
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double val = slider.getValue() / 10.0;
                slider.setBorder(BorderFactory.createTitledBorder(name + " Weight: " + val));
                
                // Cập nhật thẳng vào BoidConfig dựa theo tên
                if (name.equals("Separation")) config.setSepWeight(val);
                else if (name.equals("Alignment")) config.setAliWeight(val);
                else if (name.equals("Cohesion")) config.setCohWeight(val);
            }
        });
        return slider;
    }
}