package folder.health.monitor;

import folder.health.monitor.utilities.FolderUtility;
import org.junit.Test;

public class TestFolderUtility {

    @Test
    public void testIfFolderExistOrCreateFolderSuccess(){

        assert FolderUtility.ifFolderExistOrCreateFolderSuccess("akansha");

    }
}
