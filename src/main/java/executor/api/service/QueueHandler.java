package executor.api.service;

import java.util.Optional;

public interface QueueHandler<T> {

    boolean add(T item);

    Optional<T> poll();

    int queueSize();

}
