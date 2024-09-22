package logger;

import java.lang.System.Logger;

public interface ISimpleLogger extends Logger
{
	void trace(String msg, Object... objects);

	void debug(String msg, Object... objects);

	void warn(String msg, Object... objects);

	void warn(Throwable e, String msg, Object... objects);

	void error(String msg, Object... objects);

	void error(Throwable e, String msg, Object... objects);
}
