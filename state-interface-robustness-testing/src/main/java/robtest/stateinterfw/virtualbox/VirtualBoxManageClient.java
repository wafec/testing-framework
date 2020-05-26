package robtest.stateinterfw.virtualbox;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VirtualBoxManageClient implements IVirtualBoxManageClient {

    public VirtualBoxManageClient() {

    }

    private void call(String... arg) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(arg);
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!StringUtils.isEmpty(line))
                    System.out.println(line);
            }
            int exitCode = process.waitFor();
            if (exitCode != 0)
                System.out.println("\nExited with error code: " + exitCode);
        } catch (IOException | InterruptedException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void powerOff(IVirtualBoxEnvironment virtualBoxEnvironment) {
        String command = String.join(" ","VBoxManage", "controlvm", virtualBoxEnvironment.getName(), "poweroff");
        System.out.println(command);
        call("cmd", "/c", command);
    }

    @Override
    public void snapshot(IVirtualBoxEnvironment virtualBoxEnvironment) {
        String command = String.join(" ", "VBoxManage", "snapshot", virtualBoxEnvironment.getName(), "restore", virtualBoxEnvironment.getSnapshot());
        System.out.println(command);
        call("cmd", "/c", command);
    }

    @Override
    public void powerOn(IVirtualBoxEnvironment virtualBoxEnvironment) {
        String command = String.join(" ", "VBoxManage", "startvm", virtualBoxEnvironment.getName());
        System.out.println(command);
        call("cmd", "/c", command);
    }
}
