package executor.api.model.mapper;

public interface Mapper <F, T> {

    T map (F from);

}
