package com.phellipesander.cursomc.services;


import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.phellipesander.cursomc.entity.Cliente;
import com.phellipesander.cursomc.entity.Pedido;

public interface EmailService {
	//texto plano
	void sendOrderConfirmationEmail(Pedido obj);
	void sendEmail(SimpleMailMessage msg);
	
	//html
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	void sendHtmlEmail(MimeMessage msg);
	void sendNewPasswordEmail(Cliente cliente, String newPassword);
}
