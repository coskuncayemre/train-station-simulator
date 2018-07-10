import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class readFile {
	private Scanner userScanner;

	public ArrayList<String> read_file(String path) throws FileNotFoundException {

		userScanner = new Scanner(new File(path));
		ArrayList<String> lines = new ArrayList<>();

		while (userScanner.hasNextLine()) {
			String line = userScanner.nextLine();
			lines.add(line);
		}
		return lines;
	}

}
