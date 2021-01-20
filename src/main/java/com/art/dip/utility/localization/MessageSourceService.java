package com.art.dip.utility.localization;

import com.art.dip.service.CurrentPersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component("messageSourceService")
public class MessageSourceService {

    private final MessageSource messages;

    private final CurrentPersonInfoService currentPersonInfoService;

    @Autowired
    public MessageSourceService(MessageSource messages, CurrentPersonInfoService currentPersonInfoService) {
        this.messages = messages;
        this.currentPersonInfoService = currentPersonInfoService;
    }

    private String simpleMessage(String message) {
        return messages.getMessage(message,null,currentPersonInfoService.getCurrentLoggedPersonLocale());
    }

    private String simpleExplicitLocaleMessage(String message,Locale locale) {
        return messages.getMessage(message,null,locale);
    }

    private String messageWithArgs(String message,String[] args) {
        return messages.getMessage(message,args,currentPersonInfoService.getCurrentLoggedPersonLocale());
    }

    public String getValidationMessageValidateFormApplicant() {
        return simpleMessage("invalid.faculty.appForm");
    }

    public String getValidationMessagePasswordNotMatched() {
        return simpleMessage("error.password.match");
    }

    public String getRegistrationConfirmSubjectMessage() {
        return simpleMessage("message.emailConfirmTitle");
    }
    public String getRegistrationConfirmBodyMessage(String[] args) {
        return messageWithArgs("message.emailConfirm",args);
    }

    public String getValidApplicantEmailSubjectMessage() {
        return simpleMessage("valid.applicant.email.subject");
    }

    public String getValidApplicantEmailBodyMessage() {
        return simpleMessage("valid.applicant.email.body");
    }

    public String getErrorFormAdminMistakeNoCausesMessage(Locale locale) {
        return simpleExplicitLocaleMessage("error.admin.mistake.noCauses",locale);
    }

    public String getReasonOrReasons(boolean isOne,Locale locale) {
        return isOne ? simpleExplicitLocaleMessage("reason",locale) : simpleExplicitLocaleMessage("reasons",locale);
    }

    public String getInvalidGradeMarkCauseMessage(Locale locale) {
        return simpleExplicitLocaleMessage("invalid.grade.mark",locale);
    }

    public String getInvalidGradePhotoCauseMessage( Locale locale) {
        return simpleExplicitLocaleMessage("invalid.grade.photo",locale);
    }

    public String getInvalidCertificatePhotoCauseMessage(Locale locale) {
        return simpleExplicitLocaleMessage("invalid.certificate.photo",locale);
    }

    public String getInvalidCertificateMarkCauseMessage(Locale locale) {
        return simpleExplicitLocaleMessage("invalid.certificate.mark",locale);
    }

    public String getInvalidApplicantEmailSubjectMessage(Locale locale) {
        return simpleExplicitLocaleMessage("invalid.email.subject",locale);
    }

    public String getInvalidApplicantTemplateBodyMessage(Locale locale) {
        return simpleExplicitLocaleMessage("invalid.emailMessage.template",locale);
    }

    public String getInvalidFormApplicantNoCausesMessage() {
        return simpleMessage("invalid.form.applicant.noCauses");
    }

    public String getExpiredTokenMessage() {
        return simpleMessage("label.verifyToken.expired");
    }

    public String getEmailIsExistMessage(String ... args) {
        return messageWithArgs("message.emailExist",args);
    }

}
