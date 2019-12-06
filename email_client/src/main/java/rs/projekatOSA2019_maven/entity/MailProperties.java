package rs.projekatOSA2019_maven.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties (prefix = "spring.mail")
public class MailProperties {
	
	private String host;
	private Integer port;
	private String userName;
	private String password;
}
