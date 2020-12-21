package nl.thedutchmc.alertbot_watch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

public class Config {
	
	public void load() throws URISyntaxException, IOException {
		//Get the path of where the config file should be
		final File jarPath = new File(Config.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		final File folderPath = new File(jarPath.getParentFile().getPath());
		final File configFile = new File(folderPath, "alertbot-watch.json");
		
		//Check if the config file exists.
		//If this isn't the case, create it and write the default values to it
		if(!configFile.exists()) {
			configFile.createNewFile();
			
			FileWriter fw = new FileWriter(configFile, true);
			
			fw.write("{\r\n"
					+ "    \"name\": \"This would be the name, e.g CDN-1 or Proxy-1\",\r\n"
					+ "    \"port\": 5555\r\n"
					+ "}");

			fw.close();
		}
		
		//Parse the file into a JSON Object
		String content = new String(Files.readAllBytes(Paths.get(configFile.getAbsolutePath())));
		JSONObject configFileJson = new JSONObject(content);
		
		App.name = configFileJson.getString("name");
		App.port = configFileJson.getInt("port");
	}
}
