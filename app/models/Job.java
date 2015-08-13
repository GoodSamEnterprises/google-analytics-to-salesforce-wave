package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import models.dao.GoogleAnalyticsProfileDAO;
import models.dao.SalesforceAnalyticsProfileDAO;

import org.hibernate.validator.constraints.NotEmpty;

import play.libs.Json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.ga2sa.utils.JsonUtil;


/**
 * The persistent class for the jobs database table.
 * @author Igor Ivarov
 * @editor Sergey Legostaev
 */
@Entity
@Table(name="jobs")
@NamedQuery(name="Job.findAll", query="SELECT j FROM Job j ORDER BY j.created DESC")
public class Job extends BaseEntity {
	private static final long serialVersionUID = 1L;

//	@Id
//	@SequenceGenerator(name="JOBS_ID_GENERATOR", sequenceName="JOBS_ID_SEQ")
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="JOBS_ID_GENERATOR")
//	private Long id;

	@Column(name="end_time")
	private Timestamp endTime;
	
	@NotEmpty
	@NotNull
	private String name;
	
	private String errors;
	
	@NotEmpty
	@NotNull
	@Column(name="google_analytics_properties")
	private String googleAnalyticsProperties;
	
	@Column(name="start_time")
	private Timestamp startTime;
	
	@Column(name="next_start_time")
	private Timestamp nextStartTime;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private JobStatus status;
	
	@Column(name="repeat_period")
	private String repeatPeriod;
	
	@Column(name="include_previous_data")
	private Boolean includePreviousData;

	@NotNull
	@ManyToOne
	@JoinColumn(name="google_analytics_profile_id")
	private GoogleAnalyticsProfile googleAnalyticsProfile;

	@NotNull
	@ManyToOne
	@JoinColumn(name="salesforce_analytics_profile_id")
	private SalesforceAnalyticsProfile salesforceAnalyticsProfile;

	@NotNull
	@ManyToOne
	@JoinColumn(name="executed_by")
	private User user;

	public Job() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getErrors() {
		return this.errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	public String getGoogleAnalyticsProperties() {
		return this.googleAnalyticsProperties;
	}

	public void setGoogleAnalyticsProperties(String googleAnalyticsProperties) {
		this.googleAnalyticsProperties = googleAnalyticsProperties;
	}

	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getNextStartTime() {
		return nextStartTime;
	}

	public void setNextStartTime(Timestamp nextStartTime) {
		this.nextStartTime = nextStartTime;
	}

	public JobStatus getStatus() {
		return this.status;
	}

	public void setStatus(JobStatus status) {
		this.status = status;
	}

	public String getRepeatPeriod() {
		return repeatPeriod;
	}

	public void setRepeatPeriod(String repeatPeriod) {
		this.repeatPeriod = repeatPeriod;
	}
	
	public Boolean getIncludePreviousData() {
		return includePreviousData;
	}

	public void setIncludePreviousData(Boolean includePreviousData) {
		this.includePreviousData = includePreviousData;
	}

	public GoogleAnalyticsProfile getGoogleAnalyticsProfile() {
		return this.googleAnalyticsProfile;
	}

	public void setGoogleAnalyticsProfile(GoogleAnalyticsProfile googleAnalyticsProfile) {
		this.googleAnalyticsProfile = googleAnalyticsProfile;
	}

	public SalesforceAnalyticsProfile getSalesforceAnalyticsProfile() {
		return this.salesforceAnalyticsProfile;
	}

	public void setSalesforceAnalyticsProfile(SalesforceAnalyticsProfile salesforceAnalyticsProfile) {
		this.salesforceAnalyticsProfile = salesforceAnalyticsProfile;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Boolean isRepeated() {
		return this.getRepeatPeriod() != null;
	}
	
	public Boolean needIncludePreviousData() {
		return this.getIncludePreviousData();
	}
	
	@JsonProperty(value="googleAnalyticsProfile")
	public JsonNode getGoogleProfileId() {
		return JsonUtil.excludeFields(Json.toJson(this.googleAnalyticsProfile), GoogleAnalyticsProfileDAO.privateFields);
	}
	
	@JsonProperty(value="salesforceAnalyticsProfile")
	public JsonNode getSalesforceAnalyticsProfileId() {
		return JsonUtil.excludeFields(Json.toJson(this.salesforceAnalyticsProfile), SalesforceAnalyticsProfileDAO.privateFields);
	}
	
	@JsonProperty(value="user")
	public String getUserId() {
		return this.user.username;
	}

}