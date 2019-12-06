package rs.projekatOSA2019_maven.etc;

import rs.projekatOSA2019_maven.dto.MessageDTO;
import rs.projekatOSA2019_maven.entity.Message;

public class Util {
	public static void getToCCBccToString(MessageDTO messageDTO, Message message) {
		StringBuffer sb;
		
		if (messageDTO.getTo() != null) {
			sb = new StringBuffer();
			for (int i = 0; i < messageDTO.getTo().size(); i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(messageDTO.getTo().get(i));
				

			} 
			message.setTo(sb.toString());
		} else {
			message.setTo("");
		}
		
		if (messageDTO.getCc() != null) {
			sb = new StringBuffer();
			for (int i = 0; i < messageDTO.getCc().size(); i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(messageDTO.getCc().get(i));
				

			} 
			message.setCc(sb.toString());
			if(message.getCc().equals(""))
				message.setCc(null);
		} else {
			message.setCc(null);
		}
		
		
		if (messageDTO.getBcc() != null) {
			sb = new StringBuffer();
			for (int i = 0; i < messageDTO.getBcc().size(); i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(messageDTO.getBcc().get(i));
				

			} 
			message.setBcc(sb.toString());
			if(message.getBcc().equals(""))
				message.setBcc(null);
		} else {
			message.setBcc(null);
		}
		
		
	}
}
