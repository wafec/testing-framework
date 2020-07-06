package robtest.stateinterfw.services;

import robtest.stateinterfw.IState;

public interface IStateManageService {
    IState addOrUpdate(IState state);
}
