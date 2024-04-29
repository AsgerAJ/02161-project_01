package app;

import domain.Project;

public class ProjectInfo {

    public String projectId;

    public ProjectInfo(Project project){
        this.projectId = project.getProjectID();
    }
}
