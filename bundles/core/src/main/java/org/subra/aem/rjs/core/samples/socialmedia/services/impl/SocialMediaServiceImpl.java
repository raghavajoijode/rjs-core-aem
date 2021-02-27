package org.subra.aem.rjs.core.samples.socialmedia.services.impl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.subra.aem.rjs.core.samples.socialmedia.services.SocialMediaService;


@Component(service = SocialMediaService.class, immediate = false)
@ServiceDescription("SocialMediaServiceImpl Service Configuration")
@Designate(ocd = SocialMediaServiceImpl.Config.class)
public class SocialMediaServiceImpl implements SocialMediaService {

	private String twitterOAuthConsumerKey;

	private String twitterOAuthConsumerSecret;

	private String twitterOAuthAccessToken;

	private String twitterOAuthaccessTokenSecret;

	private String fbCompanyID;

	private String fbOAuthAccessToken;

	private String youtubeChannelName;

	private String youtubeKey;

	private String linkedInCompanyID;

	private String linkedInAccessToken;

	protected void activate(final Config config) {
		this.twitterOAuthConsumerKey = config.twitterOAuthConsumerkey();
		this.twitterOAuthConsumerSecret = config.twitterOAuthConsumerSecret();
		this.twitterOAuthAccessToken = config.twitterOAuthAcessToken();
		this.twitterOAuthaccessTokenSecret = config.twitterOAuthAcessTokenSecret();
		this.fbCompanyID = config.facebookCompanyId();
		this.fbOAuthAccessToken = config.facebookOAuthAppAccessToken();
		this.youtubeKey = config.youtubeClientkey();
		this.youtubeChannelName = config.youtubeChannelname();
		this.linkedInCompanyID = config.linkedInCompanyID();
		this.linkedInAccessToken = config.linkedInAccessToken();
	}

	public String getOAuthConsumerKey() {
		return twitterOAuthConsumerKey;
	}

	public String getOAuthConsumerSecret() {
		return twitterOAuthConsumerSecret;
	}

	public String getOAuthAccessToken() {
		return twitterOAuthAccessToken;
	}

	public String getOAuthaccessTokenSecret() {
		return twitterOAuthaccessTokenSecret;
	}

	public String getFBCompanyID() {
		return fbCompanyID;
	}

	public String getFBOAuthAccessToken() {
		return fbOAuthAccessToken;
	}

	public String getYoutubeKey() {
		return youtubeKey;
	}

	public String getYoutubeChannelName() {
		return youtubeChannelName;
	}

	public String getLinkedInCompanyID() {
		return linkedInCompanyID;
	}

	public String getLinkedInAccessToken() {
		return linkedInAccessToken;
	}
	
	@ObjectClassDefinition(name = "Subra SocialMediaServiceImpl Interface", description = "Two Factor Authentication Handler Interface Configuration")
	public @interface Config {

		@AttributeDefinition(name = "Twitter OAuth Consumer key", description = "Twitter OAuth Consumer key for Used Twitter Account to feed twitter data in website")
		String twitterOAuthConsumerkey() default "mt8mVa8xEH8wVpS2FttIHYw9t";
		
		@AttributeDefinition(name = "Twitter OAuth Consumer Secret", description = "Twitter OAuth Consumer key for Used Twitter Account to feed twitter data in website")
		String twitterOAuthConsumerSecret() default "UdJx01yUnzkrtZsofYA5tNYyOIzhfuSfUpfJWQr7eOsSqD9sdD";
		
		@AttributeDefinition(name = "Twitter OAuth Access Token", description = "Twitter OAuth Access Token  for Used Twitter Account to feed twitter data in website")
		String twitterOAuthAcessToken() default "14390109-eZyXaxlffOrT8dGWT3BD436siZs8sG6U4l96lEdLH";
		
		@AttributeDefinition(name = "Twitter OAuth Access Token Secret", description = "Twitter OAuth Access Token Secret for Used Twitter Account to feed twitter data in website")
		String twitterOAuthAcessTokenSecret() default "XPSZw6teh2bJvpRFAUVbYZXp5kWEaXVFrynmZZF9kzWpZ";
		
		@AttributeDefinition(name = "Facebook Company ID", description = "Facebook Company ID for Used  Account to feed Facebook data in website")
		String facebookCompanyId() default "Raghava.Joijode";
		
		@AttributeDefinition(name = "Facebook OAuth App Access Token", description = "Facebook OAuth App Secret for Used  Account to feed Facebook data in website")
		String facebookOAuthAppAccessToken() default "EAAFgcwuLZB0oBAHtjivAHQlnwJG8UwepiMtpK0LnLyAJ1RHjheCLPc3HwxKO9pnYs3HWx2RfKIGiyrZC49XKeHd8NaF1Ftx8k8LaNl9wlLX50LOVQFSnpxTCqJEtu6pBRwK0ODoF2gI6PZAaZA4httLUq0fv6xEZD";
		
		@AttributeDefinition(name = "Youtube Channel Name", description = "Youtube Channel Name to feed Youtube data in website")
		String youtubeChannelname() default "raghavaVideos";
		
		@AttributeDefinition(name = "Youtube Key", description = "Youtube Client key for Used  Account to feed Youtube data in website")
		String youtubeClientkey() default "AIzaSyDD_6agCrGHFhsPkaUz8X_V-A8CjzO4VEQ";
		
		@AttributeDefinition(name = "LinkedIn Company ID", description = "LinkedIn Company ID of which data in website has to be fetched")
		String linkedInCompanyID() default "1318-A8CjzO4VEQ";

		@AttributeDefinition(name = "LinkedIn Access Token", description = "LinkedIn Access Token for Used  Account to feed Youtube data in website")
		String linkedInAccessToken() default "AQXj2sNNugQMnMHXC5JDbusHGC6sO77n96S7Yzr2gUEzJ6ZDaGoSeihJKdbC3eebm3j1moHeh-_yyZkXNx9TVayRRmnfB-3T6itYW_N6ZYOGyA7WfM-C2gwVV_xHjAjVWFKhEnr6nEnU-GlgZj_hQyHk1K-7mHlWrYa0H0gZ2o_yGZQHR1w";
	}

}
