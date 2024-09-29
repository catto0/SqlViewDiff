package printer;

public enum SimpleColor
{
	BLACK(30), RED(31), GREEN(32), YELLOW(33), BLUE(34), PURPLE(35), CYAN(36), WHITE(37);

	private int color;

	private SimpleColor(int color)
	{
		this.color = color;
	}

	public int value()
	{
		return color;
	}
}
