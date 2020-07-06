package robtest.stateinterfw.services;

import com.google.inject.Inject;
import robtest.stateinterfw.IState;
import robtest.stateinterfw.data.ITransactionRepository;
import robtest.stateinterfw.data.Param;
import robtest.stateinterfw.data.State;

public class StateManageService implements IStateManageService {
    private final ITransactionRepository repository;

    @Inject
    public StateManageService(ITransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public IState addOrUpdate(IState state) {
        try (repository) {
            var result = repository.querySingleResult("from State where name = :name", State.class, Param.list("name", state.getName()).all());
            if (result == null) {
                repository.save(state);
            } else {
                result.setTimeout(state.getTimeout());
                result.setRequired(state.isRequired());
                repository.sync(result);
                state.setId(result.getId());
            }
            return state;
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }
}
