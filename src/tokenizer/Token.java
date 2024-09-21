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

    private TokenKind kind;
    private TokenStatus status; 
    private final String token;
    private final int beginning;
    private final int end;

    public Token(String token, int beginning, int end)
    {
        this.kind = TokenKind.WORD;
        this.status = TokenStatus.GOOD;
        this.token = token;
        this.beginning = beginning;
        this.end = end;
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
}
