package robtest.stateinterfw.examples.openStack;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.core.transport.Config;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.common.payloads.FilePayload;
import org.openstack4j.model.image.v2.ContainerFormat;
import org.openstack4j.model.image.v2.DiskFormat;
import org.openstack4j.model.image.v2.Image;
import org.openstack4j.openstack.OSFactory;
import robtest.stateinterfw.*;
import robtest.stateinterfw.examples.openStack.content.OpenStackUserContent;

import java.io.File;
import java.util.Collections;

public class OpenStackTestInputCommand extends TestInputCommand implements IOpenStackTestInputCommand {
    public OpenStackTestInputCommand() {
        add("identity.login", this::login);
        add("compute.flavor.create", this::flavorCreate);
        add("image.create", this::imageCreate);
        add("compute.server.create", this::serverCreate);
        add("compute.server.delete", this::serverDelete);
        add("compute.flavor.delete", this::flavorDelete);
        add("image.delete", this::imageDelete);
    }

    private OpenStackUserContent getUserContent() {
        if (this.testExecutionContext != null) {
            if (this.testExecutionContext.getVolatileUserContent() != null &&
                this.testExecutionContext.getVolatileUserContent() instanceof OpenStackUserContent) {
                return (OpenStackUserContent) this.testExecutionContext.getVolatileUserContent();
            } else if (this.testExecutionContext.getVolatileUserContent() != null) {
                throw new IllegalArgumentException("User content is not an instance of OpenStackUserContent");
            } else {
                var userContent = new OpenStackUserContent();
                this.testExecutionContext.setVolatileUserContent(userContent);
                return userContent;
            }
        }
        return null;
    }

    private OSClientV3 createClient() {
        var userContent = getUserContent();
        if (userContent != null) {
            Identifier domainIdentifier = Identifier.byName(userContent.getLogin().getDomain());
            Identifier projectIdentifier = Identifier.byName(userContent.getLogin().getProject());
            OSClientV3 client = OSFactory.builderV3()
                    .endpoint(userContent.getLogin().getEndpoint())
                    .credentials(userContent.getLogin().getUser(), userContent.getLogin().getPassword(), domainIdentifier)
                    .withConfig(Config.newConfig().withEndpointNATResolution("controller"))
                    .authenticate();
            return client;
        }
        return null;
    }

    private Object login(ITestInputArgs args) {
        var userContent = getUserContent();
        if (userContent != null) {
            String user = args.get("user").getDataValue();
            String password = args.get("password").getDataValue();
            String domain = args.get("domain").getDataValue();
            String endpoint = args.get("endpoint").getDataValue();
            String project = args.get("project").getDataValue();
            userContent.getLogin().setUser(user);
            userContent.getLogin().setPassword(password);
            userContent.getLogin().setDomain(domain);
            userContent.getLogin().setEndpoint(endpoint);
            userContent.getLogin().setProject(project);
        }
        return null;
    }

    private Object flavorCreate(ITestInputArgs args) {
        var client = createClient();
        Object result = null;
        if (client != null) {
            var flavor = Builders.flavor()
                    .name(args.get("name").getDataValue())
                    .vcpus(Integer.parseInt(args.get("vcpus").getDataValue()))
                    .ram(Integer.parseInt(args.get("ram").getDataValue()))
                    .disk(1)
                    .build();
            var flavorResult = client.compute().flavors().create("test", 100, 5, 1, 0, 0, 1.2f, true);
            result = flavorResult;
        }
        return result;
    }

    private Object imageCreate(ITestInputArgs args) {
        var client = createClient();
        Object result = null;
        if (client != null) {
            var image = Builders.imageV2()
                    .name(args.get("name").getDataValue())
                    .diskFormat(DiskFormat.valueOf(args.get("diskFormat").getDataValue()))
                    .containerFormat(ContainerFormat.valueOf(args.get("containerFormat").getDataValue()))
                    .visibility(Image.ImageVisibility.valueOf(args.get("visibility").getDataValue()))
                    .minDisk(Long.parseLong(args.get("minDisk").getDataValue()))
                    .minRam(Long.parseLong(args.get("minRam").getDataValue()))
                    .build();
            var payload = new FilePayload(new File(args.get("file").getDataValue()));
            var resultImage = client.imagesV2().create(image);
            client.imagesV2().upload(resultImage.getId(), payload, null);
        }
        return result;
    }

    private Object serverCreate(ITestInputArgs args) {
        var client = createClient();
        Object result = null;
        if (client != null) {
            var image = client.imagesV2().list(Collections.singletonMap("name", args.get("image").getDataValue())).get(0);
            var flavor = client.compute().flavors().list(Collections.singletonMap("name", args.get("flavor").getDataValue())).get(0);
            var serverCreate = Builders.server()
                    .name(args.get("name").getDataValue())
                    .image(image.getId())
                    .flavor(flavor.getId())
                    .build();
            result = client.compute().servers().boot(serverCreate);
        }
        return result;
    }

    private Object serverDelete(ITestInputArgs args) {
        var client = createClient();
        Object result = null;
        if (client != null) {
            var server = client.compute().servers().list(Collections.singletonMap("name", args.get("name").getDataValue())).get(0);
            client.compute().servers().delete(server.getId());
        }
        return result;
    }

    private Object imageDelete(ITestInputArgs arg) {
        var client = createClient();
        Object result = null;
        if (client != null) {
            var imageOpt = client.imagesV2().list().stream().filter(i -> i.getName().equals(arg.get("name"))).findFirst();
            if (imageOpt.isPresent()) {
                var image = imageOpt.get();
                client.imagesV2().delete(image.getId());
                result = image;
            }
        }
        return result;
    }

    private Object flavorDelete(ITestInputArgs arg) {
        var client = createClient();
        Object result = null;
        if (client != null) {
            var flavorOpt = client.compute().flavors().list().stream().filter(f -> f.getName().equals(arg.get("name"))).findFirst();
            if (flavorOpt.isPresent()) {
                var flavor = flavorOpt.get();
                client.compute().flavors().delete(flavor.getId());
                result = flavor;
            }
        }
        return result;
    }
}
