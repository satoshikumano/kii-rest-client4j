package com.kii.cloud.rest.client.model.uri;

import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.util.StringUtils;

/**
 * Represents the XXXX URI like following URIs:
 * 
 * <ul>
 * <li>kiicloud://{AppID}/bukcets/{BucketID}
 * <li>kiicloud://{AppID}/users/{UserID}/bukcets/{BucketID}
 * <li>kiicloud://{AppID}/group/{GroupID}/bukcets/{BucketID}
 * <li>kiicloud://{AppID}/things/{ThingsID}/bukcets/{BucketID}
 * </ul>
 */
public class KiiBucketURI extends KiiURI {
	
	public static KiiBucketURI newAppScopeURI(String appID, String bucketID) {
		return new KiiBucketURI(new KiiAppURI(appID), bucketID);
	}
	public static KiiBucketURI newUserScopeURI(String appID, String userIdentifier, String bucketID) {
		return new KiiBucketURI(new KiiUserURI(new KiiAppURI(appID), userIdentifier), bucketID);
	}
	public static KiiBucketURI newGroupScopeURI(String appID, String groupID, String bucketID) {
		return new KiiBucketURI(new KiiGroupURI(new KiiAppURI(appID), groupID), bucketID);
	}
	public static KiiBucketURI newThingScopeURI(String appID, String thingIdentifier, String bucketID) {
		return new KiiBucketURI(new KiiThingURI(new KiiAppURI(appID), thingIdentifier), bucketID);
	}
	
	public static KiiBucketURI parse(String str) {
		if (StringUtils.isEmpty(str)) {
			throw new IllegalArgumentException("str is null or empty");
		}
		if (!str.startsWith(SCHEME)) {
			throw new IllegalArgumentException("URI should start with 'kiicloud://'");
		}
		String[] segments = str.replace(SCHEME, "").split("/");
		if (segments.length == 3) {
			if (StringUtils.equals(SEGMENT_BUCKETS, segments[1])) {
				return new KiiBucketURI(new KiiAppURI(segments[0]), segments[2]);
			}
		} else if (segments.length == 5) {
			if (StringUtils.equals(SEGMENT_BUCKETS, segments[3])) {
				if (StringUtils.equals(SEGMENT_USERS, segments[1])) {
					return new KiiBucketURI(new KiiUserURI(new KiiAppURI(segments[0]), segments[2]), segments[4]);
				} else if (StringUtils.equals(SEGMENT_GROUPS, segments[1])) {
					return new KiiBucketURI(new KiiGroupURI(new KiiAppURI(segments[0]), segments[2]), segments[4]);
				} else if (StringUtils.equals(SEGMENT_THINGS, segments[1])) {
					return new KiiBucketURI(new KiiThingURI(new KiiAppURI(segments[0]), segments[2]), segments[4]);
				}
			}
		}
		throw new IllegalArgumentException("invalid URI : " + str);
	}
	
	private final KiiURI parent;
	private final String bucketID;
	
	protected KiiBucketURI(KiiAppURI parent, String bucketID) {
		this.parent = parent;
		this.bucketID = bucketID;
	}
	protected KiiBucketURI(KiiUserURI parent, String bucketID) {
		this.parent = parent;
		this.bucketID = bucketID;
	}
	protected KiiBucketURI(KiiGroupURI parent, String bucketID) {
		this.parent = parent;
		this.bucketID = bucketID;
	}
	protected KiiBucketURI(KiiThingURI parent, String bucketID) {
		this.parent = parent;
		this.bucketID = bucketID;
	}
	public String getScopeID() {
		if (this.parent instanceof KiiUserURI) {
			KiiUserURI userURI = (KiiUserURI)this.parent;
			return userURI.getAccountType().getFullyQualifiedIdentifier(userURI.getIdentifier());
		} else if (this.parent instanceof KiiGroupURI) {
			return ((KiiGroupURI)this.parent).getGroupID();
		} else if (this.parent instanceof KiiThingURI) {
			KiiThingURI thingURI = (KiiThingURI)this.parent;
			return thingURI.getIdentifierType().getFullyQualifiedIdentifier(thingURI.getIdentifier());
		}
		return null;
	}
	public String getBucketID() {
		return this.bucketID;
	}
	@Override
	public KiiScope getScope() {
		return this.parent.getScope();
	}
	@Override
	public String toUriString() {
		return this.parent.toUriString() + "/" + SEGMENT_BUCKETS + "/" + this.bucketID;
	}
	@Override
	public String toString() {
		return this.toUriString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bucketID == null) ? 0 : bucketID.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KiiBucketURI other = (KiiBucketURI) obj;
		if (bucketID == null) {
			if (other.bucketID != null)
				return false;
		} else if (!bucketID.equals(other.bucketID))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}
}
