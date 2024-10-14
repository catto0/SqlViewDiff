package utils.printer;

import static tokenizer.Keyword.PRINT_NEW_LINE_AFTER_KEYWORDS;
import static tokenizer.Keyword.PRINT_NEW_LINE_BEFORE_KEYWORDS;
import static tokenizer.TokenKind.isEnd;
import static tokenizer.TokenKind.isLinker;
import static tokenizer.TokenKind.isSeparator;
import static tokenizer.TokenKind.isSquareClosing;
import static tokenizer.TokenKind.isSquareOpening;

import java.lang.System.Logger.Level;
import java.util.List;

import tokenizer.Keyword;
import tokenizer.Token;
import tokenizer.TokenKind;
import utils.builder.SimpleStringBuilder;
import utils.color.SimpleColor;
import utils.logger.SimpleLogger;

public class SimplePrinter extends SimplePrinterBase implements ISimplePrinter
{
	private static final SimpleLogger LOG = new SimpleLogger(SimplePrinter.class, Level.DEBUG);

	private static Integer indentation = 1;

	@Override
	protected String createFormattedOutput(List<Token> tokens, int averageWordLength)
	{
		SimpleStringBuilder builder = new SimpleStringBuilder(tokens.size() * (averageWordLength + 1));
		builder.append(System.lineSeparator());

		for (int i = 0; i < tokens.size(); i++)
		{
			Token token = tokens.get(i);
			LOG.trace("Processing word %s, with index %s", token, i);
			switch (token.getKind())
			{
			case END:
				processEnd(builder, token);
				break;
			case KEYWORD:
				Token leftToken = i > 1 ? tokens.get(i - 1) : null;
				processKeyword(builder, leftToken, token);
				break;
			case SEPARATOR:
				processSeparator(builder, token);
				break;
			default:
				processDefault(builder, token, tokens.get(i + 1));
				break;
			}
		}

		return builder.append(System.lineSeparator()).toString();
	}

	private static void processEnd(SimpleStringBuilder builder, Token token)
	{
		builder.append(token.getOriginalWord(), SimpleColor.of(token.getStatus()));
	}

	private static void processKeyword(SimpleStringBuilder builder, Token leftToken, Token token)
	{
		Keyword keyword = Keyword.valueOf(token.getToken());
		boolean printNewLineBefore = PRINT_NEW_LINE_BEFORE_KEYWORDS.contains(keyword);
		if (!printNewLineBefore)
		{
			builder.append(token.getOriginalWord(), SimpleColor.of(token.getStatus()));
			builder.append(' ');
			return;
		}

		Keyword leftKeyword = Keyword.valueOfOrNull(leftToken.getToken());
		boolean printNewLineAfter = PRINT_NEW_LINE_AFTER_KEYWORDS.contains(keyword);
		if (leftKeyword == null || !PRINT_NEW_LINE_BEFORE_KEYWORDS.contains(leftKeyword))
		{
			if (printNewLineAfter)
			{
				LOG.trace("Ran into %s, decrementing indentation from %s to %s", keyword, indentation, --indentation);
			}

			appendNewLineAndTab(builder);
		}

		builder.append(token.getOriginalWord(), SimpleColor.of(token.getStatus()));
		if (printNewLineAfter)
		{
			LOG.trace("Ran into %s, incrementing indentation from %s to %s", keyword, indentation, ++indentation);
			appendNewLineAndTab(builder);
			return;
		}

		builder.append(' ');
	}

	private static void appendNewLineAndTab(SimpleStringBuilder builder)
	{
		builder.append(System.lineSeparator());
		if (indentation > 0)
		{
			builder.append("    ".repeat(indentation));
		}
	}

	private static void processSeparator(SimpleStringBuilder builder, Token token)
	{
		builder.append(token.getOriginalWord(), SimpleColor.of(token.getStatus()));
		appendNewLineAndTab(builder);
	}

	private static void processDefault(SimpleStringBuilder builder, Token token, Token rightToken)
	{
		builder.append(token.getOriginalWord(), SimpleColor.of(token.getStatus()));
		conditionallyAppendSpace(builder, token.getKind(), rightToken.getKind());
	}

	private static void conditionallyAppendSpace(SimpleStringBuilder builder, TokenKind currentKind,
			TokenKind rightKind)
	{
		if (isLinker(currentKind) || isLinker(rightKind) || isSeparator(rightKind) || isEnd(rightKind)
				|| isSquareOpening(currentKind) || isSquareOpening(rightKind) || isSquareClosing(rightKind))
		{
			return;
		}

		builder.append(' ');
	}
}
