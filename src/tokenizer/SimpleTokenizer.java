package tokenizer;

import static tokenizer.Keyword.DEFINED_KEYWORDS;
import static tokenizer.Keyword.LEADING_KEYWORDS;
import static tokenizer.Keyword.SELECT;
import static tokenizer.TokenKind.ABBREVIATION;
import static tokenizer.TokenKind.CHILD;
import static tokenizer.TokenKind.COMPARATOR;
import static tokenizer.TokenKind.END;
import static tokenizer.TokenKind.KEYWORD;
import static tokenizer.TokenKind.LINKER;
import static tokenizer.TokenKind.NICKNAME;
import static tokenizer.TokenKind.PARENT;
import static tokenizer.TokenKind.SEPARATOR;
import static tokenizer.TokenKind.SIMPLE_CLOSING;
import static tokenizer.TokenKind.SIMPLE_OPENING;
import static tokenizer.TokenKind.SQUARE_CLOSING;
import static tokenizer.TokenKind.SQUARE_OPENING;
import static tokenizer.TokenKind.WORD;
import static tokenizer.TokenKind.isChild;
import static tokenizer.TokenKind.isKeyword;
import static tokenizer.TokenKind.isLinker;
import static tokenizer.TokenKind.isParent;
import static tokenizer.TokenKind.isSeparator;
import static tokenizer.TokenKind.isSquareClosing;
import static tokenizer.TokenKind.isSquareOpening;

import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import logger.SimpleLogger;

public class SimpleTokenizer implements ISimpleTokenizer
{
	private static final SimpleLogger LOG = new SimpleLogger(SimpleTokenizer.class, Level.DEBUG);

	private static final Set<Character> SPECIAL_CHARACTERS = Set.of(new Character[] { ',', '.' });

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
		Set<String> names = new HashSet<>();
		Set<String> abbreviations = new HashSet<>();
		List<Token> tokens = new ArrayList<>();

		int endIdx = 0;
		Keyword leadingKeyword = null;
		String[] words = view.split(" ");
		for (int index = 0; index < words.length; index++)
		{
			String word = words[index];
			String token = word.toUpperCase();

			int beginningIdx = endIdx + 1;
			endIdx = beginningIdx + word.length();

			TokenKind kind = determineKind(names, abbreviations, leadingKeyword, tokens, token, index);
			if (LEADING_KEYWORDS.contains(token))
			{
				leadingKeyword = Keyword.valueOf(token);
			}

			tokens.add(new Token(word, token, kind, beginningIdx, endIdx));
		}

		return tokens;
	}

	private static TokenKind determineKind(Set<String> names, Set<String> abbreviations, Keyword leadingKeyword,
			List<Token> tokens, String token, int index)
	{
		if (DEFINED_KEYWORDS.contains(token))
		{
			return KEYWORD;
		}

		switch (token)
		{
		case "(":
			return SIMPLE_OPENING;
		case ")":
			return SIMPLE_CLOSING;
		case "[":
			return SQUARE_OPENING;
		case "]":
			return SQUARE_CLOSING;
		case ".":
			return LINKER;
		case ",":
			return SEPARATOR;
		case "=":
		case "LIKE":
			return COMPARATOR;
		case ";":
			return END;
		}

		TokenKind prevKind = tokens.get(index - 1).getKind();
		TokenKind penuKind = tokens.get(index - 2).getKind();

		switch (leadingKeyword)
		{
		case CREATE:
		case VIEW:
			if (names.contains(token) || isKeyword(prevKind) || isSquareOpening(prevKind) && isKeyword(penuKind))
			{
				names.add(token);
				return PARENT;
			}

			if (isSquareOpening(prevKind) && isLinker(penuKind) || isLinker(prevKind) && isParent(penuKind))
			{
				return CHILD;
			}

			break;

		case SELECT:
			if (abbreviations.contains(token) || isSeparator(prevKind)
					|| SELECT.value().equals(tokens.get(index - 1).getToken()))
			{
				abbreviations.add(token);
				return ABBREVIATION;
			}

			if (isLinker(prevKind) || isSquareOpening(prevKind) && isLinker(penuKind))
			{
				return CHILD;
			}

			if (isKeyword(prevKind) || isSquareOpening(prevKind) && isKeyword(penuKind))
			{
				return NICKNAME;
			}

			break;

		case FROM:
		case JOIN:
			if (abbreviations.contains(token) || isChild(prevKind) || isSquareClosing(prevKind) && isChild(penuKind))
			{
				abbreviations.add(token);
				return ABBREVIATION;
			}

			if (names.contains(token) || isKeyword(prevKind) || isSquareOpening(prevKind) && isKeyword(penuKind))
			{
				names.add(token);
				return PARENT;
			}

			if (isLinker(prevKind) || isSquareOpening(prevKind) && isLinker(penuKind))
			{
				return CHILD;
			}

			break;

		case Keyword.WHERE:
			// TODO: Handle where
			break;

		default:
			LOG.warn("Unhandled keyword %s", leadingKeyword);
			return CHILD;
		}

		LOG.error("Unhandled condition when resolving kind for %s, last keyword was %s, last 2 tokens were %s, %s",
				token, leadingKeyword, tokens.get(index - 1), tokens.get(index - 2));
		return WORD;
	}
}
