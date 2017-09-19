package com.ts.zx.ig;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class AQHandler {
	
	private static Connection conn;
	private static Session ses;
	
	static{
		ini();
	}
	
	private static void ini(){
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("failover:(tcp://127.0.0.1:61616)");
		try {
			conn = factory.createConnection();
			ses = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Serious exception happened.");
		}
	}
	
	public static void send(String message) throws JMSException{
		
		MessageProducer producer = ses.createProducer(ses.createQueue("IN.IP.EQUIPMENT"));
		producer.send(ses.createTextMessage(message));
		
	}
	

}
