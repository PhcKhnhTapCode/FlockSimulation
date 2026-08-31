import simulation.*;
import ui.SimulationPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class Main {

    // Dark theme palette, matches the black simulation canvas
    private static final Color BG_DARK = new Color(18, 18, 20);
    private static final Color BG_PANEL = new Color(28, 28, 32);
    private static final Color ACCENT = new Color(66, 165, 245);
    private static final Color TEXT_LIGHT = new Color(225, 225, 230);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 13);

    public static void main(String[] args) {
        installLookAndFeel();

        //Initialize Config & Environment
        WorldConfig world = new WorldConfig(800, 600, 50, true);
        BoidConfig boids = new BoidConfig();
        Environment env = new Environment(world, boids);

        //Initialize the Frame.
        JFrame frame = new JFrame("OOP Boids Flocking Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(BG_DARK);

        //Initialize the Simulation Table
        SimulationPanel simPanel = new SimulationPanel(env);

        //Initialize the Control Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 4, 24, 0));
        controlPanel.setBackground(BG_PANEL);
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(50, 50, 55)),
                BorderFactory.createEmptyBorder(14, 24, 14, 24)
        ));

        // Create sliders to adjust the weights
        JSlider sepSlider = createSlider("Separation", 0, 100, 0, boids);
        JSlider aliSlider = createSlider("Alignment", 0, 100, 0, boids);
        JSlider cohSlider = createSlider("Cohesion", 0, 100, 0, boids);

        // "Add Boid" button
        JButton addBtn = createAccentButton("+ Add Boid");
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

    // Ép Nimbus theo bảng màu tối để đồng bộ với canvas đen của SimulationPanel
    private static void installLookAndFeel() {
        try {
            UIManager.put("control", BG_PANEL);
            UIManager.put("info", BG_PANEL);
            UIManager.put("nimbusBase", new Color(35, 35, 40));
            UIManager.put("nimbusBlueGrey", new Color(60, 60, 65));
            UIManager.put("nimbusFocus", ACCENT);
            UIManager.put("nimbusLightBackground", BG_DARK);
            UIManager.put("nimbusSelectionBackground", ACCENT);
            UIManager.put("text", TEXT_LIGHT);
            UIManager.put("textForeground", TEXT_LIGHT);
            UIManager.put("nimbusSelectedText", Color.WHITE);
            UIManager.put("controlText", TEXT_LIGHT);

            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
        } catch (Exception ignored) {
            // Không tìm thấy Nimbus thì dùng L&F mặc định của hệ thống
        }
    }

    private static JButton createAccentButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(LABEL_FONT);
        btn.setForeground(Color.WHITE);
        btn.setBackground(ACCENT);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMargin(new Insets(8, 16, 8, 16));
        return btn;
    }

    // Hàm phụ trợ để tạo thanh trượt Slider nhanh gọn hơn
    private static JSlider createSlider(String name, int min, int max, int init, BoidConfig config) {
        JSlider slider = new JSlider(min, max, init);
        slider.setOpaque(false);
        slider.setUI(new FlatSliderUI(slider));
        slider.setBorder(titledSliderBorder(name, init / 10.0));

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double val = slider.getValue() / 10.0;
                slider.setBorder(titledSliderBorder(name, val));

                // Cập nhật thẳng vào BoidConfig dựa theo tên
                if (name.equals("Separation")) config.setSepWeight(val);
                else if (name.equals("Alignment")) config.setAliWeight(val);
                else if (name.equals("Cohesion")) config.setCohWeight(val);
            }
        });
        return slider;
    }

    private static javax.swing.border.TitledBorder titledSliderBorder(String name, double value) {
        javax.swing.border.TitledBorder border = BorderFactory.createTitledBorder(String.format("%s: %.1f", name, value));
        border.setTitleColor(TEXT_LIGHT);
        border.setTitleFont(LABEL_FONT);
        return border;
    }

    private static class FlatSliderUI extends BasicSliderUI {
        FlatSliderUI(JSlider slider) { super(slider); }

        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int trackY = trackRect.y + trackRect.height / 2 - 2;
            g2.setColor(new Color(60, 60, 65));
            g2.fillRoundRect(trackRect.x, trackY, trackRect.width, 4, 4, 4);

            int fillWidth = thumbRect.x + thumbRect.width / 2 - trackRect.x;
            g2.setColor(ACCENT);
            g2.fillRoundRect(trackRect.x, trackY, Math.max(fillWidth, 0), 4, 4, 4);
            g2.dispose();
        }

        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(ACCENT);
            g2.fillOval(thumbRect.x, thumbRect.y + thumbRect.height / 2 - 7, 14, 14);
            g2.setColor(Color.WHITE);
            g2.drawOval(thumbRect.x, thumbRect.y + thumbRect.height / 2 - 7, 14, 14);
            g2.dispose();
        }

        @Override
        public void paintFocus(Graphics g) {
        }
    }
}
