package com.company.jmixpm.screen.sandbox;

import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.Task;
import io.jmix.core.DataManager;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("SandboxScreen")
@UiDescriptor("sandbox-screen.xml")
public class SandboxScreen extends Screen {
    @Autowired
    private DataManager dataManager;

    @Subscribe
    public void onInit(InitEvent event) {
        Task task = dataManager.load(Task.class)
                .all()
                .one();

        Project project = task.getProject();
        String name = project.getName();
        
    }


}