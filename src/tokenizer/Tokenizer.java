package tokenizer;

import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import logger.SimpleLogger;
import tokenizer.Token.TokenKind;

public class Tokenizer implements ITokenizer
{
	private static final SimpleLogger LOG = new SimpleLogger(Tokenizer.class, Level.DEBUG);

	private static final Set<Character> SPECIAL_CHARACTERS = Set.of(new Character[] { ',', '.' });
	private static final Set<String> KEYWORDS = //
			Set.of(new String[] { "CREATE", "DROP", "DESC", "EXISTS", "ALTER", "VIEW", "AS", "SELECT", "DELETE",
				"UPDATE", "FORCE", "FROM", "INNER", "LEFT", "RIGHT", "JOIN", "GROUP", "BY", "TOP", ";" });

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

			if (word.matches("[\\(\\[]{1}[a-zA-Z0-9]+[\\)\\]]{1}|[a-zA-Z0-9]+[\\)\\]]{1}|[\\(\\[]{1}[a-zA-Z0-9]+"))
			{
				processBrackets(builder, word);
				continue;
			}

			builder.append(word).append(' ');
		}
		builder.append(";");

		LOG.debug("Fnished unifying view's format.");
		return builder.toString();
	}

	private static String spaceOutSpecialCharacters(String view)
	{
		StringBuilder builder = new StringBuilder((int) (view.length() * 1.2));
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

		int start = 0;
		if (word.charAt(0) == '[' || word.charAt(0) == '(')
		{
			builder.append(word.charAt(0)).append(' ');
			start++;
		}

		int end = word.length() - 1;
		if (word.charAt(end) == ']' || word.charAt(end) == ')')
		{
			builder.append(word.substring(start, end)).append(' ').append(word.charAt(end)).append(' ');
			return;
		}

		builder.append(word.substring(start, end + 1)).append(' ');
	}

	@Override
	public List<Token> tokenize(String view)
	{
		List<Token> tokens = createTokens(view);

		return tokens;
	}

	private static List<Token> createTokens(String view)
	{
		List<Token> tokens = new ArrayList<>();

		int endIdx = 0;
		String[] words = view.split(" ");
		for (int i = 0; i < words.length; i++)
		{
			String word = words[i];
			int beginningIdx = endIdx + 1;
			endIdx = beginningIdx + word.length();

			tokens.add(new Token(word, determineKind(word, words), beginningIdx, endIdx));
		}

		return tokens;
	}

	private static TokenKind determineKind(String word, String[] words)
	{
		if (KEYWORDS.contains(word))
		{
			return TokenKind.KEYWORD;
		}

		switch (word)
		{
		case "[":
		case "]":
			return TokenKind.SQUARE_BRACKET;
		case "(":
		case ")":
			return TokenKind.SIMPLE_BRACKET;
		}

		return TokenKind.WORD;
	}
}
