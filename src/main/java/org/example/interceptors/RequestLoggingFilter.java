package org.example.interceptors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.inject.Named;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;


@Named
@Provider
public class RequestLoggingFilter implements ContainerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        logRequest(containerRequestContext);
    }

    private void logRequest(ContainerRequestContext containerRequestContext) {
        String requestBody = null;
        try {
            requestBody = IOUtils.toString(containerRequestContext.getEntityStream(), StandardCharsets.UTF_8);
            // replace input stream for Jersey as we've already read it
            InputStream in = IOUtils.toInputStream(requestBody, StandardCharsets.UTF_8);
            containerRequestContext.setEntityStream(in);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read request body");
        }
        URI uri = containerRequestContext.getUriInfo().getRequestUri();
        String uriInfo = uri.getPath();

        logger.info(String.format("Request URI: %s", uriInfo));
        System.out.println(String.format("Request URI: %s", uriInfo));
        if (StringUtils.isNotEmpty(requestBody)) {
            logger.info(String.format("Request body: %s", requestBody));
            System.out.println(String.format("Request body: %s", requestBody));
        }
    }
}
