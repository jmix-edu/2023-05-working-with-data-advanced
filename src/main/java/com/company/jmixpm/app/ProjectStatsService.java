package com.company.jmixpm.app;

import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.ProjectStats;
import com.company.jmixpm.entity.Task;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlanBuilder;
import io.jmix.core.FetchPlans;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProjectStatsService {

    private DataManager dataManager;
    private final FetchPlans fetchPlans;

    public ProjectStatsService(DataManager dataManager, FetchPlans fetchPlans) {
        this.dataManager = dataManager;
        this.fetchPlans = fetchPlans;
    }

    public List<ProjectStats> fetchProjectStatistics() {
        List<Project> projects = dataManager.load(Project.class)
                .all()
//                .fetchPlan(createFetchPlanWithTasks())
                .fetchPlan("project-with-tasks")
                .list();

        return projects.stream()
                .map(project -> {
                    ProjectStats stats = dataManager.create(ProjectStats.class);
                    stats.setProjectName(project.getName());

                    List<Task> tasks = project.getTasks();
                    stats.setTaskCount(tasks.size());

                    Integer estimatedEfforts = tasks.stream()
                            .map(task-> task.getEstimatedEfforts() == null ? 0 : task.getEstimatedEfforts())
                            .reduce(0, Integer::sum);
                    stats.setPlannedEfforts(estimatedEfforts);
                    stats.setActualEfforts(getActualEfforts(project.getId()));
                    return stats;
                })
                .collect(Collectors.toList());
    }

    private FetchPlan createFetchPlanWithTasks() {
        FetchPlanBuilder taskFetchPlanBuilder = fetchPlans.builder(Task.class)
                .add("estimatedEfforts")
                .add("startDate");

        FetchPlanBuilder projectFetchPlanBuilder = fetchPlans.builder(Project.class)
                .addFetchPlan(FetchPlan.INSTANCE_NAME)
                .add("tasks", taskFetchPlanBuilder);

        return projectFetchPlanBuilder.build();
    }

    private Integer getActualEfforts(UUID projectId) {

        return dataManager.loadValue("select sum(te.timeSpent) from TimeEntry te " +
                        "where te.task.project.id = :projectId", Integer.class)
                .parameter("projectId", projectId)
                .one();
    }
}