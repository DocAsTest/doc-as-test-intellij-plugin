import com.intellij.psi.PsiFile;
import docAsTest.DocAsTestFilenameIndex;
import docAsTest.DocAsTestStartupActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tools.DocAsTestPlatformTestCase;

@RunWith(JUnit4.class)
public class DocAsTestStartupActivityTest extends DocAsTestPlatformTestCase {

    public void setUp() throws Exception {
        DocAsTestFilenameIndex.setSlowOperationPolicy(DocAsTestPlatformTestCase.NO_SLOW_OPERATION_POLICY);
        super.setUp();
        DocAsTestStartupActivity.reset();
    }

    @Test
    public void test_src_docs_has_default_value_when_no_property_file() throws Exception {
        //new DocAsTestStartupActivity().runActivity(myFixture.getProject());
        assertEquals(DocAsTestStartupActivity.DEFAULT_SRC_DOCS, DocAsTestStartupActivity.getSrcDocs(this.getProject()));
        assertEquals(DocAsTestStartupActivity.DEFAULT_SRC_PATH, DocAsTestStartupActivity.getSrcPath(this.getProject()));
    }
    @Test
    public void test_src_docs_has_default_value_when_property_is_not_yet_created() throws Exception {
        //new DocAsTestStartupActivity().runActivity(myFixture.getProject());
        DocAsTestStartupActivity.setProperties(null);
        assertEquals(DocAsTestStartupActivity.DEFAULT_SRC_DOCS, DocAsTestStartupActivity.getSrcDocs(this.getProject()));
        assertEquals(DocAsTestStartupActivity.DEFAULT_SRC_PATH, DocAsTestStartupActivity.getSrcPath(this.getProject()));
    }

    @Test
    public void test_load_property_file_on_startup() throws Exception {
        final PsiFile propertyFile = myFixture.addFileToProject("docAsTest.properties",
                String.join("\n",
                        "DOC_PATH:src/doc/custom_folder",
                        "TEST_PATH:src/code/test_folder"));
        DocAsTestStartupActivity.loadProperties(myFixture.getProject());
        assertEquals("src/doc/custom_folder", DocAsTestStartupActivity.getSrcDocs(this.getProject()));
        assertEquals("src/code/test_folder", DocAsTestStartupActivity.getSrcPath(this.getProject()));
    }
}
