package printer;

public enum SimpleColorType
{
	BACKGROUND(10), HIGH_INTENSITY(60), HIGH_INTENSITY_BACKGROUND(70);

	private int type;

	private SimpleColorType(int type)
	{
		this.type = type;
	}

	public int value()
	{
		return type;
	}
}
