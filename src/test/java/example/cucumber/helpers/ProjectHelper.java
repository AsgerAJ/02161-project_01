package example.cucumber.helpers;

import domain.Project;

public class ProjectHelper {

    private Project project;
    public ProjectHelper () {

    }
    public void setProject(Project p) {
        this.project=p;
    }
    public Project getProject() {
        return this.project;
    }
}
