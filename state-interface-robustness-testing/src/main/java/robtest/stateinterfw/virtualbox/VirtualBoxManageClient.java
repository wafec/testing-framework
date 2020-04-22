package robtest.stateinterfw.virtualbox;

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
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            System.out.println("\nExited with error code: " + exitCode);
        } catch (IOException exc) {
            exc.printStackTrace();
        } catch (InterruptedException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void powerOff(VirtualBoxEnvironment virtualBoxEnvironment) {
        call("cmd", "/c", "VBoxManage", "controlvm", virtualBoxEnvironment.getName(), "poweroff");
    }

    @Override
    public void snapshot(VirtualBoxEnvironment virtualBoxEnvironment) {
        call("cmd", "/c", "VBoxManage", "snapshot", virtualBoxEnvironment.getName(), "restore", virtualBoxEnvironment.getSnapshot());
    }

    @Override
    public void powerOn(VirtualBoxEnvironment virtualBoxEnvironment) {
        call("cmd", "/c", "VBoxManage", "startvm", virtualBoxEnvironment.getName(), "--type", "headless");
    }
}
