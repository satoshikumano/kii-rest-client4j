package com.kii.cloud.resource;

import java.util.Map;

import com.kii.cloud.model.KiiCredentialsContainer;
import com.kii.cloud.model.KiiGroup;
import com.kii.cloud.model.KiiUser;

public class KiiAppResource extends KiiRestResource {
	public static final String BASE_PATH = "/apps";
	
	private final String appID;
	private final String appKey;
	private final String endpoint;
	private final KiiCredentialsContainer credentials;
	
	public KiiAppResource(String appID, String appKey, String endpoint, KiiCredentialsContainer credentials) {
		this.appID = appID;
		this.appKey = appKey;
		this.endpoint = endpoint;
		this.credentials = credentials;
	}
	public KiiOAuthResource oauth() {
		return new KiiOAuthResource(this);
	}
	public KiiUsersResource users() {
		return new KiiUsersResource(this);
	}
	public KiiUserResource users(String identifier) {
		return new KiiUserResource(this.users(), identifier);
	}
	public KiiUserResource users(KiiUser user) {
		return new KiiUserResource(this.users(), user.getUserID());
	}
	public KiiGroupsResource groups() {
		return new KiiGroupsResource(this);
	}
	public KiiGroupResource groups(KiiGroup group) {
		return groups(group.getGroupID());
	}
	public KiiGroupResource groups(String groupID) {
		return new KiiGroupResource(this.groups(), groupID);
	}
	public KiiBucketResource buckets(String name) {
		return new KiiBucketResource(this, name);
	}
	public KiiEncryptedBucketResource encryptedBuckets(String name) {
		return new KiiEncryptedBucketResource(this, name);
	}
	public KiiTopicResource topics(String name) {
		return new KiiTopicResource(this, name);
	}
	public KiiScopeAclResource acl() {
		return new KiiScopeAclResource(this);
	}
	public KiiServerCodeResource servercode() {
		return new KiiServerCodeResource(this);
	}
	public KiiServerCodeResource servercode(String version) {
		return new KiiServerCodeResource(this, version);
	}
	@Override
	public String getPath() {
		return this.endpoint + BASE_PATH + "/" + this.appID;
	}
	@Override
	protected KiiRestResource getParent() {
		// This class is root resource
		return null;
	}
	@Override
	protected void setAppHeader(Map<String, String> headers) {
		headers.put("X-Kii-AppID", this.appID);
		headers.put("X-Kii-AppKey", this.appKey);
	}
	@Override
	protected void setAuthorizationHeader(Map<String, String> headers) {
		if (this.credentials != null) {
			headers.put("Authorization", "Bearer " + this.credentials.getAccessToken());
		}
	}
}
