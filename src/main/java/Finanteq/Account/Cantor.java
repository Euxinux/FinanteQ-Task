package Finanteq.Account;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class Cantor {

    public double getCourse (String addressURL) throws JSONException {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            URL u = new URL(addressURL);
            HttpURLConnection url = (HttpURLConnection) u.openConnection();
            String line;
            if (url.getResponseCode() == 200) {
                InputStream inputStream = url.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                inputStream.close();
            }
        } catch (Exception e) {
            System.out.println("error");
        }
        JSONObject jsonObject = new JSONObject(String.valueOf(stringBuffer));
        JSONArray jsonArray = jsonObject.getJSONArray("rates");
        Double currencyRate = jsonArray.getJSONObject(0).getDouble("mid");
        return currencyRate;
    }
}
