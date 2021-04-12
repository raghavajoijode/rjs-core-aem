package org.subra.aem.rjs.core.samples.socialmedia.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subra.aem.rjs.core.samples.socialmedia.services.SocialMediaService;

/* import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory */

/**
 * @author raghava
 *
 */
@Model(adaptables = { SlingHttpServletRequest.class })
public class SocialMediaModel {

	@OSGiService
	private SocialMediaService socailMediaService;

	private static final Logger LOGGER = LoggerFactory.getLogger(SocialMediaModel.class);

	// private String oAuthConsumerKey

	// private String oAuthConsumerSecret

	// private String oAuthAccessToken

	// private String oAuthaccessTokenSecret

	//private Twitter twitter

	private String fbCompanyID;

	private String fbOAuthAccessToken;

	private String linkedInCompanyID;

	private String linkedInAccessToken;

	private String youtubeChannelName;

	private String youtubeKey;
	
	@Inject @Default(intValues = 5)
	private int noOfStatus;


	@PostConstruct
	public void init() {
		// this.oAuthConsumerKey = socailMediaService.getOAuthConsumerKey()
		// this.oAuthConsumerSecret = socailMediaService.getOAuthConsumerSecret()
		// this.oAuthAccessToken = socailMediaService.getOAuthAccessToken()
		// this.oAuthaccessTokenSecret = socailMediaService.getOAuthaccessTokenSecret()
		this.fbCompanyID = socailMediaService.getFBCompanyID();
		this.fbOAuthAccessToken = socailMediaService.getFBOAuthAccessToken();
		this.youtubeChannelName = socailMediaService.getYoutubeChannelName();
		this.youtubeKey = socailMediaService.getYoutubeKey();
		this.linkedInCompanyID = socailMediaService.getLinkedInCompanyID();
		this.linkedInAccessToken = socailMediaService.getLinkedInAccessToken();
		LOGGER.debug("End of Activate");
		this.twitterConnect();

	}

	public void twitterConnect() {
		LOGGER.info("Start of to Twitter");
		//ConfigurationBuilder cb = new ConfigurationBuilder()
		LOGGER.info("Start of proxy");
		LOGGER.info("End of proxy");

		/* cb.setDebugEnabled(true).setOAuthConsumerKey(this.oAuthConsumerKey)
				.setOAuthConsumerSecret(this.oAuthConsumerSecret).setOAuthAccessToken(this.oAuthAccessToken)
				.setOAuthAccessTokenSecret(this.oAuthaccessTokenSecret)
		TwitterFactory tf = new TwitterFactory(cb.build())
		twitter = tf.getInstance() */
		LOGGER.debug("End of to Twitter");
	}

	// public List<Status> getUserTimeLineTwitter() throws TwitterException {
	//	List<Status> statusText = new ArrayList<>()
	//	LOGGER.debug("In twitter method")
	//	List<Status> twits = twitter.getUserTimeline()
	//	LOGGER.debug("In twitter method twoo")
	//	for :int i = 0; i < noOfStatus; i++
	//		statusText.add(twits.get(i))
	//		LOGGER.debug("In twitter method:: {}", i)
	//
	//	LOGGER.debug(" Status Text {}", statusText)
	// 	return statusText
	//

	public String getYoutubeChannelName() {

		return this.youtubeChannelName;
	}

	public String getYoutubekey() {

		return this.youtubeKey;
	}

	public String getLinkedInCompanyID() {

		return this.linkedInCompanyID;
	}

	public String getLinkedInAccessToken() {

		return this.linkedInAccessToken;
	}

	public String getFacebookAccessToken() {

		return this.fbOAuthAccessToken;
	}

	public String getFacebookCompanyID() {

		return this.fbCompanyID;
	}

}
