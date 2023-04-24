package executor.api.captor;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ResultCaptor<T> implements Answer<T> {

    private T result = null;

    public T getResult() {
        return result;
    }

    @Override
    public T answer(InvocationOnMock invocation) throws Throwable {
        result = (T) invocation.callRealMethod();
        return result;
    }
}
