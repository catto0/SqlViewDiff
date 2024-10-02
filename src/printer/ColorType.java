package printer;

public enum ColorType
{
	BACKGROUND(10), HIGH_INTENSITY(60), HIGH_INTENSITY_BACKGROUND(70);

	private int type;

	private ColorType(int type)
	{
		this.type = type;
	}

	public int value()
	{
		return type;
	}
}
