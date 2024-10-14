package utils.color;

import java.util.Arrays;
import java.util.List;

import tokenizer.TokenStatus;

public class SimpleColor
{
	public static final String RESET = "\033[0m";
	public static final String COLOR = "\033[%dm";

	public enum Color
	{
		BLACK(0), RED(1), GREEN(2), YELLOW(3), BLUE(4), PURPLE(5), CYAN(6), WHITE(7);

		private int color;

		private Color(int color)
		{
			this.color = color;
		}

		public int value()
		{
			return color;
		}
	}

	public enum Type
	{
		NORMAL(30), BACKGROUND(40), HIGH_INTENSITY(90), HIGH_INTENSITY_BACKGROUND(100);

		private int type;

		private Type(int type)
		{
			this.type = type;
		}

		public int value()
		{
			return type;
		}
	}

	private static final List<Integer> STATUS_COLORS = Arrays.asList( //
			Type.BACKGROUND.value() + Color.RED.value(), //
			Type.BACKGROUND.value() + Color.YELLOW.value(), //
			Type.BACKGROUND.value() + Color.GREEN.value(), //
			null //
	);

	private SimpleColor()
	{}

	public static Integer of(TokenStatus status)
	{
		return STATUS_COLORS.get(status.value());
	}
}
