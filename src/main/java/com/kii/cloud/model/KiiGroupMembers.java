package com.kii.cloud.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class KiiGroupMembers extends KiiJsonModel {
	public static final KiiJsonProperty PROPERTY_MEMBERS = new KiiJsonProperty("members");
	public static final KiiJsonProperty PROPERTY_USER_ID = new KiiJsonProperty("userID");
	
	public KiiGroupMembers() {
	}
	public KiiGroupMembers(JsonObject json) {
		super(json);
	}
	/**
	 * 
	 * @return list of user id who member of this group
	 */
	public List<String> getMembers() {
		List<String> members = new ArrayList<>();
		if (this.json.has(PROPERTY_MEMBERS.getName())) {
			JsonArray array = PROPERTY_MEMBERS.getJsonArray(this.json);
			for (int i = 0; i < array.size(); i++) {
				JsonElement member = array.get(i);
				if (member.isJsonPrimitive()) {
					// members ["UserID1", "UserID2", ...]
					members.add(member.getAsString());
				} else if (member.isJsonObject()) {
					// members [{"userID":"UserID1"}, {"userID":"UserID2"}, ...]
					members.add(PROPERTY_USER_ID.getString(array.get(i).getAsJsonObject()));
				}
			}
		}
		return members;
	}
	public KiiGroupMembers addMember(String userID) {
		JsonArray members = null;
		if (this.json.has(PROPERTY_MEMBERS.getName())) {
			members = PROPERTY_MEMBERS.getJsonArray(this.json);
		} else {
			members = new JsonArray();
			this.json.add(PROPERTY_MEMBERS.getName(), members);
		}
		JsonObject member = new JsonObject();
		member.addProperty(PROPERTY_USER_ID.getName(), userID);
		members.add(member);
		return this;
	}
	public JsonArray toJsonArrayAsRequest() {
		// convert to 'members ["UserID1", "UserID2", ...]'
		JsonArray result = new JsonArray();
		for (String member : this.getMembers()) {
			result.add(new JsonPrimitive(member));
		}
		return result;
	}
}
