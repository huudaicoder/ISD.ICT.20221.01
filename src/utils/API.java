package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class provides APIs and methods for connecting to APIs of banks
 */

public class API {
	public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public static String patch(String url, String message) throws IOException {
		URL api = new URL(url);
		HttpURLConnection conn = (HttpURLConnection)api.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write(message);
		writer.close();
		BufferedReader in;
		String inputLine;
		if (conn.getResponseCode() / 100 == 2) {
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);
		in.close();
		return response.toString();
	}
	
	private static void allowMethods(String... methods) {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
			methodsField.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);

			methodsField.set(null/* static field */, newMethods);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}
	
	private static void setRequestMethodViaJreBugWorkaround(final HttpURLConnection httpURLConnection, final String method) {
		try {
	         Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
	         methodsField.setAccessible(true);
	         // get the methods field modifiers
	         Field modifiersField = Field.class.getDeclaredField("modifiers");
	         // bypass the "private" modifier 
	         modifiersField.setAccessible(true);

	         // remove the "final" modifier
	         modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

	         /* valid HTTP methods */
	         String[] methods = {
	                    "GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE", "PATCH"
	         };
	         // set the new methods - including patch
	         methodsField.set(null, methods);

	     } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
	      e.printStackTrace();
	     }
	}
}