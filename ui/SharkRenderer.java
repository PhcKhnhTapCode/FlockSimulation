
package ui;

import entity.Shark;
import simulation.SharkConfig;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class SharkRenderer {
    public void render(Graphics2D g, Shark shark, SharkConfig config) {
        double x = shark.getPosition().getX();
        double y = shark.getPosition().getY();
        double vx = shark.getVelocity().getX();
        double vy = shark.getVelocity().getY();
        double theta = Math.atan2(vy, vx);
        int R = (int)config.size();

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(x, y);
        g2d.rotate(theta);

        int[] xpoints = {R * 2, -R, -R};
        int[] ypoints = {0, -R, R};
        Polygon triangle = new Polygon(xpoints, ypoints, 3);

        g2d.setColor(Color.RED);
        g2d.fillPolygon(triangle);
        g2d.setColor(Color.ORANGE);
        g2d.drawPolygon(triangle);

        g2d.dispose();
    }
}
