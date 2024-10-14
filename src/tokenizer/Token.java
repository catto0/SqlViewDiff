package tokenizer;

import java.util.Objects;

public class Token
{
	private final String originalWord;
	private final String token;
	private TokenKind kind;
	private TokenStatus status;
	private final int beginning;
	private final int end;

	public Token(String originalWord, String token, TokenKind kind, int beginning, int end)
	{
		this.originalWord = originalWord;
		this.token = token;
		this.kind = kind;
		this.status = TokenStatus.NOT_CHANGED;
		this.beginning = beginning;
		this.end = end;
	}

	public String getOriginalWord()
	{
		return originalWord;
	}

	public String getToken()
	{
		return this.token;
	}

	public void setKind(TokenKind kind)
	{
		this.kind = kind;
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

	@Override
	public int hashCode()
	{
		return Objects.hash(beginning, end, kind, originalWord, status, token);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		Token other = (Token) obj;
		return beginning == other.beginning && end == other.end && kind == other.kind
				&& Objects.equals(originalWord, other.originalWord) && status == other.status
				&& Objects.equals(token, other.token);
	}
}
