package printer;

import static tokenizer.Keyword.PRINT_NEW_LINE_AFTER_KEYWORDS;
import static tokenizer.Keyword.PRINT_NEW_LINE_BEFORE_KEYWORDS;
import static tokenizer.TokenKind.KEYWORD;
import static tokenizer.TokenKind.isEnd;
import static tokenizer.TokenKind.isLinker;
import static tokenizer.TokenKind.isSeparator;
import static tokenizer.TokenKind.isSquareClosing;
import static tokenizer.TokenKind.isSquareOpening;

import java.lang.System.Logger.Level;
import java.util.List;

import logger.SimpleLogger;
import tokenizer.Keyword;
import tokenizer.Token;
import tokenizer.TokenKind;

public class SimplePrinter implements ISimplePrinter
{
	private static final SimpleLogger LOG = new SimpleLogger(SimplePrinter.class, Level.DEBUG);

	private static final String RESET = "\u001b[0m";
	private static final String COLOR = "\u001b[%dm";

	private static Integer indentation = 0;

	@Override
	public void printToConsole(List<Token> tokens)
	{
		StringBuilder builder = new StringBuilder(tokens.size() * 7).append(System.lineSeparator());
		for (int i = 0; i < tokens.size(); i++)
		{
			Token token = tokens.get(i);
			switch (token.getKind())
			{
			case END:
				builder.append(token.getOriginalWord());
				break;
			case KEYWORD:
				Token leftToken = i > 1 ? tokens.get(i - 1) : null;
				processKeyword(builder, leftToken, token);
				break;
			case SEPARATOR:
				builder.append(token.getOriginalWord());
				appendNewLineAndTab(builder);
				break;
			default:
				builder.append(token.getOriginalWord());
				conditionallyAppendSpace(builder, token.getKind(), tokens.get(i + 1).getKind());
				break;
			}
		}

		builder.append(System.lineSeparator());
		LOG.info("%s%s", System.lineSeparator(), builder.toString());
	}

	private static void processKeyword(StringBuilder builder, Token leftToken, Token token)
	{
		Keyword type = Keyword.valueOf(token.getToken());
		boolean printNewLineBefore = PRINT_NEW_LINE_BEFORE_KEYWORDS.contains(type);
		if (!printNewLineBefore)
		{
			builder.append(token.getOriginalWord()).append(' ');
			return;
		}

		boolean printNewLineAfter = PRINT_NEW_LINE_AFTER_KEYWORDS.contains(type);
		Keyword leftType = leftToken != null && KEYWORD.equals(leftToken.getKind())
				? Keyword.valueOf(leftToken.getToken())
				: null;

		if (leftType == null || !PRINT_NEW_LINE_BEFORE_KEYWORDS.contains(leftType))
		{
			if (printNewLineAfter)
			{
				LOG.trace("Ran into %s, decrementing indentation from %s to %s", type, indentation, indentation - 1);
				indentation = indentation > 0 ? indentation - 1 : 0;
			}

			appendNewLineAndTab(builder);
		}

		builder.append(token.getOriginalWord());
		if (printNewLineAfter)
		{
			LOG.trace("Ran into %s, incrementing indentation from %s to %s", type, indentation, indentation + 1);
			indentation++;
			appendNewLineAndTab(builder);
			return;
		}

		builder.append(' ');
	}

	private static void appendNewLineAndTab(StringBuilder builder)
	{
		builder.append(System.lineSeparator()).append("    ".repeat(indentation));
	}

	private static void conditionallyAppendSpace(StringBuilder builder, TokenKind token, TokenKind right)
	{
		if (isLinker(token) || isLinker(right) || isSeparator(right) || isEnd(right) || isSquareOpening(token)
				|| isSquareOpening(right) || isSquareClosing(right))
		{
			return;
		}

		builder.append(' ');
	}

	@Override
	public void printToFile(List<Token> tokens)
	{
		// TODO Auto-generated method stub

	}

}
