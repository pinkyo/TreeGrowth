package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.*;

public class TreeGenerator {
    private Random random = new Random();

    public TreeGenerator() {
    }

    public List<Line> generate(int maxGeneration) {
        Line initialLine = new Line(600, 1000, 600, 400);

        Queue<LineGeneration> queue = new LinkedList<>();
        queue.add(new LineGeneration(initialLine, 0));

        List<Line> lineList = new ArrayList<>();
        LineGeneration lineGeneration;
        while (Objects.nonNull(lineGeneration = queue.poll())) {
            Line line = lineGeneration.getLine();
            line.setStrokeWidth(5 * (maxGeneration - lineGeneration.getGeneration() + 1) / maxGeneration);
            line.setStroke(Color.GREEN);
            lineList.add(line);

            forkNewBranch(queue, lineGeneration, maxGeneration);
        }

        return lineList;
    }

    private void forkNewBranch(
            Queue<LineGeneration> queue,
            LineGeneration lineGeneration,
            int maxGeneration) {
        if (lineGeneration.getGeneration() >= maxGeneration) return;


        int numOfBranch = random.nextInt(3) + 3; // at most 4 branches.

        Line line = lineGeneration.getLine();

        double baseAngle;
        if (line.getStartX() == line.getEndX()) {
            baseAngle = Math.PI / 2;
        } else {
            baseAngle = Math.atan((line.getStartY() - line.getEndY()) / (line.getStartX() - line.getEndX()));
        }

        for (int i = 0; i < numOfBranch; i++) {
            double newLength = calLength(line) * (random.nextDouble() / 1.5 + 0.2);
            if (newLength <= 1) continue;

            Point firstPoint = pickFirstPoint(line);
            double angel = baseAngle + Math.PI / 1.5 * random.nextDouble() - Math.PI / 36;
            if (Math.abs(angel - Math.PI / 2) < Math.PI / 60) {
                continue;
            }

            Point secondPoint = calSecondPoint(firstPoint, angel, newLength);

            queue.add(new LineGeneration(new Line(firstPoint.X, firstPoint.Y, secondPoint.X, secondPoint.Y),
                    lineGeneration.getGeneration() + 1));
        }
    }

    private static double calLength(Line line) {
        return Math.sqrt(
                Math.pow(line.getEndX() - line.getStartX(), 2) +
                        Math.pow(line.getEndY() - line.getStartY(), 2));
    }

    static class Point {
        private double X;
        private double Y;

        public Point(double x, double y) {
            X = x;
            Y = y;
        }

        public double getX() {
            return X;
        }

        public void setX(double x) {
            X = x;
        }

        public double getY() {
            return Y;
        }

        public void setY(double y) {
            Y = y;
        }
    }

    private Point pickFirstPoint(Line line) {
        double Y = line.getStartY() - (line.getStartY() - line.getEndY()) * (random.nextDouble() / 2 + 0.2);
        double X = (line.getStartX() - line.getEndX()) / (line.getStartY() - line.getEndY())
                * (Y - line.getStartY()) + line.getStartX();
        return new Point(X, Y);
    }

    private Point calSecondPoint(Point firstPoint, double angle, double length) {
        double Y = firstPoint.Y - length / Math.sqrt(1 + Math.pow(1 / Math.tan(angle), 2));
        double X = (Y - firstPoint.Y) / Math.tan(angle) + firstPoint.X;

        return new Point(X, Y);
    }

    static class LineGeneration {
        private Line line;
        private int generation;

        public LineGeneration(Line line, int generation) {
            this.line = line;
            this.generation = generation;
        }

        public Line getLine() {
            return line;
        }

        public void setLine(Line line) {
            this.line = line;
        }

        public int getGeneration() {
            return generation;
        }

        public void setGeneration(int generation) {
            this.generation = generation;
        }
    }
}