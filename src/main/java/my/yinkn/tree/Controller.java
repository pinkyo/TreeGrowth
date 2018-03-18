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
    @FXML
    private TextField textField;

    @FXML
    private Group group;

    private TreeGenerator generator = new TreeGenerator();

    @FXML
    public void startGrown(MouseEvent mouseEvent) {
        String text = textField.getText();
        try {
            Integer generation = Integer.parseInt(text);
            if (generation < 0 || generation > 8) {
                System.out.println(String.format("generation [%d] is invalid.", generation));
                return;
            }

            group.getChildren().clear();
            textField.setDisable(true);

            List<Line> lineList = generator.generate(generation);

            group.getChildren().addAll(lineList);
            lineList.forEach(line -> {
                Circle circle = new Circle(line.getEndX(), line.getEndY(), line.getStrokeWidth());
                circle.setStroke(Color.RED);
                circle.setStrokeWidth(line.getStrokeWidth() * 1.5);
                group.getChildren().add(circle);
            });
            textField.setDisable(false);

        } catch (NumberFormatException e) {
            textField.setText("");
        }
    }
}
