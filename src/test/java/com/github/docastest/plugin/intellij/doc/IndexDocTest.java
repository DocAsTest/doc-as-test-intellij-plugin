package com.github.docastest.plugin.intellij.doc;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.github.docastest.docformatter.asciidoc.AsciidocFormatter;
import com.github.docastest.doctesting.junitextension.HtmlPageExtension;
import com.github.docastest.doctesting.utils.Config;
import com.github.docastest.doctesting.utils.DocPath;
import com.github.docastest.doctesting.utils.NoTitle;
import com.github.docastest.doctesting.writer.DocWriter;
import com.github.docastest.plugin.intellij.tools.ApprovalsJUnit4;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@DisplayName("DocAsTest plugin")
@RunWith(JUnit4.class)
public class IndexDocTest extends ApprovalsJUnit4 {

    AsciidocFormatter formatter = new AsciidocFormatter();

    @AfterClass
    public static void tearDown() throws IOException {
        new HtmlPage().generate(IndexDocTest.class);
    }

    @Test
    @NoTitle
    public void test_content() {
        write(
                "include::../../../../../README.adoc[tag=description]",
                ""
        );
        write(formatter.include(approvedDocPathStringFrom(ShortCutTest.class, this.getClass())));
    }

    private String approvedDocPathStringFrom(Class<?> targetClass, Class<?> fromClass) {
        return DocPath.toAsciiDoc(new DocPath(targetClass).approved().from(fromClass));
    }

}

class HtmlPage extends HtmlPageExtension {
    @Override
    public String content(Class<?> clazz) {
        return String.join("\n",
                new DocWriter<AsciidocFormatter>(new AsciidocFormatter()).defineDocPath(Paths.get(".")),
                ":nofooter:",
                super.content(clazz));
    }

    @Override
    public Path getFilePath(Class<?> clazz) {
        return Config.DOC_PATH.resolve("index.adoc");
    }
}
