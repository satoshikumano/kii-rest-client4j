package com.kii.cloud.rest.client.resource.social;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.social.KiiSocialProvider;
import com.kii.cloud.rest.client.model.storage.KiiUser;
import com.kii.cloud.rest.client.util.StringUtils;
import com.squareup.okhttp.MediaType;

/**
 * Represents the RenRen integration resource like following URI:
 * 
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/integration/renren
 * </ul>
 */
public class KiiRenrenIntegrationResource extends KiiAbstractIntegrationResource {
	
	public static final String BASE_PATH = "/renren";
	
	public KiiRenrenIntegrationResource(KiiSocialIntegrationResource parent) {
		super(parent);
	}
	public KiiUser login(String accessToken) throws KiiRestException {
		if (StringUtils.isEmpty(accessToken)) {
			throw new IllegalArgumentException("accessToken is null or empty");
		}
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("accessToken", accessToken);
		return super.login(MediaType.parse(KiiSocialProvider.RENREN.getTokenRequestContentType()), requestBody);
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
