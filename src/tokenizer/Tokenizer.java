package tokenizer;

import static java.lang.System.Logger.Level.DEBUG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import logger.SimpleLogger;

public class Tokenizer implements ITokenizer
{
	private static final SimpleLogger LOG = new SimpleLogger(Tokenizer.class, DEBUG);

	private static final List<Character> SPECIAL_CHARACTERS = Arrays.asList(',', '.');
	private static final List<String> KEYWORDS = Arrays.asList( //
			"CREATE", "DROP", "DESC", "EXISTS", "ALTER", "VIEW", "AS", "SELECT", "DELETE", "UPDATE", "FORCE", "FROM",
			"INNER", "LEFT", "RIGHT", "JOIN", "GROUP", "BY", "TOP", ";");

	@Override
	public String unifyFormat(String view)
	{
		LOG.debug("Unifying view's format.");
		StringBuilder builder = new StringBuilder(view.length());
		String preprocessedView = spaceOutSpecialCharacters(view);
		LOG.debug("Finished preprocessing view %s", preprocessedView);

		for (String word : preprocessedView.split(" "))
		{
			if (word.isBlank())
			{
				continue;
			}

			if (word.matches("[\\(\\[]{1}.+[\\)\\]]{1}"))
			{
				processBrackets(builder, word);
				continue;
			}

			builder.append(word).append(" ");
		}
		builder.append(";");

		LOG.debug("Fnished unifying view's format.");
		return builder.toString();
	}

	private static String spaceOutSpecialCharacters(String view)
	{
		StringBuilder builder = new StringBuilder();
		for (char c : view.toCharArray())
		{
			if (SPECIAL_CHARACTERS.contains(c))
			{
				LOG.trace("Adding spacing for special character \"%c\"", c);
				builder.append(" " + c + " ");
				continue;
			}

			if (';' == c)
			{
				continue;
			}

			builder.append(c);
		}

		return builder.toString();
	}

	private static void processBrackets(StringBuilder builder, String word)
	{
		LOG.trace("Processing brackets for word %s", word);

		int len = word.length();
		builder.append(word.charAt(0)).append(" ");
		builder.append(word.substring(1, len - 1).trim()).append(" ");
		builder.append(word.charAt(len - 1)).append(" ");
	}

	@Override
	public List<Token> tokenize(String view)
	{
		List<Token> tokens = createTokens(view);
		classifyTokens(tokens);

		return tokens;
	}

	private static List<Token> createTokens(String view)
	{
		List<Token> tokens = new ArrayList<>();
		int end = 0;
		for (String word : view.split(" "))
		{
			int beginning = end + 1;
			end = beginning + word.length();

			tokens.add(new Token(word, beginning, end));
		}

		return tokens;
	}

	private static void classifyTokens(List<Token> tokens)
	{
		// TODO: set token kind
	}
}
