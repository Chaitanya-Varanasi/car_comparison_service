package org.example.interceptors;

import org.junit.jupiter.api.Test;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import java.io.IOException;

import static org.mockito.Mockito.*;

class ResponseLoggingFilterTest {

    @Test
    void testFilter_SuccessResponse_LogsResponse() throws IOException {
        // Mock ContainerRequestContext and ContainerResponseContext
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        ContainerResponseContext responseContext = mock(ContainerResponseContext.class);

        // Mock request method and response status
        when(requestContext.getMethod()).thenReturn("POST");
        StatusType statusType = mock(StatusType.class);
        when(statusType.getFamily()).thenReturn(Response.Status.Family.SUCCESSFUL);
        when(responseContext.getStatusInfo()).thenReturn(statusType);

        // Mock response entity
        Object responseEntity = new Object();
        when(responseContext.getEntity()).thenReturn(responseEntity);

        // Create ResponseLoggingFilter instance
        ResponseLoggingFilter filter = new ResponseLoggingFilter();

        // Call filter method
        filter.filter(requestContext, responseContext);

        // Verify that logResponse was called
        verify(responseContext, times(1)).getEntity();
    }

    @Test
    void testFilter_ErrorResponse_DoesNotLogResponse() throws IOException {
        // Mock ContainerRequestContext and ContainerResponseContext
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        ContainerResponseContext responseContext = mock(ContainerResponseContext.class);

        // Mock request method and response status
        when(requestContext.getMethod()).thenReturn("POST");
        StatusType statusType = mock(StatusType.class);
        when(statusType.getFamily()).thenReturn(Response.Status.Family.CLIENT_ERROR);
        when(responseContext.getStatusInfo()).thenReturn(statusType);

        // Mock response entity
        Object responseEntity = new Object();
        when(responseContext.getEntity()).thenReturn(responseEntity);

        // Create ResponseLoggingFilter instance
        ResponseLoggingFilter filter = new ResponseLoggingFilter();

        // Call filter method
        filter.filter(requestContext, responseContext);

        // Verify that logResponse was not called
        verify(responseContext, never()).getEntity();
    }

    @Test
    void testFilter_GetResponse_DoesNotLogResponse() throws IOException {
        // Mock ContainerRequestContext and ContainerResponseContext
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        ContainerResponseContext responseContext = mock(ContainerResponseContext.class);

        // Mock request method and response status
        when(requestContext.getMethod()).thenReturn("GET");
        StatusType statusType = mock(StatusType.class);
        when(statusType.getFamily()).thenReturn(Response.Status.Family.SUCCESSFUL);
        when(responseContext.getStatusInfo()).thenReturn(statusType);

        // Mock response entity
        Object responseEntity = new Object();
        when(responseContext.getEntity()).thenReturn(responseEntity);

        // Create ResponseLoggingFilter instance
        ResponseLoggingFilter filter = new ResponseLoggingFilter();

        // Call filter method
        filter.filter(requestContext, responseContext);

        // Verify that logResponse was not called
        verify(responseContext, never()).getEntity();
    }

    @Test
    void testLogResponse_LogsResponse() {
        // Mock ContainerRequestContext and ContainerResponseContext
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        ContainerResponseContext responseContext = mock(ContainerResponseContext.class);

        // Mock response entity
        when(responseContext.getEntity()).thenReturn(null);
        when(responseContext.getStatusInfo()).thenReturn(Response.Status.OK);


        // Create ResponseLoggingFilter instance
        ResponseLoggingFilter filter = new ResponseLoggingFilter();

        // Call logResponse method
        filter.filter(requestContext, responseContext);

        // Verify that logResponse was called
        verify(responseContext, times(1)).getEntity();
    }

    @Test
    void testLogResponse_DoesNotLogResponse_NullEntity() {
        // Mock ContainerRequestContext and ContainerResponseContext
        ContainerRequestContext requestContext = mock(ContainerRequestContext.class);
        ContainerResponseContext responseContext = mock(ContainerResponseContext.class);

        // Mock response entity as null
        when(responseContext.getEntity()).thenReturn(null);
        when(responseContext.getStatusInfo()).thenReturn(Response.Status.OK);

        // Create ResponseLoggingFilter instance
        ResponseLoggingFilter filter = new ResponseLoggingFilter();

        // Call logResponse method
        filter.filter(requestContext, responseContext);

        // Verify that logResponse was called
        verify(responseContext, times(1)).getEntity();
    }
}