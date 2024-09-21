package main;

import static java.lang.System.Logger.Level.DEBUG;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import logger.ISimpleLogger;
import logger.SimpleLogger;
import tokenizer.Tokenizer;

public class App
{
	private static final ISimpleLogger LOG = new SimpleLogger(App.class, DEBUG);

	public static void main(String[] args)
	{
		File file = new File("./resources/view1.txt");
		StringBuilder querry = new StringBuilder();

		LOG.debug("Reading file %s.", file);
		try (BufferedReader reader = new BufferedReader(new FileReader(file)))
		{
			String line;
			while ((line = reader.readLine()) != null)
			{
				querry.append(line).append(" ");
			}
		}
		catch (IOException e)
		{
			LOG.error(e, "IO Exception has occured when reading file %s.", file);
		}

		LOG.debug(querry.toString());

		Tokenizer tokenizer = new Tokenizer();
		String formattedView = tokenizer.unifyFormat(querry.toString());

		LOG.debug(formattedView);
	}
}
