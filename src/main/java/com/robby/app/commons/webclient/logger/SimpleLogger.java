package com.robby.app.commons.webclient.logger;

import feign.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created @2019/4/24 10:43
 *
 * @auth Robby
 */
public class SimpleLogger extends Logger {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SimpleLogger.class);

    @Override
    protected void log(String configKey, String format, Object... args) {
        LOG.info("[{}] {}", configKey, String.format(format, args));
    }
}
