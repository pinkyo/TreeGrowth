package my.yinkn.tree;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.List;

public class Controller {
    /**
     * CIRCLE_STROKE_WIDTH_RATIO = CIRCLE_STROKE_WIDTH / LINE_STROKE_WIDTH
     * LINE_STROKE_WIDTH: all branch line width;
     * CIRCLE_STROKE_WIDTH: endpoint circle width of branches
     */
    private static final double CIRCLE_STROKE_WIDTH_RATIO = 1.5;
    private static int MIN_GENERATION = 1;
    private static int MAX_GENERATION = 7;

    @FXML
    private TextField textField;

    @FXML
    private Group group;

    private TreeGenerator generator = new TreeGenerator();

    @FXML
    public void startGrown() {
        String text = textField.getText();
        try {
            Integer generation = Integer.parseInt(text);
            if (generation < MIN_GENERATION || generation > MAX_GENERATION) {
                System.out.println(String.format("Generation [%d] is invalid.", generation));
                return;
            }

            group.getChildren().clear();
            textField.setDisable(true);

            List<Line> lineList = generator.generate(generation);

            group.getChildren().addAll(lineList);
            lineList.forEach(line -> {
                Circle circle = new Circle(line.getEndX(), line.getEndY(), line.getStrokeWidth());
                circle.setStroke(Color.RED);
                circle.setStrokeWidth(line.getStrokeWidth() * CIRCLE_STROKE_WIDTH_RATIO);
                group.getChildren().add(circle);
            });
            textField.setDisable(false);

        } catch (NumberFormatException e) {
            textField.setText("");
        }
    }
}
