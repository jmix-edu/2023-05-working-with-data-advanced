package com.company.jmixpm.screen.project;

import com.company.jmixpm.app.ProjectsService;
import com.company.jmixpm.datatype.ProjectLabels;
import com.company.jmixpm.entity.Project;
import com.company.jmixpm.screen.user.UserBrowse;
import io.jmix.audit.snapshot.EntitySnapshotManager;
import io.jmix.core.validation.group.UiComponentChecks;
import io.jmix.core.validation.group.UiCrossFieldChecks;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Field;
import io.jmix.ui.component.Window;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Set;

@UiController("Project.edit")
@UiDescriptor("project-edit.xml")
@EditedEntityContainer("projectDc")
public class ProjectEdit extends StandardEditor<Project> {

    @Autowired
    private Field<ProjectLabels> labelsField;

    @Autowired
    private ProjectsService projectsService;
    @Autowired
    private Notifications notifications;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Project> event) {
        labelsField.setEditable(true);

        event.getEntity().setLabels(new ProjectLabels(List.of("bug", "enhancement", "task")));
    }

    @Subscribe
    public void onInit(InitEvent event) {
//        setCrossFieldValidate(false);
    }

    @Subscribe("commitWithBeanValidationBtn")
    public void onCommitWithBeanValidationClick(Button.ClickEvent event) {
        try {
            projectsService.saveProject(getEditedEntity());
            close(new StandardCloseAction(Window.COMMIT_ACTION_ID, false));
        } catch (ConstraintViolationException e) {
            StringBuilder sb = new StringBuilder();

            for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
                sb.append(constraintViolation.getMessage()).append("\n");
            }

            notifications.create()
                    .withCaption(sb.toString())
                    .show();
        }
    }

    @Install(to = "usersTable.add", subject = "screenConfigurer")
    private void usersTableAddScreenConfigurer(Screen screen) {
        ((UserBrowse) screen).setFilterProject(getEditedEntity());
    }

    @Autowired
    private Validator validator;

    @Subscribe("performBeanValidationBtn")
    public void onPerformBeanValidationBtnClick(Button.ClickEvent event) {
        Set<ConstraintViolation<Project>> violations = validator.validate(getEditedEntity(),
                Default.class, UiCrossFieldChecks.class, UiComponentChecks.class);

        showBeanValidationExceptions(violations);
    }

    private void showBeanValidationExceptions(Set<ConstraintViolation<Project>> violations) {
        StringBuilder sb = new StringBuilder();

        for (ConstraintViolation<?> constraintViolation : violations) {
            sb.append(constraintViolation.getMessage()).append("\n");
        }

        notifications.create()
                .withCaption(sb.toString())
                .show();
    }

    @Autowired
    private EntitySnapshotManager entitySnapshotManager;

    @Subscribe
    public void onAfterCommitChanges(AfterCommitChangesEvent event) {
        entitySnapshotManager.createSnapshot(getEditedEntity(), getEditedEntityContainer().getFetchPlan());
    }


}