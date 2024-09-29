package tokenizer;

public enum TokenKind
{
	KEYWORD, SIMPLE_OPENING, SIMPLE_CLOSING, SQUARE_OPENING, SQUARE_CLOSING, LINKER, SEPARATOR, COMPARATOR, PARENT,
	ABBREVIATION, CHILD, WORD, NICKNAME, END;

	public static boolean isKeyword(TokenKind kind)
	{
		return KEYWORD.equals(kind);
	}

	public static boolean isSimpleOpening(TokenKind kind)
	{
		return SIMPLE_OPENING.equals(kind);
	}

	public static boolean isSimpleClosing(TokenKind kind)
	{
		return SIMPLE_CLOSING.equals(kind);
	}

	public static boolean isSquareOpening(TokenKind kind)
	{
		return SQUARE_OPENING.equals(kind);
	}

	public static boolean isSquareClosing(TokenKind kind)
	{
		return SQUARE_CLOSING.equals(kind);
	}

	public static boolean isLinker(TokenKind kind)
	{
		return LINKER.equals(kind);
	}

	public static boolean isSeparator(TokenKind kind)
	{
		return SEPARATOR.equals(kind);
	}

	public static boolean isComparator(TokenKind kind)
	{
		return COMPARATOR.equals(kind);
	}

	public static boolean isParent(TokenKind kind)
	{
		return PARENT.equals(kind);
	}

	public static boolean isAbbreviation(TokenKind kind)
	{
		return ABBREVIATION.equals(kind);
	}

	public static boolean isChild(TokenKind kind)
	{
		return CHILD.equals(kind);
	}

	public static boolean isWord(TokenKind kind)
	{
		return WORD.equals(kind);
	}

	public static boolean isNickname(TokenKind kind)
	{
		return NICKNAME.equals(kind);
	}

	public static boolean isEnd(TokenKind kind)
	{
		return END.equals(kind);
	}
}
