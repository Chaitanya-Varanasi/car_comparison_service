package org.example.util.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;


/**
 * This class provides a generic framework for executing asynchronous tasks.
 * It takes a collection of requests and a function to process each request,
 * and executes the function asynchronously on a separate thread pool.
 *
 * @param <R> The type of the request object.
 * @param <S> The type of the response object.
 */
public abstract class AsyncTaskExecutor<R, S> {

    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskExecutor.class);


    /**
     * Executes the given function asynchronously for each request in the provided collection.
     *
     * @param requests The collection of requests to be processed.
     * @param functionToRun The function to be executed for each request.
     * @param executorService The thread pool to use for asynchronous execution.
     * @return A map containing the responses for each request.
     */
    public Map<R, S> compute(Iterable<R> requests, Function<R, S> functionToRun, ExecutorService executorService) {
        List<CompletableFuture<Map.Entry<R, S>>> completableFutures = new ArrayList<>();


        for (R request : requests) {
            // Create a CompletableFuture that returns a Map.Entry<T, S>
            completableFutures.add(CompletableFuture.supplyAsync(() -> {
                // Apply the function to the request
                S response = functionToRun.apply(request);
                return new AbstractMap.SimpleEntry<>(request, response);
            }, executorService));
        }
        CompletableFuture<Void> finalFuture = CompletableFuture
                .allOf(completableFutures.toArray(new CompletableFuture[0]));
        try {
            finalFuture.get();
            Map<R, S> responseMap = new HashMap<>();
            for (CompletableFuture<Map.Entry<R, S>> completableFuture : completableFutures) {
                Map.Entry<R, S> entry = completableFuture.get();
                responseMap.put(entry.getKey(), entry.getValue());
            }
            return responseMap;
        } catch (ExecutionException e) {
            logger.error("Error during asynchronous computation: {}", e);
            // Handle the exception appropriately, e.g., log the error and return an empty map
            throw new RuntimeException("Error during asynchronous computation", e);
        } catch (InterruptedException e) {
            String interruptingThread = Thread.currentThread().getThreadGroup().getParent().getName();
            String errorMsg = String.format("Interrupted Exception occurred by thread %s with error %s",
                    interruptingThread, e.getMessage());
            Thread.currentThread().interrupt();
            logger.error(errorMsg);
            throw new RuntimeException("The task was interrupted: ", e);
        }
    }


}

