package logging;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class LoggerNameFilter implements Filter {
        String loggerName;

        public LoggerNameFilter(String loggerName){
            this.loggerName = loggerName;
        }

		public boolean isLoggable(LogRecord logRecord) {
			return logRecord.getLoggerName().contains(this.loggerName);
		}
	}
