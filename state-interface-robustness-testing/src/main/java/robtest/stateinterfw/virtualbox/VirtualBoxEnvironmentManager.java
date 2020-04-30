package robtest.stateinterfw.virtualbox;

import robtest.stateinterfw.IEnvironmentManager;
import robtest.stateinterfw.ITestExecutionContext;
import robtest.stateinterfw.ITestSpecs;
import robtest.stateinterfw.data.IEntity;
import robtest.stateinterfw.data.IRepository;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class VirtualBoxEnvironmentManager implements IVirtualBoxEnvironmentManager {
    private ITestExecutionContext _testExecutionContext;
    private IVirtualBoxManageClient _virtualBoxManageClient;
    private IRepository _repository;

    public VirtualBoxEnvironmentManager(IVirtualBoxManageClient virtualBoxManageClient,
                                        IRepository repository) {
        this._testExecutionContext = null;
        this._virtualBoxManageClient = virtualBoxManageClient;
        this._repository = repository;
    }

    private VirtualBoxEnvironment[] getDevices() {
        VirtualBoxEnvironment[] result = new VirtualBoxEnvironment[0];
        if (_testExecutionContext != null) {
            if (_testExecutionContext.getSpecs() != null) {
                if (_testExecutionContext.getSpecs().getEnvironmentCount() > 0) {
                    ITestSpecs specs = _testExecutionContext.getSpecs();
                    result = new VirtualBoxEnvironment[specs.getEnvironmentCount()];
                    for (int i = 0; i < result.length; i++) {
                        result[i] = (VirtualBoxEnvironment) specs.getEnvironment(i);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void initialize(ITestExecutionContext testExecutionContext) {
        this._testExecutionContext = testExecutionContext;
        List<VirtualBoxEnvironment> devices = Arrays.stream(this.getDevices())
                .sorted(Comparator.comparingInt(VirtualBoxEnvironment::getPriority)).collect(Collectors.toList());
        for (VirtualBoxEnvironment device : devices) {
            if (device.getState().equals("power_on")) {
                _virtualBoxManageClient.powerOff(device);
                device.setState("power_off");
                _repository.save((IEntity) device);
            }
        }
        for (VirtualBoxEnvironment device : devices) {
            _virtualBoxManageClient.snapshot(device);
        }
        for (VirtualBoxEnvironment device: devices) {
            if (device.getState().equals("power_off")) {
                _virtualBoxManageClient.powerOn(device);
                device.setState("power_on");
                _repository.save((IEntity) device);
            }
        }
    }

    @Override
    public void clear() {
        List<VirtualBoxEnvironment> devices = Arrays.stream(this.getDevices())
                .sorted(Comparator.comparingInt(VirtualBoxEnvironment::getPriority)).collect(Collectors.toList());
        for (VirtualBoxEnvironment device : devices) {
            if (device.getState().equals("power_on")) {
                _virtualBoxManageClient.powerOff(device);
                device.setState("power_off");
                _repository.save((IEntity) device);
            }
        }
    }
}
