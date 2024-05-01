package example.cucumber.helpers;

import app.ProjectInfo;
import domain.Classes.Project;

public class ProjectHelper {

    private Project project;

    private ProjectInfo pI;
    public ProjectHelper () {

    }
    public void setProject(Project p) {

        this.project=p;
        this.pI=new ProjectInfo(p);
    }
    public Project getProject() {
        return this.project;
    }

    public ProjectInfo getProjectInfo(){return this.pI;}
}
