package tokenizer;

public class Token
{
	public enum TokenKind
	{
		KEYWORD, SIMPLE_BRACKET, SQUARE_BRACKET, TABLE, NICKNAME, COLUMN, WORD,
	}

	public enum TokenStatus
	{
		MISSING, CHANGED, GOOD,
	}

	private final String token;
	private TokenKind kind;
	private TokenStatus status;
	private final int beginning;
	private final int end;

	public Token(String token, TokenKind kind, int beginning, int end)
	{
		this.kind = kind;
		this.status = null;
		this.token = token;
		this.beginning = beginning;
		this.end = end;
	}

	public TokenKind getKind()
	{
		return this.kind;
	}

	public void setStatus(TokenStatus status)
	{
		this.status = status;
	}

	public TokenStatus getStatus()
	{
		return this.status;
	}

	public String getToken()
	{
		return this.token;
	}

	public int getBeginning()
	{
		return this.beginning;
	}

	public int getEnd()
	{
		return this.end;
	}

	@Override
	public String toString()
	{
		return "Token [token=\"" + token + "\", kind=" + kind + ", status=" + status + "]";
	}
}
