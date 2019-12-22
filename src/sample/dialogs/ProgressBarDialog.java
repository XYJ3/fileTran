package sample.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;

import java.util.Optional;

public class ProgressBarDialog {
private int max;
private ProgressBar progressBar;
    public ProgressBarDialog(int max) {
        this.max = max;
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("接收");
        alert.setContentText("是否接收");
        progressBar.setVisible(true);

    }


}
