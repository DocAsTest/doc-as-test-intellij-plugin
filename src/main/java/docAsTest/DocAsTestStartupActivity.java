package docAsTest;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// https://plugins.jetbrains.com/docs/intellij/plugin-components.html?from=jetbrains.org#project-open
// https://plugins.jetbrains.com/docs/intellij/ide-infrastructure.html#running-tasks-once

@Service(Service.Level.PROJECT)
public final class DocAsTestStartupActivity {

    private static final Logger LOG = Logger.getInstance(DocAsTestStartupActivity.class);
    private static final String DOC_AS_TEST_PROPERTIES_FILENAME = "docAsTest.properties";

    public static final String DEFAULT_SRC_PATH = "src/test/java";
    public static final String DEFAULT_SRC_DOCS = "src/test/docs";

    //public static String getSrcDocs() {
    //    return getProperty("DOC_PATH", DEFAULT_SRC_DOCS);
    //}

    public static String getSrcDocs(Project project) {
        DocAsTestStartupActivity projectService =
                project.getService(DocAsTestStartupActivity.class);
        return getProperty("DOC_PATH", DEFAULT_SRC_DOCS);
    }

    //public static String getSrcPath() {
    //    return getProperty("TEST_PATH", DEFAULT_SRC_PATH);
    //}

    public static String getSrcPath(Project project) {
        DocAsTestStartupActivity projectService =
                project.getService(DocAsTestStartupActivity.class);
        return getProperty("TEST_PATH", DEFAULT_SRC_PATH);
    }

    private static String getProperty(String key, String defaultValue) {
        return properties == null
                ? defaultValue
                : properties.getProperty(key, defaultValue);
    }

    public static void setProperties(Properties properties) {
        DocAsTestStartupActivity.properties = properties;
    }

    private static Properties properties = null;

    public static void reset() {
        properties = null;
    }

    DocAsTestStartupActivity(Project project) {
        loadProperties(project);
    }
   // @Override
    //public void runActivity(@NotNull Project project) {
    //    LOG.info("Initialize DocAsTest plugin on project:" + project.getName());
    //    loadProperties(project);
    //    LOG.info("Test path: " + getSrcPath());
    //    LOG.info("Doc path : " + getSrcDocs());
    //}

    public static void loadProperties(Project project) {
        LOG.debug("project: " + project.getName());

        final VirtualFile[] contentSourceRoots = ProjectRootManager.getInstance(project).getContentSourceRoots();
        properties = new Properties();
        for (VirtualFile contentSourceRoot : contentSourceRoots) {
            final VirtualFile fileByRelativePath = contentSourceRoot.findFileByRelativePath(DOC_AS_TEST_PROPERTIES_FILENAME);
            if(fileByRelativePath != null) {
                loadProperties(fileByRelativePath);
            }
        }
    }

    private static void loadProperties(VirtualFile virtualFile) {
        try (final InputStream inputStream = virtualFile.getInputStream()) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
