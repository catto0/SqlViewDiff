package tokenizer;

public enum TokenStatus
{
	MISSING(0), CHANGED(1), ADDED(2), NOT_CHANGED(3);

	private int value;

	TokenStatus(int value)
	{
		this.value = value;
	}

	public int value()
	{
		return value;
	}
}
