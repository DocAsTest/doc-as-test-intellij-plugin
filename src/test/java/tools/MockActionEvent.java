package tools;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import org.mockito.Mockito;

public class MockActionEvent extends AnActionEvent {

    public MockActionEvent(Project project) {
        super(MockActionEvent.buildDataContext(project),
                new Presentation(),
                "Here",
                ActionUiKind.NONE,
                null,
                0,
                Mockito.mock(ActionManager.class));
    }

    private static DataContext buildDataContext(Project project) {
        DataContext context = Mockito.mock(DataContext.class);
        Mockito.when(context.getData(CommonDataKeys.PROJECT)).thenReturn(project);
        return context;
    }

}
