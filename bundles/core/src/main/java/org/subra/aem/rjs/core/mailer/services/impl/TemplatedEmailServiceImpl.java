package org.subra.aem.rjs.core.mailer.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceRanking;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subra.aem.rjs.core.mailer.services.MailerGatewayService;
import org.subra.aem.rjs.core.mailer.services.MailerService;
import org.subra.aem.rjs.core.mailer.services.TemplatedEmailService;
import org.subra.aem.rjs.core.mailer.utils.EmailType;
import org.subra.aem.rjs.core.mailer.utils.MailerUtils;

import java.util.HashMap;
import java.util.Map;

@Component(service = TemplatedEmailService.class, immediate = true)
@ServiceRanking(60000)
@ServiceDescription("RJS Templated Email Service")
@Designate(ocd = TemplatedEmailService.Config.class)
public final class TemplatedEmailServiceImpl implements TemplatedEmailService {


    private static final Logger LOGGER = LoggerFactory.getLogger(TemplatedEmailServiceImpl.class);
    @Reference
    private MailerService emailService;
    @Reference(target = "(connect.with=HELP)")
    private MailerGatewayService emailGatewayHelp;
    @Reference(target = "(connect.with=NOREPLY)")
    private MailerGatewayService emailGatewayNoReply;
    @Reference
    private MailerGatewayService emailGateway;
    private String registrationEmailTemplate;
    private String invititionEmailTemplate;
    private String welcomeEmailTemplate;
    private String exceptionEmailTemplate;
    private String genericEmailTemplate;
    private String htmlEmailTemplate;

    @Activate
    protected void activate(final Config config) {
        registrationEmailTemplate = config.registration_email_template();
        invititionEmailTemplate = config.invitation_email_template();
        welcomeEmailTemplate = config.welcome_email_template();
        exceptionEmailTemplate = config.exception_email_template();
        genericEmailTemplate = config.generic_email_template();
        htmlEmailTemplate = config.sample_html_email();
        LOGGER.info(
                "SubraTemplatedEmailService activated with [registrationEmailTemplate : {}] , [invititionEmailTemplate : {}], [welcomeEmailTemplate : {}], [exceptionEmailTemplate : {}], [genericEmailTemplate : {}]",
                registrationEmailTemplate, invititionEmailTemplate, welcomeEmailTemplate, exceptionEmailTemplate,
                genericEmailTemplate);
    }

    @Override
    public boolean email(EmailType type, String subject, String recipientName, String senderName, String link,
                         Map<String, String> optionalParams, String... recipient) {
        boolean response = false;
        Map<String, String> emailParams = new HashMap<>();
        emailParams.put(MailerUtils.SUBJECT, subject);
        emailParams.put("recipientName", recipientName);
        emailParams.put(MailerUtils.SENDER_NAME, senderName);
        emailParams.put("link", link);
        emailParams.putAll(optionalParams);
        if (StringUtils.isNoneBlank(subject, recipientName) && recipient.length > 0) {
            switch (type) {
                case GENERIC:
                    response = sendEmail(emailGatewayNoReply, htmlEmailTemplate, emailParams, recipient);
                    break;
                case EXCEPTION:
                    response = sendEmail(emailGateway, exceptionEmailTemplate, emailParams, recipient);
                    break;
                case WELCOME:
                    response = sendEmail(emailGateway, welcomeEmailTemplate, emailParams, recipient);
                    break;
                case INVITITION:
                    response = sendEmail(emailGateway, invititionEmailTemplate, emailParams, recipient);
                    break;
                case REGESTRATION:
                default:
                    response = sendEmail(emailGateway, registrationEmailTemplate, emailParams, recipient);
                    break;
            }
        }
        return response;
    }

    private boolean sendEmail(MailerGatewayService conn, String template, Map<String, String> parameters,
                              String... recipient) {
        Map<String, Object> response = emailService.sendEmail(conn, template, parameters, recipient);
        return response.isEmpty();
    }

}
