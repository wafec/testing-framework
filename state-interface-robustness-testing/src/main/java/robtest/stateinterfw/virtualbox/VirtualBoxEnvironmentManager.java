package robtest.stateinterfw.virtualbox;

import robtest.stateinterfw.IEnvironmentManager;
import robtest.stateinterfw.ITestExecutionContext;
import robtest.stateinterfw.data.IEntity;
import robtest.stateinterfw.data.IRepository;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class VirtualBoxEnvironmentManager implements IEnvironmentManager {
    private VirtualBoxEnvironment[] _devices;
    private IVirtualBoxManageClient _virtualBoxManageClient;
    private IRepository _repository;

    public VirtualBoxEnvironmentManager(VirtualBoxEnvironment[] devices, IVirtualBoxManageClient virtualBoxManageClient,
                                        IRepository repository) {
        this._devices = devices;
        this._virtualBoxManageClient = virtualBoxManageClient;
        this._repository = repository;
    }

    @Override
    public void initialize(ITestExecutionContext testExecutionContext) {
        List<VirtualBoxEnvironment> devices = Arrays.stream(_devices)
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
        List<VirtualBoxEnvironment> devices = Arrays.stream(_devices)
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
