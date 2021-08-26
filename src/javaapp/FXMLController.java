package javaapp;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.R;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;
import org.json.JSONObject;

public class FXMLController {

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text temp_feels;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    private Text temp_pres;

    @FXML
    private Text outemp_info;

    @FXML
    private Text outemp_feels;

    @FXML
    private Text outemp_max;

    @FXML
    private Text outemp_min;

    @FXML
    private Text outemp_pres;

    @FXML
    private ImageView image;

    @FXML
    void initialize() {
        getData.setOnAction(event -> {
            String getUserCity = city.getText().trim();
            if (!getUserCity.equals("")) {
                System.out.println(getUserCity);
                String output = getUrlContent("https://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=b581d7a227560ef0039d630f01ebb5bf&units=metric");
                System.out.println(output);

                if (!output.isEmpty()) {
                    JSONObject obj = new JSONObject(output);
                    outemp_info.setText(obj.getJSONObject("main").getDouble("temp") + " °С");
                    outemp_feels.setText(obj.getJSONObject("main").getDouble("feels_like") + " °С");
                    outemp_max.setText(obj.getJSONObject("main").getDouble("temp_max") + " °С");
                    outemp_min.setText(obj.getJSONObject("main").getDouble("temp_min") + " °С");
                    outemp_pres.setText(obj.getJSONObject("main").getDouble("pressure") + " мм рт.ст.");
                    
                    String img = obj.get("weather").toString();
                    img = img.substring(10,13);
                    System.out.println(img);
                    String url = "http://openweathermap.org/img/wn/" + img + "@2x.png";
                    boolean backgroundLoading = true;
                    Image image1 = new Image(url, backgroundLoading);
                    image.setImage(image1);
                }
            }
        });
    }

    private static String getUrlContent(String urlAdress) {
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Invalid city id");
        }
        return content.toString();
    }
}
