package utils.printer;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.System.Logger.Level;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import tokenizer.Token;
import utils.logger.SimpleLogger;

public abstract class SimplePrinterBase implements ISimplePrinter
{
	private static final SimpleLogger LOG = new SimpleLogger(SimplePrinterBase.class, Level.DEBUG);

	private static final String DEFAULT_OUTPUT_FOLDER = ".\\outputs\\";

	@Override
	public void printToConsole(List<Token> tokens, int averageWordLength)
	{
		String formattedString = createFormattedOutput(tokens, averageWordLength);
		LOG.info("%s%s", System.lineSeparator(), formattedString);
	}

	@Override
	public void writeToFile(String fileName, List<Token> tokens, int averageWordLength)
	{
		Path path = Paths.get("%s%s.txt".formatted(DEFAULT_OUTPUT_FOLDER, fileName));
		try
		{
			Files.createDirectories(path);
		}
		catch (IOException e)
		{
			LOG.error(e, "Couldn't create directories for path %s, aborting writing to file.", path.toString());
			return;
		}

		String formattedString = createFormattedOutput(tokens, averageWordLength);
		try (FileWriter writer = new FileWriter(path.toFile()))
		{
			writer.write(formattedString);
		}
		catch (IOException e)
		{
			LOG.error(e, "Couldn't write formatted string into a file %s.txt", fileName);
		}
	}

	abstract protected String createFormattedOutput(List<Token> tokens, int averageWordLength);
}
