package com.art.dip.service;

import com.art.dip.model.Person;
import com.art.dip.service.interfaces.EmailService;
import com.art.dip.utility.dto.CauseInvalid;
import com.art.dip.utility.dto.ValidateFormApplicantDTO;
import com.art.dip.utility.event.OnRegistrationCompleteEvent;
import com.art.dip.utility.exception.ValidationFormException;
import com.art.dip.utility.localization.MessageSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Set;


@Component
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender sender;

    private final MessageSourceService mesService;

    @Autowired
    public EmailServiceImpl(JavaMailSender sender, MessageSourceService mesService) {

        this.sender = sender;
        this.mesService = mesService;
    }

    @Override
    public void sendRegistrationMessage(Person user, OnRegistrationCompleteEvent event, String token) {
        String subject = mesService.getRegistrationConfirmSubjectMessage();
        String confirmationUrl
                = event.getAppUrl() + "/registrationConfirm?token=" + token;
        String message = mesService.getRegistrationConfirmBodyMessage(new String[]{user.getFirstName(),confirmationUrl});
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setText(message);
        sender.send(email);
    }

    @Override
    public void sendValidApplicantEmail(ValidateFormApplicantDTO dto) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(dto.getEmail());
        email.setSubject(mesService.getValidApplicantEmailSubjectMessage());
        email.setText(mesService.getValidApplicantEmailBodyMessage());
        sender.send(email);
    }

    @Override
    public void sendInvalidApplicantEmail(ValidateFormApplicantDTO dto) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(dto.getEmail());
        email.setSubject(mesService.getInvalidApplicantEmailSubjectMessage(supposedLocale(dto)));
        email.setText(createInvalidApplicantMessage(dto));
        sender.send(email);
    }

    private String createInvalidApplicantMessage(ValidateFormApplicantDTO dto) {

        Locale locale = supposedLocale(dto);

        Set<CauseInvalid> causes = dto.getCauses();
        if (causes == null) {
            log.warn("Admin mistake. Form for: Email ".concat(dto.getEmail()));
            throw new ValidationFormException(mesService.getErrorFormAdminMistakeNoCausesMessage(locale));
        }
        //If true reason else reasons
        String reasonOrReasons = mesService.getReasonOrReasons(causes.size() == 1 && dto.getAnotherCause() == null,locale);
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
                        str.append(String.format(templ, ++count[0], mesService.getInvalidGradeMarkCauseMessage(locale)));
                case INVALID_GRADE_PHOTO ->
                        str.append(String.format(templ, ++count[0], mesService.getInvalidGradePhotoCauseMessage(locale)));
                case INVALID_CERTIFICATE_MARK ->
                        str.append(String.format(templ, ++count[0], mesService.getInvalidCertificateMarkCauseMessage(locale)));
                case INVALID_CERTIFICATE_PHOTO ->
                        str.append(String.format(templ, ++count[0], mesService.getInvalidCertificatePhotoCauseMessage(locale)));
            }
        });
        return String.format(mesService.getInvalidApplicantTemplateBodyMessage(locale),dto.getFirstName(),str.toString());
    }

/*
Check if name first symbol is russian
 */
    private Locale supposedLocale(ValidateFormApplicantDTO dto) {
        String name = dto.getFirstName();

      return name.charAt(0) >=0x0400 && name.charAt(0) <= 0x04FF  ? new Locale("ru","RU") : Locale.ENGLISH;
    }
}
