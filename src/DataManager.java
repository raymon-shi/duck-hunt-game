import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataManager {
	public static void saveAction(Serializable data, String file) throws Exception {
		try (ObjectOutputStream output = 
				new ObjectOutputStream(Files.newOutputStream(Paths.get(file)))) {
				output.writeObject(data);
			}
		}

	public static Object loadAction(String file) throws Exception {
		try (ObjectInputStream input = 
				new ObjectInputStream(Files.newInputStream(Paths.get(file)))) {
				return input.readObject();
		}
	}
}
