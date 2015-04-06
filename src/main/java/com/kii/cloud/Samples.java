package com.kii.cloud;

import com.kii.cloud.model.KiiNormalUser;
import com.kii.cloud.model.KiiObject;
import com.kii.cloud.model.KiiUser;
import com.kii.cloud.resource.KiiUserResource.NotificationMethod;

public class Samples {
	public static void main(String[] args) throws Exception {
		KiiRest rest = new KiiRest("c230bead", "1ecb42576c3b98924f1ab21badd2e7ef", KiiRest.Site.JP);
		
		KiiNormalUser user = new KiiNormalUser()
			.setUsername("nori" + System.currentTimeMillis())
			.setDisplayName("Noriyoshi Fukuzaki")
			.setCountry("JP");
		
		rest.api().user().register(user, "pasword");
		rest.setCredentials(user);
		
		KiiUser u = rest.api().user(user).get();
		
		KiiObject object = new KiiObject().set("score", 0).set("level", 1);
		rest.api().user(user).bucket("my_bucket").object().save(object);
		object.set("score", 100).set("level", 2);
		rest.api().user(user).bucket("my_bucket").object(object).update(object);
		
		KiiObject partialObject = new KiiObject().set("hight_score", 10000);
		rest.api().user(user).bucket("my_bucket").object(object).partialUpdate(partialObject);
		
		object = rest.api().user(user).bucket("my_bucket").object(object.getObjectID()).get();
		
//		rest.api().user(user.getEmail()).resetPassword(NotificationMethod.EMAIL);
		
		rest.api().user(user).bucket("my_bucket").object(object.getObjectID()).delete();
		
	}
}
