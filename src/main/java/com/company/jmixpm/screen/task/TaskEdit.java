package com.company.jmixpm.screen.task;

import com.company.jmixpm.entity.Project;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Task_.edit")
@UiDescriptor("task-edit.xml")
@EditedEntityContainer("taskDc")
public class TaskEdit extends StandardEditor<Task> {
    @Autowired
    private ComboBox<String> labelField;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        Project project = getEditedEntity().getProject();
        if (project != null && project.getLabels() != null) {
            labelField.setOptionsList(project.getLabels().getLabels());
        }
    }
}