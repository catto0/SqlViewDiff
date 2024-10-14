package main;

import static java.lang.System.Logger.Level.DEBUG;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import tokenizer.SimpleTokenizer;
import tokenizer.Token;
import tokenizer.TokenStatus;
import utils.logger.SimpleLogger;
import utils.printer.SimplePrinter;

public class App
{
	private static final SimpleLogger LOG = new SimpleLogger(App.class, DEBUG);

	/*
	 * TODO: Expand to also allow comparison of other sql statements
	 */

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

		SimpleTokenizer tokenizer = new SimpleTokenizer();
		String formattedView = tokenizer.unifyFormat(querry.toString());
//		String formattedView2 = tokenizer.unifyFormat(querry2.toString());
		LOG.debug(formattedView);
//		LOG.debug(formattedView2);

		List<Token> tokens = tokenizer.tokenize(formattedView);
//		List<Token> tokens2 = tokenizer.tokenize(formattedView2);
		LOG.debug(tokens.toString());
//		LOG.debug(tokens2.toString());

		LOG.debug("Adjusting %s to %s", tokens.get(0), TokenStatus.ADDED);
		tokens.get(0).setStatus(TokenStatus.ADDED);
		LOG.debug("Adjusting %s to %s", tokens.get(1), TokenStatus.ADDED);
		tokens.get(1).setStatus(TokenStatus.ADDED);
		LOG.debug("Adjusting %s to %s", tokens.get(2), TokenStatus.ADDED);
		tokens.get(2).setStatus(TokenStatus.ADDED);
		LOG.debug("Adjusting %s to %s", tokens.get(3), TokenStatus.ADDED);
		tokens.get(3).setStatus(TokenStatus.ADDED);
		LOG.debug("Adjusting %s to %s", tokens.get(5), TokenStatus.ADDED);
		tokens.get(4).setStatus(TokenStatus.ADDED);

		LOG.debug("Adjusting %s to %s", tokens.get(11), TokenStatus.MISSING);
		tokens.get(11).setStatus(TokenStatus.MISSING);

		LOG.debug("Adjusting %s to %s", tokens.get(15), TokenStatus.CHANGED);
		tokens.get(15).setStatus(TokenStatus.CHANGED);

		SimplePrinter printer = new SimplePrinter();
		printer.printToConsole(tokens, 7);
//		printer.printToConsole(tokens2);
	}
}
