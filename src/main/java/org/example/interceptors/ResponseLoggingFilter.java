package org.example.interceptors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


@Named
@Provider
public class ResponseLoggingFilter implements ContainerResponseFilter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseLoggingFilter.class);

    @Override
    public void filter(ContainerRequestContext containerRequestContext,
            ContainerResponseContext containerResponseContext) {
        logResponse(containerRequestContext, containerResponseContext);
    }

    private void logResponse(ContainerRequestContext containerRequestContext,
            ContainerResponseContext containerResponseContext) {
        if (!HttpMethod.GET.equals(containerRequestContext.getMethod())
                && Response.Status.Family.SUCCESSFUL == containerResponseContext.getStatusInfo().getFamily()) {
            Object response = containerResponseContext.getEntity();
            if (response != null) {
                logger.info(String.format("Response: %s", response));
                System.out.println(String.format("Response: %s", response));
            }
        }
    }
}
