package org.example.interceptors;

import org.junit.jupiter.api.Test;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.UriInfo;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;

class RequestLoggingFilterTest {

    @Test
    void testFilter() throws IOException {
        // Mock ContainerRequestContext
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        UriInfo uriInfo = mock(UriInfo.class);
        when(uriInfo.getRequestUri()).thenReturn(URI.create("http://localhost:8080/api/v1/cars/toyota-camry"));
        when(requestContext.getUriInfo()).thenReturn(uriInfo);

        // Mock EntityStream
        InputStream entityStream = new ByteArrayInputStream("{\"make\":\"Toyota\",\"model\":\"Camry\"}".getBytes(StandardCharsets.UTF_8));
        when(requestContext.getEntityStream()).thenReturn(entityStream);

        // Create RequestLoggingFilter instance
        RequestLoggingFilter filter = new RequestLoggingFilter();

        // Call filter method
        filter.filter(requestContext);

        // Verify that logRequest was called
        verify(requestContext, times(1)).getEntityStream();
        verify(requestContext, times(1)).setEntityStream(any(InputStream.class));
    }

    @Test
    void testLogRequestWithEmptyRequestBody() throws IOException {
        // Mock ContainerRequestContext
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        UriInfo uriInfo = mock(UriInfo.class);
        when(uriInfo.getRequestUri()).thenReturn(URI.create("http://localhost:8080/api/v1/cars/toyota-camry"));
        when(requestContext.getUriInfo()).thenReturn(uriInfo);

        // Mock EntityStream with empty body
        InputStream entityStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
        when(requestContext.getEntityStream()).thenReturn(entityStream);

        // Create RequestLoggingFilter instance
        RequestLoggingFilter filter = new RequestLoggingFilter();

        // Call logRequest method
        filter.filter(requestContext);

        // Verify that logRequest was called
        verify(requestContext, times(1)).getEntityStream();
        verify(requestContext, times(1)).setEntityStream(any(InputStream.class));
    }
}