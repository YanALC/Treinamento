package zgsolucoes.webinar.refatoracao1

import java.text.NumberFormat

class Prontuario {
	private String nomePaciente
	private Internacao internacao
	private Set<Procedimento> procedimentos = new HashSet<Procedimento>()

	Prontuario(String nomePaciente) {
		this.nomePaciente = nomePaciente
	}

	void setInternacao(Internacao internacao) {
		this.internacao = internacao
	}

	void addProcedimento(Procedimento procedimento) {
		this.procedimentos.add(procedimento)
	}

	String imprimaConta() {
		def formatter = NumberFormat.currencyInstance

		float valorDiarias = contabilizaDiarias()
		float valorTotalProcedimentos = obtenhaTotalProcedimentos()
		int qtdeProcedimentosBasicos = obtenhaQtdeProcedimentosBasicos()
		int qtdeProcedimentosComuns = obtenhaQtdeProcedimentosComuns()
		int qtdeProcedimentosAvancados = obtenhaQtdeProcedimentosAvancados()

		String conta = montaCabecalho(formatter, valorDiarias, valorTotalProcedimentos)

		conta += montaDadosInternacao(formatter, valorDiarias)
		conta += montaDadosDosProcedimentos(formatter, valorTotalProcedimentos, qtdeProcedimentosBasicos, qtdeProcedimentosComuns, qtdeProcedimentosAvancados)
		conta += montaRodape()

		return conta
	}

	private String montaDadosDosProcedimentos(NumberFormat formatter, float valorTotalProcedimentos, int qtdeProcedimentosBasicos, int qtdeProcedimentosComuns, int qtdeProcedimentosAvancados) {
		String dadosProcedimentos = ""
		if (procedimentos.size() > 0) {
			dadosProcedimentos += "\n\nValor Total Procedimentos:\t\t${formatter.format(valorTotalProcedimentos)}"

			if (qtdeProcedimentosBasicos > 0) {
				dadosProcedimentos += "\n\t\t\t\t\t${qtdeProcedimentosBasicos} procedimento${qtdeProcedimentosBasicos > 1 ? 's' : ''} básico${qtdeProcedimentosBasicos > 1 ? 's' : ''}"
			}

			if (qtdeProcedimentosComuns > 0) {
				dadosProcedimentos += "\n\t\t\t\t\t${qtdeProcedimentosComuns} procedimento${qtdeProcedimentosComuns > 1 ? 's' : ''} comu${qtdeProcedimentosComuns > 1 ? 'ns' : 'm'}"
			}

			if (qtdeProcedimentosAvancados > 0) {
				dadosProcedimentos += "\n\t\t\t\t\t${qtdeProcedimentosAvancados} procedimento${qtdeProcedimentosBasicos > 1 ? 's' : ''} avançado${qtdeProcedimentosAvancados > 1 ? 's' : ''}"
			}
		}
		dadosProcedimentos
	}

	private String montaDadosInternacao(NumberFormat formatter, float valorDiarias) {
		String dadosInternacao = ""
		if (internacao) {
			dadosInternacao += "\n\nValor Total Diárias:\t\t\t${formatter.format(valorDiarias)}"
			dadosInternacao += "\n\t\t\t\t\t${internacao.qtdeDias} diária${internacao.qtdeDias > 1 ? 's' : ''} em ${internacao.tipoLeito == TipoLeito.APARTAMENTO ? 'apartamento' : 'enfermaria'}"
		}
		return dadosInternacao
	}

	private static String montaRodape() {
		String rodape = "\n\nVolte sempre, a casa é sua!"
		rodape += "\n----------------------------------------------------------------------------------------------"
		return rodape
	}

	private String montaCabecalho(NumberFormat formatter, float valorDiarias, float valorTotalProcedimentos) {
		String cabecalho = "----------------------------------------------------------------------------------------------"
		cabecalho += "\nA conta do(a) paciente $nomePaciente tem valor total de __ ${formatter.format(valorDiarias + valorTotalProcedimentos)} __"
		cabecalho += "\n\nConforme os detalhes abaixo:"
		cabecalho
	}

	int obtenhaQtdeProcedimentosAvancados() {
		int qtdeProcedimentosAvancados = 0

		for (Procedimento procedimento in procedimentos) {
			if (procedimento.tipoProcedimento == TipoProcedimento.AVANCADO) {
				qtdeProcedimentosAvancados++
			}
		}

		return qtdeProcedimentosAvancados
	}

	int obtenhaQtdeProcedimentosComuns() {
		int qtdeProcedimentosComuns = 0

		for (Procedimento procedimento in procedimentos) {
			if (procedimento.tipoProcedimento == TipoProcedimento.COMUM) {
				qtdeProcedimentosComuns++
			}
		}

		return qtdeProcedimentosComuns
	}

	int obtenhaQtdeProcedimentosBasicos() {
		int qtdeProcedimentosBasicos = 0

		for (Procedimento procedimento in procedimentos) {
			if (procedimento.tipoProcedimento == TipoProcedimento.BASICO) {
				qtdeProcedimentosBasicos++
			}
		}

		return qtdeProcedimentosBasicos
	}

	float obtenhaTotalProcedimentos() {
		float valorTotalProcedimentos = 0
		for (Procedimento procedimento in procedimentos) {
			switch (procedimento.tipoProcedimento) {
				case TipoProcedimento.BASICO:
					valorTotalProcedimentos += 50.00
					break

				case TipoProcedimento.COMUM:
					valorTotalProcedimentos += 150.00
					break

				case TipoProcedimento.AVANCADO:
					valorTotalProcedimentos += 500.00
					break
			}
		}

		return valorTotalProcedimentos

	}

	private float contabilizaDiarias() {
		float valorDiarias = 0
		switch (internacao?.tipoLeito) {
			case TipoLeito.ENFERMARIA:
				if (internacao.qtdeDias <= 3) {
					valorDiarias += 40.00 * internacao.qtdeDias // Internação Básica
				} else if (internacao.qtdeDias <= 8) {
					valorDiarias += 35.00 * internacao.qtdeDias // Internação Média
				} else {
					valorDiarias += 30.00 * internacao.qtdeDias // Internação Grave
				}
				break

			case TipoLeito.APARTAMENTO:
				if (internacao.qtdeDias <= 3) {
					valorDiarias += 100.00 * internacao.qtdeDias // Internação Básica
				} else if (internacao.qtdeDias <= 8) {
					valorDiarias += 90.00 * internacao.qtdeDias  // Internação Média
				} else {
					valorDiarias += 80.00 * internacao.qtdeDias  // Internação Grave
				}
				break
		}

		return valorDiarias
	}
}
