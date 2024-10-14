package utils.builder;

import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.List;

import utils.color.SimpleColor;
import utils.logger.SimpleLogger;

public class SimpleStringBuilder
{
	private static final SimpleLogger LOG = new SimpleLogger(SimpleStringBuilder.class, Level.TRACE);

	private List<Integer> lastColors = new ArrayList<>();;
	private StringBuilder builder;

	public SimpleStringBuilder()
	{
		builder = new StringBuilder();
	}

	public SimpleStringBuilder(int capacity)
	{
		builder = new StringBuilder(capacity);
	}

	public <T> SimpleStringBuilder append(T value)
	{
		builder.append(value);
		return this;
	}

	public <T> SimpleStringBuilder append(T value, Integer color)
	{
		clearIfNecessary();

		if (lastColors.size() == 1)
		{
			if (lastColors.get(0) == color)
			{
				return this.append(value);
			}

			lastColors.set(0, color);
		}
		else
		{
			lastColors.add(color);
		}

		LOG.trace("Changing color for word \"%s\" to %s", value, color);
		builder.append(color == null ? SimpleColor.RESET : SimpleColor.COLOR.formatted(color));
		builder.append(value);
		return this;
	}

	// TODO: fix implementation of appending more colors
//	public <T> SimpleStringBuilder append(T value, Integer... colors)
//	{
//		if (colors != null && colors.length > 0)
//		{
//			for (Integer color : colors)
//			{
//				builder.append(SimpleColor.COLOR.formatted(color));
//			}
//			builder.append(value).append(SimpleColor.RESET);
//			return this;
//		}
//
//		return this.append(value);
//	}

	private void clearIfNecessary()
	{
		if (lastColors.size() > 1)
		{
			lastColors.clear();
			builder.append(SimpleColor.RESET);
		}
	}

	@Override
	public String toString()
	{
		return builder.toString();
	}
}
