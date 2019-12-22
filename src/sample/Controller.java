package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.dialogs.WarningDialog;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public ListView ipListView;
    public TextField filePath;
    public Tab toTab;
    public TextArea textareaSendLog;
    public ProgressBar progressbar;

    private File sendF;
    private ServerAccept serverAccept;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            new ScanPanel();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        progressbar.setProgress(0);
        MyProgress.progressBar=progressbar;
//        textareaSendLog.appendText("发送  "+"123"+"d.txt");
        toTab.setText("未选择");
        filePath.setEditable(false);
        IpList.ipList.add("111");
        IpList.ipList.add("222");
        ipListView.setItems(FXCollections.observableList(IpList.ipList));
        class NoticeListItemChangeListener implements ChangeListener<Object> {
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                Object value = observable.getValue();
                toTab.setText(value.toString());
                System.out.println(newValue);
            }
        }
        ipListView.getSelectionModel().selectedItemProperty().addListener(new NoticeListItemChangeListener());

        try {
            serverAccept = new ServerAccept(new ServerSocket(6666));
            new Thread(serverAccept).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFile(javafx.event.ActionEvent actionEvent) {

        String s = filePath.getText();
        if ("".equals(s)) {
            new WarningDialog("没有选择文件");
        } else {
            System.out.println(s);
            InetSocketAddress address = new InetSocketAddress(toTab.getText(), 6666);
            System.out.println(address.getAddress().getHostAddress());
            new Thread(new SendFile(sendF, address)).start();
            textareaSendLog.appendText("我 -->"+toTab.getText()+"  d.txt");
        }
    }

    public void selectFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
//        fileChooser.showOpenDialog(new Stage());
        sendF = fileChooser.showOpenDialog(new Stage());
        if (sendF == null) {
            return;
        }
        filePath.setText(sendF.getAbsolutePath());
    }

}
