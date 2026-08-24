package ui;

import entity.Boid;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class BoidRenderer {
    
    // Boid's size
    private static final int R = 5; 

    // Hàm render chỉ nhận đầu vào là bút vẽ (Graphics2D) và dữ liệu (Boid)
    public void render(Graphics2D g, Boid boid) {
        // 1. Lấy dữ liệu tọa độ và vận tốc thông qua Getters
        double x = boid.getPosition().getX();
        double y = boid.getPosition().getY();
        double vx = boid.getVelocity().getX();
        double vy = boid.getVelocity().getY();

        // 2. Tính toán góc xoay (Radian) dựa trên hướng vector vận tốc
        double theta = Math.atan2(vy, vx);

        // 3. Tạo bản sao của đối tượng Graphics để không làm lệch hệ trục của toàn màn hình
        Graphics2D g2d = (Graphics2D) g.create();

        // 4. Dời gốc tọa độ (0,0) về vị trí của con cá và tiến hành xoay
        g2d.translate(x, y);
        g2d.rotate(theta);

        // 5. Định nghĩa hình dáng đa giác (Tam giác mỏ nhọn)
        int[] xpoints = {R * 2, -R, -R};
        int[] ypoints = {0, -R, R};
        Polygon triangle = new Polygon(xpoints, ypoints, 3);

        // 6. Đổ màu và vẽ viền
        g2d.setColor(Color.BLUE);
        g2d.fillPolygon(triangle);
        
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawPolygon(triangle);

        // 7. Giải phóng bộ nhớ của bản sao Graphics2D
        g2d.dispose();
    }
}