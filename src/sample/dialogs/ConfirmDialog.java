package sample.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

//确认是否接收文件弹窗
public class ConfirmDialog {

    private String fromIP;

    public ConfirmDialog(String fromIP) {
        this.fromIP = fromIP;
    }
    public int show(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("接收");
        alert.setHeaderText(fromIP+"发来文件");
        alert.setContentText("是否接收");
        Optional<ButtonType> result=alert.showAndWait();
        if (result.get()==ButtonType.OK){
            return 1;
        }else{
            return 0;
        }
    }
}
