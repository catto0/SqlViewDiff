package printer;

import static tokenizer.Keyword.PRINT_NEW_LINE_AFTER_KEYWORDS;
import static tokenizer.Keyword.PRINT_NEW_LINE_BEFORE_KEYWORDS;
import static tokenizer.TokenKind.KEYWORD;
import static tokenizer.TokenKind.isEnd;
import static tokenizer.TokenKind.isLinker;
import static tokenizer.TokenKind.isSeparator;
import static tokenizer.TokenKind.isSquareClosing;
import static tokenizer.TokenKind.isSquareOpening;

import java.util.List;

import tokenizer.Keyword;
import tokenizer.Token;
import tokenizer.TokenKind;

public class Printer implements IPrinter
{
	private static final String RESET = "\u001b[0m";
	private static final String COLOR = "\u001b[%dm";

	private static Integer indentation = 0;

	@Override
	public void printToConsole(List<Token> tokens)
	{
		StringBuilder builder = new StringBuilder(tokens.size() * 7);
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
				builder.append(token.getOriginalWord()).append(System.lineSeparator());
				appendTab(builder, indentation);
				break;
			default:
				builder.append(token.getOriginalWord());
				conditionallyAppendSpace(builder, tokens.get(i - 1).getKind(), token.getKind(),
						tokens.get(i + 1).getKind());
				break;
			}
		}

		builder.append(System.lineSeparator());
		System.out.println(builder.toString());
	}

	@Override
	public void printToFile(List<Token> tokens)
	{
		// TODO Auto-generated method stub

	}

	private static void processKeyword(StringBuilder builder, Token leftToken, Token token)
	{
		Keyword type = Keyword.valueOf(token.getToken());
		Keyword leftType = leftToken != null && KEYWORD.equals(leftToken.getKind())
				? Keyword.valueOf(leftToken.getToken())
				: null;

		if (PRINT_NEW_LINE_BEFORE_KEYWORDS.contains(type))
		{
			if (leftType == null || !PRINT_NEW_LINE_BEFORE_KEYWORDS.contains(leftType))
			{
				builder.append(System.lineSeparator());
				if (PRINT_NEW_LINE_AFTER_KEYWORDS.contains(type))
				{
					indentation = indentation > 0 ? indentation - 1 : 0;
				}
				appendTab(builder, indentation);
			}

			builder.append(token.getOriginalWord());
			if (PRINT_NEW_LINE_AFTER_KEYWORDS.contains(type))
			{
				builder.append(System.lineSeparator());
				indentation++;
				appendTab(builder, indentation);
			}
			else
			{
				builder.append(' ');
			}

			return;
		}

		builder.append(token.getOriginalWord()).append(' ');
	}

	private static void conditionallyAppendSpace(StringBuilder builder, TokenKind left, TokenKind token,
			TokenKind right)
	{
		if (isLinker(left) || isLinker(token) || isLinker(right) || isSeparator(right) || isEnd(right)
				|| isSquareOpening(token) || isSquareOpening(right) || isSquareClosing(right))
		{
			return;
		}

		builder.append(' ');
	}

	private static void appendTab(StringBuilder builder, int indentation)
	{
		builder.append("    ".repeat(indentation));
	}
}
