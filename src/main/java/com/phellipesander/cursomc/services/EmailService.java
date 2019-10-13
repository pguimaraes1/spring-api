package com.phellipesander.cursomc.services;


import org.springframework.mail.SimpleMailMessage;

import com.phellipesander.cursomc.entity.Pedido;

public interface EmailService {
	void sendOrderConfirmationEmail(Pedido obj);
	void sendEmail(SimpleMailMessage msg);
}
