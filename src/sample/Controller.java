package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import jssc.SerialPort;
import jssc.SerialPortException;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    static SerialPort serialPort = new SerialPort("COM9");
    public TextField number;

    private int value;

    public void reaingSerial() throws SerialPortException {
        value =  0;
        setValue(value);
        try {
            serialPort.openPort();
            serialPort.setParams(
                    SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE
            );
            try {
                serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
                serialPort.writeBytes(("p\r".getBytes()));
            }
            catch (Exception error) {
                error.getCause().getClass().getName();

            }
            if (serialPort.isOpened()) {

                try {
                    byte[] buffer = serialPort.readBytes(10);

                    String s = new String(buffer);
                    String s2 = s.substring(1, 9).trim();
                    if (isValid(s2)) {
                        value = Integer.valueOf(s2);
                        if (value >= 10) {

                            setValue(value);
                        }
                        else {
                            System.out.println("Wait");
                        }
                    }
                    serialPort.removeEventListener();
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception err) {
            err.getCause();

        }
    }

    public void dicConnectSerialPOrt() {
        try {
            Thread.sleep(100);

            serialPort.closePort();
        } catch (SerialPortException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public Integer getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public  boolean isValid(String value) {
        try {
            Integer.parseInt(value);
            return true;
        }
        catch (NumberFormatException formatError){
            System.out.println("invalid Input from Indicator");
            System.out.println("Error date : " + LocalDateTime.now());
            return false;

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void read(ActionEvent event) {
        number.setText(String.valueOf(getValue()));
    }
}