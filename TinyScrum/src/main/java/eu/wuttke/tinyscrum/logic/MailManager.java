package eu.wuttke.tinyscrum.logic;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Component;

import eu.wuttke.tinyscrum.domain.Comment;
import eu.wuttke.tinyscrum.domain.CommentType;
import eu.wuttke.tinyscrum.domain.ScrumUser;
import eu.wuttke.tinyscrum.domain.Task;
import eu.wuttke.tinyscrum.domain.UserStory;

/**
 * Utility class for sending mail
 * @author Matthias Wuttke
 */
@Component
@Configurable(autowire=Autowire.BY_NAME)
@RooJavaBean
public class MailManager {

	/**
	 * SMTP server to use
	 */
	private String smtpServer;
	
	/**
	 * SMTP port to use
	 */
	private int smtpPort = 25;
	
	/**
	 * User manager (injected)
	 */
	private UserManager userManager;

	/**
	 * Sends a mail regarding a task-related action.
	 * @param task task
	 * @param action action string
	 */
	public void sendTaskMail(Task task, String action) {
		final String NL = "<br/>\r\n";
		String subject = "[TinyScrum] Task #" + task.getId() + ": " + task.getName() +  " - " + action;
		
		StringBuilder body = new StringBuilder();
		body.append("<p>Task: <b>" + StringEscapeUtils.escapeHtml4(task.getName()) + "</b> (#" + task.getId() + ")" + NL);
		body.append("Story: <b>" + StringEscapeUtils.escapeHtml4(task.getStory().getTitle()) + "</b> (#" + task.getStory().getId() + ")" + NL);
		body.append("Iteration: <b>" + StringEscapeUtils.escapeHtml4(task.getStory().getIterationName()) + "</b>" + NL);
		body.append("Project: <b>" + StringEscapeUtils.escapeHtml4(task.getProject().toString()) + "</b>" + NL);
		body.append("Status: <b>" + StringEscapeUtils.escapeHtml4(task.getStatus().toString()) + "</b></p>\r\n");
		
		if (!StringUtils.isEmpty(task.getDescription()))
			body.append("<p>" + StringEscapeUtils.escapeHtml4(task.getDescription()) + "</p>\r\n");
		
		List<Comment> comments = getComments(CommentType.TASK, task.getId());
		if (comments != null && comments.size() > 0) {
			body.append("<p>Comments:\r\n<ul>\r\n");
			for (Comment comment : comments)
				body.append("<li>" + StringEscapeUtils.escapeHtml4(comment.getComment()) + 
						" (<i>" + StringEscapeUtils.escapeHtml4(comment.getUserName()) + ", " + comment.getCreateDateTime() + "</i>)</li>\r\n");
			body.append("</ul></p>");
		}
		
		Set<String> users = new HashSet<String>();
		if (task.getDeveloper1() != null)
			users.add(task.getDeveloper1());
		if (task.getDeveloper2() != null)
			users.add(task.getDeveloper2());
		if (task.getTester() != null)
			users.add(task.getTester());
		if (task.getStory().getOwner() != null)
			users.add(task.getStory().getOwner());
		
		try {
			sendHtmlMail(retrieveMail(users), subject, body.toString(), "bot@tinyscrum.org");
		} catch (MessagingException e) {
		}
	}
	
	/**
	 * Sends a mail regarding a story-related action.
	 * @param story user story
	 * @param action action label
	 */
	public void sendStoryMail(UserStory story, String action) {
		final String NL = "<br/>\r\n";
		String subject = "[TinyScrum] Story #" + story.getId() + ": " + story.getTitle() +  " - " + action;
		
		StringBuilder body = new StringBuilder();
		body.append("<p>Story: <b>" + StringEscapeUtils.escapeHtml4(story.getTitle()) + "</b> (#" + story.getId() + ")" + NL);
		body.append("Iteration: <b>" + StringEscapeUtils.escapeHtml4(story.getIterationName()) + "</b>" + NL);
		body.append("Project: <b>" + StringEscapeUtils.escapeHtml4(story.getProject().toString()) + "</b>" + NL);
		body.append("Status: <b>" + StringEscapeUtils.escapeHtml4(story.getStatus().toString()) + "</b></p>\r\n");
		
		if (!StringUtils.isEmpty(story.getDescription()))
			body.append("<p>" + StringEscapeUtils.escapeHtml4(story.getDescription()) + "</p>\r\n");
		
		List<Comment> comments = getComments(CommentType.USER_STORY, story.getId());
		if (comments != null && comments.size() > 0) {
			body.append("<p>Comments:\r\n<ul>\r\n");
			for (Comment comment : comments)
				body.append("<li>" + StringEscapeUtils.escapeHtml4(comment.getComment()) + 
						" (<i>" + StringEscapeUtils.escapeHtml4(comment.getUserName()) + ", " + comment.getCreateDateTime() + "</i>)</li>\r\n");
			body.append("</ul></p>");
		}
		
		Set<String> users = new HashSet<String>();
		users.add(story.getOwner());
		// include all task developers/testers too?
		
		try {
			sendHtmlMail(retrieveMail(users), subject, body.toString(), "bot@tinyscrum.org");
		} catch (MessagingException e) {
		}
	}

	/**
	 * Reads the comments of the given type.
	 * @param commentType comment type
	 * @param parentId parent id
	 * @return comment list
	 */
	public List<Comment> getComments(CommentType commentType, Long parentId) {
		EntityManager em = Comment.entityManager();
		TypedQuery<Comment> q1 = em.createQuery("FROM Comment WHERE commentType = ? AND parentId = ? ORDER BY createDateTime", Comment.class);
		q1.setParameter(1, commentType);
		q1.setParameter(2, parentId);
		return q1.getResultList();
	}
	
	/**
	 * Looks up the user names and gives a String
	 * array of distinct user e-mail addresses.
	 * @param users user names
	 * @return addresses
	 */
	public String[] retrieveMail(Set<String> users) {
		Set<String> emails = new HashSet<String>(users.size());
		for (String userName : users) {
			ScrumUser user = userManager.getUser(userName);
			if (user != null && !StringUtils.isEmpty(user.getEmail()))
				emails.add(user.getEmail());
		}
		
		return emails.toArray(new String[emails.size()]);
	}

	/**
	 * Sends e-mail.
	 * @param recipients recipient addresses
	 * @param subject mail subject
	 * @param message message body
	 * @param from sender address
	 * @throws MessagingException
	 */
	public void sendHtmlMail(String recipients[], String subject, String message, String from) 
	throws MessagingException {
		if (recipients == null || recipients.length == 0)
			return;
		
	    Properties props = new Properties();
	    props.put("mail.smtp.host", smtpServer);
	    props.put("mail.smtp.port", smtpPort);
	
	    Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress(from));
	
	    InternetAddress[] addressTo = new InternetAddress[recipients.length]; 
	    for (int i = 0; i < recipients.length; i++)
	        addressTo[i] = new InternetAddress(recipients[i]);
	    msg.setRecipients(Message.RecipientType.TO, addressTo);
	   	
	    msg.setSubject(subject);
	    msg.setContent(message, "text/html");
	    
	    /*if (logger.isInfoEnabled())
	    	logger.info("send mail: smtp.host=" + SMTP_SERVER + ", smtp.port=" + SMTP_PORT + ", from=" + from + ", to=" + recipients[0]);*/
	    
	    Transport.send(msg);
	}
	
}
