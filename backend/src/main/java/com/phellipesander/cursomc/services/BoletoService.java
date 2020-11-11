package com.phellipesander.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.phellipesander.cursomc.entity.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date InstanteDoBoleto) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(InstanteDoBoleto);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}
	
}
