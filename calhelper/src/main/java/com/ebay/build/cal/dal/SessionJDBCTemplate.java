package com.ebay.build.cal.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.ebay.build.cal.model.Session;

public class SessionJDBCTemplate {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public int create(final Session session) {
		final String SQL = "insert into RBT_SESSION (pool_name, machine_name, user_name, environment, " +
				"status, duration, maven_version, goals, start_time, git_url, git_branch, jekins_url, java_version) " +
				"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplateObject.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL,
						new String[] { "id" });
				ps.setString(1, session.getPool().getName());
				ps.setString(2, session.getPool().getMachine().getName());
				ps.setString(3, session.getUserName());
				ps.setString(4, session.getEnvironment());
				ps.setString(5, session.getStatus());
				ps.setLong(6, session.getDuration());
				ps.setString(7, session.getMavenVersion());
				ps.setString(8, session.getGoals());
				ps.setTimestamp(9, new java.sql.Timestamp(session.getStartTime().getTime()));
				ps.setString(10,  session.getGitUrl());
				ps.setString(11, session.getGitBranch());
				ps.setString(12, session.getJekinsUrl());
				ps.setString(13, session.getJavaVersion());

				return ps;
			}
		}, keyHolder);
		
		return keyHolder.getKey().intValue();
	}

	public Session getSession(Integer id) {
		String SQL = "select * from Session where id = ?";
		Session student = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { id }, new SessionMapper());
		return student;
	}

	public List<Session> listSessions() {
		String SQL = "select * from Session";
		List<Session> students = jdbcTemplateObject.query(SQL,
				new SessionMapper());
		return students;
	}

	public void delete(Integer id) {
		String SQL = "delete from Session where id = ?";
		jdbcTemplateObject.update(SQL, id);
		System.out.println("Deleted Record with ID = " + id);
		return;
	}
}