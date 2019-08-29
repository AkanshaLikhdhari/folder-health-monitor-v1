package folder.health.monitor.services;

import java.util.List;

public interface MonitorFolder {
   public void monitor(String source, String destination, List<String> fileExtension,long max_size);
}
