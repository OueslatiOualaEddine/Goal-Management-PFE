package hr.maps.goalmanagment.service.email;


import hr.maps.goalmanagment.dtos.EmailDto;
import hr.maps.goalmanagment.enumeration.EmailContext;
import hr.maps.goalmanagment.utils.Constants;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
@Slf4j
public class EmailService {

	@Value("${gm.front.base.url}")
	private String frontBaseUrl;

	@Value("${gm.backend.base.url}")
	private String backendBaseUrl;

	@Value("${spring.mail.username}")
	private String smtpMailUsername;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${gm.app.base-url}")
	private String baseUrl;



	@Autowired
	private SpringTemplateEngine thymeleafTemplateEngine;

	public EmailService() {
	}

	/**
	 * send email
	 *
	 * @param emailDto: email content and config
	 */
	@Async
	public void sendMail(EmailDto emailDto, List<String> destinations) {
		destinations.stream().forEach(destination -> {

			emailDto.getMaps().put(Constants.FRONT_BASE_URL, frontBaseUrl);
			emailDto.getMaps().put(Constants.BACKEND_BASE_URL, backendBaseUrl);
			try {

				Context thymeleafContext = new Context();
				thymeleafContext.setVariables(emailDto.getMaps());
				String htmlBody = thymeleafTemplateEngine.process(emailDto.getTemplateName(), thymeleafContext);

				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
				// helper.setTo(emailDto.getTo());
				helper.setTo(destination);
				helper.setFrom(smtpMailUsername);
				helper.setSubject(emailDto.getSubject());
				helper.setText(htmlBody, true);

				// Add exist attachments to email
				emailDto.getAttachments().entrySet().forEach(attachment -> {
					try {
						helper.addAttachment(attachment.getKey(), new ByteArrayResource(attachment.getValue()));
					} catch (MessagingException e) {
						log.error(e.getMessage());
						e.printStackTrace();
					}
				});

				mailSender.send(message);

			} catch (Exception e) {
				/*if(emailDto.getEmailContext().equals(EmailContext.WELCOME_USER)){
					String code=emailDto.getMaps().entrySet().stream()
							.filter(k -> k.getKey().equals("UUID"))
							.map(Map.Entry::getValue)
							.findFirst()
							.orElse(null).toString();

				}*/
				e.printStackTrace();
				log.error("Exception when send Email: {}", e.getMessage());
			}
		});

	}

}
