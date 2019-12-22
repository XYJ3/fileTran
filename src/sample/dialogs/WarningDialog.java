package sample.dialogs;

import javafx.scene.control.Alert;

public class WarningDialog {
    private String warnStr;

    public WarningDialog(String warnStr) {
        this.warnStr = warnStr;
        Alert alert=new Alert(Alert.AlertType.WARNING);
        alert.setTitle("警告");
        alert.setContentText(warnStr);
        alert.showAndWait();
    }
}
