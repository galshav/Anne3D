package anne3D.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

final public class File extends java.io.File{

	private static final long serialVersionUID = 1L;

	public File(final String filePath) throws FileNotFoundException {
		super(filePath);
		if (false == this.exists() ||
			false == this.isFile()) {
			throw new FileNotFoundException(String.format("Can not find file: [%s]", filePath));
		}
	}
	
	public List<String> read() throws IOException
	{
		return (ArrayList<String>) Files.readAllLines(this.toPath(), StandardCharsets.UTF_8);
	}
}
