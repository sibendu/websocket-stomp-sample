package com.example.messagingstompwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MyServiceController {

	private SimpMessagingTemplate template;

    @Autowired
    public MyServiceController(SimpMessagingTemplate template)
    {
        this.template = template;
    }
	
	@MessageMapping("/price/{sessionId}")
	public void sendMessage(@DestinationVariable String sessionId, @Payload HelloMessage message) throws Exception {
		
		String msg = "Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!";
		Greeting greet = new Greeting(msg);
		template.convertAndSend("/topic/price/" + sessionId, greet);
		
		Thread.sleep(2000); // simulated delay

		msg = "Hello again, " + HtmlUtils.htmlEscape(message.getName()) + "!";
		greet = new Greeting(msg);
		template.convertAndSend("/topic/price/" + sessionId, greet);
		
		Thread.sleep(2000); // simulated delay

		msg = "Bye, " + HtmlUtils.htmlEscape(message.getName()) + "!";
		greet = new Greeting(msg);
		template.convertAndSend("/topic/price/" + sessionId, greet);
	}

}
