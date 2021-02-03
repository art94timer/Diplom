package com.art.dip.utility.localization;

import com.art.dip.model.Faculty;
import com.art.dip.service.PersonInfoService;
import com.art.dip.utility.dto.CauseInvalid;
import com.art.dip.utility.dto.ValidateFormApplicantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Set;

import static com.art.dip.utility.dto.CauseInvalid.*;

@Component("messageSourceService")
public class MessageSourceService {

    private final MessageSource messages;

    private final PersonInfoService personInfoService;

    @Autowired
    public MessageSourceService(MessageSource messages, PersonInfoService personInfoService) {
        this.messages = messages;
        this.personInfoService = personInfoService;
    }

    private String simpleMessage(String message) {
        return messages.getMessage(message,null, personInfoService.getCurrentLoggedPersonLocale());
    }

    private String simpleExplicitLocaleMessage(String message,Locale locale) {
        return messages.getMessage(message,null,locale);
    }

    private String messageWithArgs(String message,String[] args) {
        return messages.getMessage(message,args, personInfoService.getCurrentLoggedPersonLocale());
    }

    private String explicitLocaleMessageWithArgs(String s, String[] args, Locale locale) {
        return messages.getMessage(s,args,locale);
    }

    public String getValidationMessageValidateFormApplicant() {
        return simpleMessage("invalid.faculty.appForm");
    }

    public String getValidationMessagePasswordNotMatched() {
        return simpleMessage("error.password.match");
    }

    public String getRegistrationConfirmSubjectMessage(Locale locale) {
        return simpleExplicitLocaleMessage("message.emailConfirmTitle", locale);
    }
    public String getRegistrationConfirmBodyMessage(String[] args,Locale locale) {
        return explicitLocaleMessageWithArgs("message.emailConfirm",args,locale);
    }



    public String getValidApplicantEmailSubjectMessage(Locale locale) {
        return simpleExplicitLocaleMessage("valid.applicant.email.subject",locale);
    }

    public String getValidApplicantEmailBodyMessage( Locale personLocale) {

        return simpleExplicitLocaleMessage("valid.applicant.email.body",personLocale);
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

    public String getInvalidApplicantEmailSubjectMessage(String to) {
        return simpleExplicitLocaleMessage("invalid.email.subject",personInfoService.getPersonLocale(to));
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

    public String getEmailIsExistMessage(Locale locale,String ... args) {
        return explicitLocaleMessageWithArgs("message.emailExist",args,locale);
    }
    public String getWeSendYouEmailMessage() {
        return simpleMessage("message.we.notify.you");
    }

    public String createInvalidApplicantMessage(ValidateFormApplicantDTO dto) {

        Locale locale = personInfoService.getPersonLocale(dto.getEmail());

        Set<CauseInvalid> causes = dto.getCauses();
        //If true reason else reasons
        String reasonOrReasons = getReasonOrReasons(causes.size() == 1 && dto.getAnotherCause() == null,locale);
        StringBuilder str = new StringBuilder(reasonOrReasons);
        int[] count = {0};
        String templ = "\n%d. %s";
        causes.forEach(x -> {
            switch (x) {
                case ANOTHER_CAUSE : {
                    if (dto.getAnotherCause() != null && !dto.getAnotherCause().isEmpty()) {
                        str.append(String.format(templ, ++count[0], dto.getAnotherCause()));
                    }
                    break;
                }

            case INVALID_GRADE_MARK : {
                str.append(String.format(templ, ++count[0], getInvalidGradeMarkCauseMessage(locale)));
                break;
            }
                case INVALID_GRADE_PHOTO : {
                    str.append(String.format(templ, ++count[0], getInvalidGradePhotoCauseMessage(locale)));
                    break;
                }
                case INVALID_CERTIFICATE_MARK : {
                    str.append(String.format(templ, ++count[0], getInvalidCertificateMarkCauseMessage(locale)));
                    break;
                }
                case INVALID_CERTIFICATE_PHOTO: {
                    str.append(String.format(templ, ++count[0], getInvalidCertificatePhotoCauseMessage(locale)));
                    break;
                }
            }
        });
        return String.format(getInvalidApplicantTemplateBodyMessage(locale),dto.getFullName(),str.toString());
    }


    public String getVerifyYourEmailMessage() {
        return simpleMessage("message.verifyEmail");
    }

    public String getWaitForAdminEmailMessage() {
        return simpleMessage("label.sucRegApp");
    }

    public String getSuccessfullyDeleteSubjectMessage() {
        return simpleMessage("message.subject.successfully.delete");
    }

    public String getSubjectBelongToFacultyMessage() {
        return simpleMessage("message.subject.belong");
    }

    public String getFacultyImmutableMessage() {
        return simpleMessage("message.faculty.immutable");
    }

    public String getSuccessfullyEditMessage() {
        return simpleMessage("message.sucEdit.subject");
    }

    public String getFacultyInSubjectAlreadyExist() {
        return simpleMessage("message.sub.already.exist");
    }

    public String getFacultyExistMessage() {
        return simpleMessage("message.faculty.exist");
    }

    public String getFacultyIsChangedSubjectMessage(Locale personLocale) {
        return simpleExplicitLocaleMessage("message.changed.faculty.email.subject",personLocale);
    }

    public String getFacultyIsChangedBodyMessage(Locale personLocale) {
        return simpleExplicitLocaleMessage("message.changed.faculty.email.body",personLocale);
    }

    public String getNotifyFacultyAvailableSubjectMessage(Locale personLocale) {
        return simpleExplicitLocaleMessage("message.notify.faculty.subject",personLocale);
    }

    public String getNotifyFacultyAvailableBodyMessage(Locale personLocale,Faculty faculty) {
        String fName = personLocale.getLanguage().equals("ru") ? faculty.getRuName() : faculty.getName();
        return explicitLocaleMessageWithArgs("message.notify.faculty.body",new String[]{fName},personLocale);
    }

    public String getWeSendYouNotifyEmailMessage() {
        return simpleMessage("message.we.send.notify");
    }

    public String getInvalidNameMessage() {
        return simpleMessage("message.invalid.name");
    }

    public String getSubjectIsExistMessage() {
        return simpleMessage("message.subject.exist");
    }

    public String getWeSorryFacultyDisabledSubjectMessage(Locale personLocale) {
        return simpleExplicitLocaleMessage("message.faculty.disabled.subject",personLocale);
    }

    public String getWeSorryFacultyDisabledBodyMessage(Locale personLocale, Faculty faculty) {
        String name = personLocale.getLanguage().equals("ru") ? faculty.getRuName() : faculty.getName();
        return explicitLocaleMessageWithArgs("message.faculty.disabled.body",new String[]{name},personLocale);
    }

    public String getCongratulationEmailSubjectMessage(Locale personLocale) {
        return simpleExplicitLocaleMessage("congratulation.email.subject",personLocale);
    }

    public String getCongratulationEmailBodyMessage(Locale personLocale, Faculty faculty) {
        String facultyName;
        if (personLocale.getLanguage().equals("ru")) {
            facultyName = faculty.getRuName();
        } else {
            facultyName = faculty.getName();
        }
        return explicitLocaleMessageWithArgs("congratulation.email.body",new String[]{facultyName},personLocale);
    }

    public String getMaybeNextTimeEmailSubjectMessage(Locale personLocale) {
        return simpleExplicitLocaleMessage("maybe.next.time.subject",personLocale);
    }

    public String getMaybeNextTimeEmailBodyMessage(Locale personLocale, Faculty faculty) {
        String facultyName;
        if (personLocale.getLanguage().equals("ru")) {
            facultyName = faculty.getRuName();
        } else {
            facultyName = faculty.getName();
        }
        return explicitLocaleMessageWithArgs("maybe.next.time.body",new String[]{facultyName},personLocale);
    }

    public String getSubjectsNotFoundMessage() {
        return simpleMessage("message.nosubjects");
    }

    public String getInvalidCapacityOrInvalidDate() {
        return simpleMessage("message.invalid.date.or.cap");
    }

    public String getYouWillNotPass(Locale locale) {
        return simpleExplicitLocaleMessage("you.will.not.pass",locale);
    }
}
