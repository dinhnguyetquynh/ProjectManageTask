package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

public class GsonHelper {
	 private static final Gson gson;

	    static {
	        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	        gson = new GsonBuilder()
	                .registerTypeAdapter(LocalDateTime.class, (com.google.gson.JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
	                        new com.google.gson.JsonPrimitive(src.format(formatter))
	                )
	                .registerTypeAdapter(LocalDateTime.class, (com.google.gson.JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
	                        LocalDateTime.parse(json.getAsString(), formatter)
	                )
//	                .setPrettyPrinting() // Optional: in JSON đẹp dễ đọc
	                .create();
	    }

	    private GsonHelper() {
	        // Không cho tạo đối tượng
	    }

	    public static String toJson(Object object) {
	        return gson.toJson(object);
	    }

	    public static <T> T fromJson(String json, Class<T> classOfT) {
	        try {
	            return gson.fromJson(json, classOfT);
	        } catch (JsonSyntaxException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    public static <T> T fromJson(String json, Type typeOfT) {
	        try {
	            return gson.fromJson(json, typeOfT);
	        } catch (JsonSyntaxException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
}
