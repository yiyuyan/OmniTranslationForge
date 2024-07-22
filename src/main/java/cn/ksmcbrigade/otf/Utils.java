package cn.ksmcbrigade.otf;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Utils {

    public static String read(File file) throws IOException {
        return org.apache.commons.io.FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }

    public static void write(File file, String s) throws IOException {
        org.apache.commons.io.FileUtils.writeStringToFile(file, s, StandardCharsets.UTF_8);
    }


    public static String getKey(String key) {
        try {
            InputStream inputStream = Utils.class.getResourceAsStream("/assets/otf/lang/" + getConfig("language") + ".json");
            String string = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            return new Gson().fromJson(string, JsonObject.class).get(key).getAsString();
        } catch (Exception e) {
            return key;
        }
    }

    public static boolean has(String key) {
        try {
            InputStream inputStream = Utils.class.getResourceAsStream("/assets/otf/lang/" + getConfig("language") + ".json");
            String string = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            new Gson().fromJson(string, JsonObject.class).get(key).getAsString();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getConfig(String key) {
        try {
            InputStream inputStream = Utils.class.getResourceAsStream("/assets/otf/config.json");
            String string = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            return new Gson().fromJson(string, JsonObject.class).get(key).getAsString();
        } catch (Exception e) {
            return key;
        }
    }
}
