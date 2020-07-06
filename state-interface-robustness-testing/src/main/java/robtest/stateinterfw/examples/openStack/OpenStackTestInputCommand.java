package robtest.stateinterfw.examples.openStack;

import com.google.inject.Inject;
import robtest.stateinterfw.*;
import robtest.stateinterfw.data.IRepository;
import robtest.stateinterfw.data.openStack.OSTest;
import robtest.stateinterfw.examples.openStack.content.OpenStackUserContent;
import robtest.stateinterfw.examples.openStack.content.OpenStackUserResource;
import robtest.stateinterfw.examples.openStack.content.ResourceTypeEnum;
import robtest.stateinterfw.openStack.cli.FlavorClient;
import robtest.stateinterfw.openStack.cli.ImageClient;
import robtest.stateinterfw.openStack.cli.ServerClient;
import robtest.stateinterfw.openStack.cli.VolumeClient;

import java.io.File;

public class OpenStackTestInputCommand extends TestInputCommand implements IOpenStackTestInputCommand {
    private IRepository _repository;
    private OpenStackTestExecutionContextWrapper contextWrapper;

    @Inject
    public OpenStackTestInputCommand(IRepository repository) {
        this._repository = repository;
        this.configure();
    }

    @Override
    public Object command(ITestExecutionContext testExecutionContext, ITestInput testInput) {
        contextWrapper = new OpenStackTestExecutionContextWrapper(testExecutionContext);
        return super.command(testExecutionContext, testInput);
    }

    private void configure() {
        add("identity.login", this::login);
        add("compute.flavor.create", this::flavorCreate);
        add("image.create", this::imageCreate);
        add("compute.server.create", this::serverCreate);
        add("compute.server.delete", this::serverDelete);
        add("compute.flavor.delete", this::flavorDelete);
        add("image.delete", this::imageDelete);
        add("compute.server.pause", this::serverPause);
        add("compute.server.unpause", this::serverUnpause);
        add("compute.server.shelve", this::serverShelve);
        add("compute.server.unshelve", this::serverUnshelve);
        add("compute.server.start", this::serverStart);
        add("compute.server.stop", this::serverStop);
        add("compute.server.suspend", this::serverSuspend);
        add("compute.server.resume", this::serverResume);
        add("compute.server.resize", this::serverResize);
        add("compute.server.confirmresize", this::serverConfirmResize);
        add("compute.server.revertresize", this::serverRevertResize);
        add("storage.volume.create", this::volumeCreate);
        add("storage.volume.extend", this::volumeExtend);
        add("storage.volume.delete", this::volumeDelete);
        add("storage.volume.detach", this::volumeDetach);
        add("storage.volume.attach", this::volumeAttach);
    }

    private OpenStackUserContent getUserContent() {
        return contextWrapper.getUserContent();
    }

    private Object login(ITestInputArgs args) {
        OSTest testConfig = new OSTest();
        testConfig.setUsername(args.get("username").getDataValue());
        testConfig.setPassword(args.get("password").getDataValue());
        testConfig.setAuthUrl(args.get("auth_url").getDataValue());
        testConfig.setProjectDomainName(args.get("project_domain_name").getDataValue());
        testConfig.setUserDomainName(args.get("user_domain_name").getDataValue());
        _repository.save(testConfig);
        getUserContent().setId(testConfig.getId());
        return testConfig;
    }

    private Object flavorCreate(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        FlavorClient flavorClient = new FlavorClient();
        var result = flavorClient.createFlavor(userContent.getId(), args.get("name").getDataValue(), Integer.parseInt(args.get("ram").getDataValue()),
                Integer.parseInt(args.get("vcpus").getDataValue()), Integer.parseInt(args.get("disk").getDataValue()));
        OpenStackUserResource resource = new OpenStackUserResource();
        resource.setUid(result.getId());
        resource.setName(result.getName());
        resource.setResourceType(ResourceTypeEnum.FLAVOR);
        userContent.addResource(resource);
        return result;
    }

    private Object imageCreate(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        ImageClient imageClient = new ImageClient();
        var result = imageClient.imageCreate(userContent.getId(), args.get("name").getDataValue(),
                args.get("disk_format").getDataValue(), args.get("container_format").getDataValue(),
                new File(args.get("file").getDataValue()));
        OpenStackUserResource resource = new OpenStackUserResource();
        resource.setUid(result.getId());
        resource.setName(result.getName());
        resource.setResourceType(ResourceTypeEnum.IMAGE);
        userContent.addResource(resource);
        return result;
    }

    private Object serverCreate(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        ServerClient serverClient = new ServerClient();
        var result = serverClient.createServer(userContent.getId(), args.get("name").getDataValue(),
                args.get("image").getDataValue(), args.get("flavor").getDataValue(), args.get("network").getDataValue());
        OpenStackUserResource resource = new OpenStackUserResource();
        resource.setUid(result.getId());
        resource.setName(result.getName());
        resource.setResourceType(ResourceTypeEnum.SERVER);
        userContent.addResource(resource);
        return result;
    }

    private Object serverDelete(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        ServerClient serverClient = new ServerClient();
        serverClient.serverDelete(userContent.getId(), args.get("name").getDataValue());
        return null;
    }

    private Object imageDelete(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        ImageClient imageClient = new ImageClient();
        imageClient.imageDelete(userContent.getId(), args.get("name").getDataValue());
        return null;
    }

    private Object flavorDelete(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        FlavorClient flavorClient = new FlavorClient();
        flavorClient.deleteFlavor(userContent.getId(), args.get("name").getDataValue());
        return null;
    }

    private Object serverPause(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        ServerClient serverClient = new ServerClient();
        serverClient.serverPause(userContent.getId(), args.get("name").getDataValue());
        return null;
    }

    private Object serverUnpause(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        ServerClient serverClient = new ServerClient();
        serverClient.serverUnpause(userContent.getId(), args.get("name").getDataValue());
        return null;
    }

    private Object serverShelve(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        ServerClient serverClient = new ServerClient();
        serverClient.serverShelveOffload(userContent.getId(), args.get("name").getDataValue());
        return null;
    }

    private Object serverUnshelve(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        ServerClient serverClient = new ServerClient();
        serverClient.serverUnshelve(userContent.getId(), args.get("name").getDataValue());
        return null;
    }

    private Object serverStart(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        ServerClient serverClient = new ServerClient();
        serverClient.serverStart(userContent.getId(), args.get("name").getDataValue());
        return null;
    }

    private Object serverStop(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        ServerClient serverClient = new ServerClient();
        serverClient.serverStop(userContent.getId(), args.get("name").getDataValue());
        return null;
    }

    private Object serverMigrate(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        ServerClient serverClient = new ServerClient();
        serverClient.serverMigrate(userContent.getId(), args.get("name").getDataValue());
        return null;
    }

    private Object serverSuspend(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        ServerClient serverClient = new ServerClient();
        serverClient.serverSuspend(userContent.getId(), args.get("name").getDataValue());
        return null;
    }

    private Object serverResume(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        ServerClient serverClient = new ServerClient();
        serverClient.serverResume(userContent.getId(), args.get("name").getDataValue());
        return null;
    }

    private Object serverResize(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        ServerClient serverClient = new ServerClient();
        serverClient.serverResize(userContent.getId(), args.get("name").getDataValue(),
                args.get("flavor").getDataValue());
        return null;
    }

    private Object serverConfirmResize(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        ServerClient serverClient = new ServerClient();
        serverClient.serverConfirmOrRejectResize(userContent.getId(), args.get("name").getDataValue(), true);
        return null;
    }

    private Object serverRevertResize(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        ServerClient serverClient = new ServerClient();
        serverClient.serverConfirmOrRejectResize(userContent.getId(), args.get("name").getDataValue(), false);
        return null;
    }

    private Object volumeCreate(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        VolumeClient volumeClient = new VolumeClient();
        var result = volumeClient.volumeCreate(userContent.getId(), args.get("name").getDataValue(),
                args.get("availabilityZone").getDataValue(), Integer.parseInt(args.get("size").getDataValue()));
        OpenStackUserResource resource = new OpenStackUserResource();
        resource.setUid(result.getId());
        resource.setName(result.getName());
        resource.setResourceType(ResourceTypeEnum.VOLUME);
        userContent.addResource(resource);
        return result;
    }

    private Object volumeExtend(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        VolumeClient volumeClient = new VolumeClient();
        volumeClient.volumeExtend(userContent.getId(), args.get("name").getDataValue(),
                Integer.parseInt(args.get("size").getDataValue()));
        return null;
    }

    private Object volumeAttach(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        VolumeClient volumeClient = new VolumeClient();
        volumeClient.volumeAttach(userContent.getId(), args.get("name").getDataValue(),
                args.get("server").getDataValue(), args.get("mountpoint").getDataValue());
        return null;
    }

    private Object volumeDetach(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        VolumeClient volumeClient = new VolumeClient();
        volumeClient.volumeDetach(userContent.getId(), args.get("name").getDataValue());
        return null;
    }

    private Object volumeDelete(ITestInputArgs args) {
        OpenStackUserContent userContent = getUserContent();
        VolumeClient volumeClient = new VolumeClient();
        volumeClient.volumeDelete(userContent.getId(), args.get("name").getDataValue());
        return null;
    }
}
