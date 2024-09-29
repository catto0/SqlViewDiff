package main;

import static java.lang.System.Logger.Level.DEBUG;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import logger.SimpleLogger;
import printer.Printer;
import tokenizer.Token;
import tokenizer.Tokenizer;

public class App
{
	private static final SimpleLogger LOG = new SimpleLogger(App.class, DEBUG);

	public static void main(String[] args)
	{
		File file = new File("./resources/view1.txt");
//		File file2 = new File("./resources/view2.txt");

		StringBuilder querry = new StringBuilder();
//		StringBuilder querry2 = new StringBuilder();

		LOG.debug("Reading a file %s.", file);
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
			LOG.error(e, "IO Exception has occured while reading a file %s.", file);
		}

//		try (BufferedReader reader = new BufferedReader(new FileReader(file2)))
//		{
//			String line;
//			while ((line = reader.readLine()) != null)
//			{
//				querry2.append(line).append(" ");
//			}
//		}
//		catch (IOException e)
//		{
//			LOG.error(e, "IO Exception has occured while reading a file %s.", file);
//		}

		LOG.debug(querry.toString());
//		LOG.debug(querry2.toString());

		Tokenizer tokenizer = new Tokenizer();
		String formattedView = tokenizer.unifyFormat(querry.toString());
//		String formattedView2 = tokenizer.unifyFormat(querry2.toString());
		LOG.debug(formattedView);
//		LOG.debug(formattedView2);

		List<Token> tokens = tokenizer.tokenize(formattedView);
//		List<Token> tokens2 = tokenizer.tokenize(formattedView2);
		LOG.debug(tokens.toString());
//		LOG.debug(tokens2.toString());

		Printer printer = new Printer();
		printer.printToConsole(tokens);
//		printer.printToConsole(tokens2);
	}
}
