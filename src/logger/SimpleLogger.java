package logger;

import static java.lang.System.Logger.Level.DEBUG;
import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.INFO;
import static java.lang.System.Logger.Level.TRACE;
import static java.lang.System.Logger.Level.WARNING;

import java.util.ResourceBundle;

public class SimpleLogger implements ISimpleLogger
{
	private static final Object[] EMPTY = new Object[0];

	private Class<?> clazz = null;
	private Level logLevel = null;
	private int messageStartsAt = 25;

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

	@Override
	public synchronized void trace(String msg, Object... objects)
	{
		if (isLoggable(TRACE))
		{
			String message = formatMessage(msg, objects);
			log(TRACE, null, message, EMPTY);
		}
	}

	@Override
	public synchronized void debug(String msg, Object... objects)
	{
		if (isLoggable(DEBUG))
		{
			String message = formatMessage(msg, objects);
			log(DEBUG, null, message, EMPTY);
		}
	}

	@Override
	public void info(String msg, Object... objects)
	{
		if (isLoggable(INFO))
		{
			String message = formatMessage(msg, objects);
			log(INFO, null, message, EMPTY);
		}
	}

	@Override
	public synchronized void warn(String msg, Object... objects)
	{
		if (isLoggable(WARNING))
		{
			String message = formatMessage(msg, objects);
			log(WARNING, null, message, EMPTY);
		}
	}

	@Override
	public synchronized void warn(Throwable e, String msg, Object... objects)
	{
		if (isLoggable(WARNING))
		{
			String message = formatMessage(msg, objects);
			log(WARNING, null, message, e);
		}
	}

	@Override
	public synchronized void error(String msg, Object... objects)
	{
		if (isLoggable(ERROR))
		{
			String message = formatMessage(msg, objects);
			log(ERROR, null, message, EMPTY);
		}
	}

	@Override
	public synchronized void error(Throwable e, String msg, Object... objects)
	{
		if (isLoggable(ERROR))
		{
			String message = formatMessage(msg, objects);
			log(ERROR, null, message, e);
		}
	}

	@Override
	public boolean isLoggable(Level level)
	{
		return level.ordinal() >= logLevel.ordinal();
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
		System.err.println(formatLog(level, msg));
		System.err.println(thrown);
	}

	@Override
	public void log(Level level, ResourceBundle bundle, String format, Object... params)
	{
		System.out.println(formatLog(level, format));
	}

	private String formatLog(Level level, String msg)
	{
		String time = "%tH:%<tM:%<tS,%<tL".formatted(System.currentTimeMillis());
		String logLevel = getLevelType(level);
		String simpleClassName = clazz.getSimpleName();
		String nameAllignment = " ".repeat(Math.max(messageStartsAt - simpleClassName.length() - 3, 0));

		int preallocSize = time.length() + 3 + logLevel.length() + 3 + simpleClassName.length()
				+ nameAllignment.length() + 3 + msg.length();
		StringBuilder buffer = new StringBuilder((int) (preallocSize * 1.1));

		buffer.append(time).append(" | ");
		buffer.append(logLevel).append(" | ");
		buffer.append(simpleClassName).append(nameAllignment).append(" | ");
		buffer.append(msg);

		return buffer.toString();
	}

	private static String getLevelType(Level level)
	{
		return switch (level)
		{
		case Level.TRACE	-> "TRACE";
		case Level.DEBUG	-> "DEBUG";
		case Level.INFO		-> "INFO ";
		case Level.WARNING	-> "WARN ";
		case Level.ERROR	-> "ERROR";
		default				-> throw new IllegalArgumentException("Unexpected value: " + level);
		};
	}
}
