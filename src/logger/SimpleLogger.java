package logger;

import static java.lang.System.Logger.Level.DEBUG;
import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.TRACE;
import static java.lang.System.Logger.Level.WARNING;

import java.util.ResourceBundle;

public class SimpleLogger implements ISimpleLogger
{
	private static final Object[] EMPTY = new Object[0];

	private Class<?> clazz = null;
	private Level logLevel = null;
	private int messageStartsAt = 20;

	public SimpleLogger(Class<?> clazz, Level logLevel)
	{
		this.clazz = clazz;
		this.logLevel = logLevel;
	}

	@Override
	public String getName()
	{
		return clazz.getName();
	}

	public synchronized void trace(String msg, Object... objects)
	{
		if (isNotLoggable(TRACE))
		{
			return;
		}

		String message = formatMessage(msg, objects);
		log(TRACE, null, message, EMPTY);
	}

	@Override
	public synchronized void debug(String msg, Object... objects)
	{
		if (isNotLoggable(DEBUG))
		{
			return;
		}

		String message = formatMessage(msg, objects);
		log(DEBUG, null, message, EMPTY);
	}

	@Override
	public synchronized void warn(String msg, Object... objects)
	{
		if (isNotLoggable(WARNING))
		{
			return;
		}

		String message = formatMessage(msg, objects);
		log(WARNING, null, message, EMPTY);
	}

	@Override
	public synchronized void warn(Throwable e, String msg, Object... objects)
	{
		if (isNotLoggable(WARNING))
		{
			return;
		}

		String message = formatMessage(msg, objects);
		log(WARNING, null, message, e);
	}

	@Override
	public synchronized void error(String msg, Object... objects)
	{
		if (isNotLoggable(ERROR))
		{
			return;
		}

		String message = formatMessage(msg, objects);
		log(ERROR, null, message, EMPTY);
	}

	@Override
	public synchronized void error(Throwable e, String msg, Object... objects)
	{
		if (isNotLoggable(ERROR))
		{
			return;
		}

		String message = formatMessage(msg, objects);
		log(ERROR, null, message, e);
	}

	@Override
	public boolean isLoggable(Level level)
	{
		return level.ordinal() >= logLevel.ordinal();
	}

	@Override
	public boolean isNotLoggable(Level level)
	{
		return !isLoggable(level);
	}

	private String formatMessage(String message, Object... objects)
	{
		if (objects == null || objects.length == 0)
		{
			return message;
		}

		return message.formatted(objects);
	}

	@Override
	public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown)
	{
		System.err.println(formatLog(msg));
		System.err.println(thrown);
	}

	@Override
	public void log(Level level, ResourceBundle bundle, String format, Object... params)
	{
		System.out.println(formatLog(format));
	}

	private String formatLog(String msg)
	{
		StringBuilder buffer = new StringBuilder();

		String time = "%tH:%<tM:%<tS:%<tL".formatted(System.currentTimeMillis());
		buffer.append(time);
		buffer.append(" | ");

		String simpleName = clazz.getSimpleName();
		int messageStartingIdx = messageStartsAt - clazz.getSimpleName().length() - 3;
		buffer.append(simpleName);
		buffer.append(" ".repeat(messageStartingIdx > 0 ? messageStartingIdx : 1));
		buffer.append(" | ");

		buffer.append(msg);
		return buffer.toString();
	}
}
