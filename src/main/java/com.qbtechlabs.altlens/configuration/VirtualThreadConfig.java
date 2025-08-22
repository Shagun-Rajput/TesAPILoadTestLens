package com.qbtechlabs.altlens.configuration;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ProtocolHandler;
import org.slf4j.Logger;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

import static com.qbtechlabs.altlens.constants.Constants.MSG_CONFIGURING_TOMCAT_VIRTUAL_THREADS;

/**
 * @author shagun.rajput
 */
@Configuration
public class VirtualThreadConfig {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(VirtualThreadConfig.class);
    /*******************************************************************************************************************
     * Configures Tomcat to use virtual threads for handling requests.
     * This allows for better concurrency handling in applications that require high throughput.
     *
     * @return a WebServerFactoryCustomizer that configures Tomcat's protocol handler to use virtual threads.
        ***************************************************************************************************************/
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatVirtualThreads() {
        // This customizer sets the executor for Tomcat's protocol handler to use virtual threads.
        LOGGER.info(MSG_CONFIGURING_TOMCAT_VIRTUAL_THREADS);
        return factory -> factory.addConnectorCustomizers((Connector connector) -> {
            ProtocolHandler protocolHandler = connector.getProtocolHandler();
            if (protocolHandler != null) {
                protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
            }
        });
    }
}