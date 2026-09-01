package ui;

import entity.Shark;
import simulation.SharkConfig;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Arc2D;

public class SharkRenderer {
    public void render(Graphics2D g, Shark shark, SharkConfig config) {
        double x = shark.getPosition().getX();
        double y = shark.getPosition().getY();
        double vx = shark.getVelocity().getX();
        double vy = shark.getVelocity().getY();
        double theta = Math.atan2(vy, vx);
        int R = (int)config.size();

        double visionRadius = config.detectRadius();
        double viewAngleDeg = Math.toDegrees(config.viewAngle());

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(x, y);
        g2d.rotate(theta);

        Arc2D.Double visionArc = new Arc2D.Double(
                -visionRadius, -visionRadius, visionRadius * 2, visionRadius * 2,
                -viewAngleDeg / 2, viewAngleDeg, Arc2D.PIE);

        g2d.setColor(shark.isPreyDetected() ? new Color(255, 60, 60, 90) : new Color(255, 255, 255, 30));
        g2d.fill(visionArc);

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
