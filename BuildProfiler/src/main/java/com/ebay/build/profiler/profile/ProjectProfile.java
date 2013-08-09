package com.ebay.build.profiler.profile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.maven.eventspy.EventSpy.Context;
import org.apache.maven.execution.ExecutionEvent;
import org.apache.maven.project.MavenProject;

import com.ebay.build.profiler.model.Project;
import com.ebay.build.profiler.readers.ProcessHelper;
import com.ebay.build.profiler.util.Timer;

public class ProjectProfile extends Profile {

	private MavenProject project;
	private List<PhaseProfile> phaseProfiles;
	private String status;
	private String message;

	private String projectGroupId;
	private String projectArtifactId;
	private String projectVersion;
	
	private String projectName = "N/A";
	private String projectId = "N/A";
	
	Project p = new Project();

	public ProjectProfile(Context c, MavenProject project, ExecutionEvent event) {
		super(new Timer(), event, c);
		this.project = project;
		this.phaseProfiles = new ArrayList<PhaseProfile>();
		this.projectGroupId = project.getGroupId();
		this.projectArtifactId = project.getArtifactId();
		this.projectVersion = project.getVersion();
		if (event != null) {
			projectName = event.getProject().getName();
			projectId = event.getProject().getId();
		}

		
		
		if (getSession() != null) {
			p.setName(projectName);
			p.setPayload(projectId);
			p.setStartTime(new Date(this.getTimer().getStartTime()));
			
			ProcessHelper.praseProjectPayload(projectId, p);

			getSession().getProjects().put(projectName, p);
			getSession().setCurrentProject(p);
		}
	}
	
	public String getProjectGroupId() {
		return projectGroupId;
	}

	public String getProjectArtifactId() {
		return projectArtifactId;
	}

	public String getProjectVersion() {
		return projectVersion;
	}

	public void addPhaseProfile(PhaseProfile phaseProfile) {
		phaseProfiles.add(phaseProfile);
	}

	public List<PhaseProfile> getPhaseProfile() {
		return phaseProfiles;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getProjectName() {
		return new StringBuilder().append(projectGroupId).append(":")
				.append(projectArtifactId).append(":")
				.append(project.getVersion()).toString();
	}

	@Override
	public void stop() {
		super.stop();

		String status = endTransaction();
		
		p.setDuration(this.getElapsedTime());
		p.setStatus(status);
	}
}