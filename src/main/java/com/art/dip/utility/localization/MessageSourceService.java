package com.art.dip.utility.localization;

import com.art.dip.service.CurrentPersonInfoService;
import com.art.dip.utility.dto.CauseInvalid;
import com.art.dip.utility.dto.ValidateFormApplicantDTO;
import com.art.dip.utility.exception.ValidationFormException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Set;

@Component("messageSourceService")
public class MessageSourceService {

    private final MessageSource messages;

    private final CurrentPersonInfoService currentPersonInfoService;

    @Autowired
    public MessageSourceService(MessageSource messages, CurrentPersonInfoService currentPersonInfoService) {
        this.messages = messages;
        this.currentPersonInfoService = currentPersonInfoService;
    }
    /*
Ru Locale if name first symbol is russian
 */
    public Locale supposedLocale(ValidateFormApplicantDTO dto) {
        String name = dto.getFirstName();
        return name.charAt(0) >=0x0400 && name.charAt(0) <= 0x04FF  ? new Locale("ru","RU") : Locale.ENGLISH;
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

    public String createInvalidApplicantMessage(ValidateFormApplicantDTO dto) {

        Locale locale = supposedLocale(dto);

        Set<CauseInvalid> causes = dto.getCauses();
        //If true reason else reasons
        String reasonOrReasons = getReasonOrReasons(causes.size() == 1 && dto.getAnotherCause() == null,locale);
        StringBuilder str = new StringBuilder(reasonOrReasons);
        int[] count = {0};
        String templ = "\n%d. %s";
        causes.forEach(x-> {
            switch (x) {
                case ANOTHER_CAUSE -> {
                    if (dto.getAnotherCause() != null && !dto.getAnotherCause().isEmpty())
                        str.append(String.format(templ, ++count[0], dto.getAnotherCause()));
                }
                case INVALID_GRADE_MARK ->
                        str.append(String.format(templ, ++count[0], getInvalidGradeMarkCauseMessage(locale)));
                case INVALID_GRADE_PHOTO ->
                        str.append(String.format(templ, ++count[0], getInvalidGradePhotoCauseMessage(locale)));
                case INVALID_CERTIFICATE_MARK ->
                        str.append(String.format(templ, ++count[0], getInvalidCertificateMarkCauseMessage(locale)));
                case INVALID_CERTIFICATE_PHOTO ->
                        str.append(String.format(templ, ++count[0], getInvalidCertificatePhotoCauseMessage(locale)));
            }
        });
        return String.format(getInvalidApplicantTemplateBodyMessage(locale),dto.getFirstName(),str.toString());
    }



}
