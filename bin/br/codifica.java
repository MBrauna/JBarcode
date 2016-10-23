/****************************************************************************
 * Autor    : Michel Brauna Rodrigues                      Data: 2016/07/19 *
 * ************************************************************************ *
 * Descrição: Codificação mediante chamada do processo principal (Main)     *
 * 			com passagem dos parâmetros:									*
 *  * codificacao.jar														*
 *  ** Parâmetros:															*
 *    *** codigo    - Chave a ser criptografada       - String    -			*
 *    *** extensao  - Extensão da imagem de retorno   - String    -			*
 *    *** altura    - Altura da imagem de retorno     - Integer   -			*
 *    *** largura   - Largura da imagem de retorno    - Integer   -			*
 *    *** tipo      - Tipo de barcode a ser gerado    - Integer   - 		*
 *    *** saida     - Se saída de texto               - Integer   -			*
 *    *** diretorio - Diretório para salvar os dados  - String    -			*
 *    *** end_ftp   - URL/Porta para acesso ao FTP    - String    -			*
 *    *** user_ftp  - Usuário de conexão ao FTP       - String    -			*
 *    *** senha_ftp - Senha de conexão ao FTP         - String    -			*
 ****************************************************************************/

package br;

// Para criação da variável que irá tratar a imagem
import br.com.novomundo.barcode.common.*;

// Chamada para procedimentos de geração de arquivo em diretórios
import br.gera_arquivo;
import br.gera_base64;

// Chamada de procedimentos de codificação em N formatos de Barcode
import br.codifica_code39;
import br.codifica_code93;
import br.codifica_code128;
import br.codifica_codabar;
import br.codifica_itf;
import br.codifica_qrcode;
import br.codifica_datamatrix;
import br.codifica_aztec;
import br.codifica_pdf417;


public class codifica
{
	// Função principal de codificação à ser executada
	// Chama os métodos de criação de acordo com os parâmetros
	// Retorna um valor de acordo com o que for solicitado.
	
	/**************************
	 * @param p_codigo
	 * @param p_extensao
	 * @param p_altura
	 * @param p_largura
	 * @param p_tipo
	 * @param p_saida
	 * @param p_diretorio
	 * @param p_end_ftp
	 * @param p_user_ftp
	 * @param p_senha_ftp
	 **************************/
	
	public static void executa(String p_codigo
						      ,String p_extensao
						      ,int    p_altura
						      ,int    p_largura
						      ,int    p_tipo
						      ,int    p_saida
						      ,String p_diretorio
						      ,String p_end_ftp
						      ,String p_user_ftp
						      ,String p_senha_ftp
						      )
    /*********************************
     * Método de criação do processo *
     * ***************************** *
     *       Cria o barcode          *
     *********************************/
	{
		// Declaração do BitMatrix para coleta da imagem
		BitMatrix v_resultado = null;
		// Declaração da string de situação do processo
		String    v_retorno   = null;

		// Coleta da imagem
		try
		{
			/****************************************
			 | |      |_[(1) - Code 39]           | |
			 | |      |_[(2) - Code 93]           | |
			 | |      |_[(3) - Code 128]          | |
			 | |      |_[(4) - Codabar]           | |
			 | |      |_[(5) - ITF]               | |
			 | |      |_[(6) - QrCode]            | |
			 | |      |_[(7) - Data Matrix]       | |
			 | |      |_[(8) - Aztec]             | |
			 | |      |_[(9) - PDF 417]           | |
			 ****************************************/
			
			// Switch para o tipo de processo à ser executado
			switch(p_tipo)
			{
			case 1:
				// [(1) - Code 39]
				v_resultado = codifica_code39.code39(p_codigo, p_extensao, p_altura, p_largura);
				break;
			case 2:
				// [(2) - Code 93]
				v_resultado = codifica_code93.code93(p_codigo, p_extensao, p_altura, p_largura);
				break;
			case 3:
				// [(3) - Code 128]
				v_resultado = codifica_code128.code128(p_codigo, p_extensao, p_altura, p_largura);
				break;
			case 4:
				// [(4) - Codabar]
				v_resultado = codifica_codabar.codabar(p_codigo, p_extensao, p_altura, p_largura);
				break;
			case 5:
				// [(5) - ITF]
				v_resultado = codifica_itf.itf(p_codigo, p_extensao, p_altura, p_largura);
				break;
			case 6:
				// [(6) - QrCode]
				v_resultado = codifica_qrcode.qrcode(p_codigo, p_extensao, p_altura, p_largura);
				break;
			case 7:
				// [(7) - Data Matrix]
				v_resultado = codifica_datamatrix.datamatrix(p_codigo, p_extensao, p_altura, p_largura);
				break;
			case 8:
				// [(8) - Aztec]
				v_resultado = codifica_aztec.aztec(p_codigo, p_extensao, p_altura, p_largura);
				break;
			case 9:
				// [(9) - PDF 417]
				v_resultado = codifica_pdf417.pdf417(p_codigo, p_extensao, p_altura, p_largura);
				break;
		    default:
				// [Outros casos]
		    	v_resultado = null;
		    	break;
			}
		}
		catch(NullPointerException e)
		{
			// Emite um erro - Imprime a mensagem de erro
        	System.out.println(e.getMessage());
        	// Finaliza o procedimento
        	return;
		}

		// Verificação do tipo de saída
		try
		{
			switch(p_saida)
			{
			  /******************************************
			   * [(1) - Saída de texto - base64       ] *
			   * [(2) - Saída para diretório - Binário] *
			   * [(3) - Saída para FTP - Binário      ] *
			   * [(4) - Saída de texto - Matrix	      ] *
			   ******************************************/
			case 1:
				// [(1) - Saída de texto - base64]
				v_retorno = gera_base64.base64(v_resultado
									          ,p_extensao
									          );
				break;
			case 2:
				// [(2) - Saída para diretório - Binário]
				v_retorno = gera_arquivo.envia_Destino(v_resultado
													  ,p_extensao
													  ,p_saida
													  ,p_end_ftp
													  ,p_diretorio
													  ,p_user_ftp
													  ,p_senha_ftp
													  );
				break;
			case 3:
				// [(3) - Saída para FTP - Binário      ]
				v_retorno = gera_arquivo.envia_Destino(v_resultado
													  ,p_extensao
													  ,p_saida
													  ,p_end_ftp
													  ,p_diretorio
													  ,p_user_ftp
													  ,p_senha_ftp
													  );
				break;
			case 4:
				// [(4) - Saída de texto Matriz       ]
				System.out.println(v_resultado);
				return;
			default:
				v_retorno = "[Inválido] - <<Erro - BarcodeNM0003>> Opção de saída não é válido ou permitido! Verifique.";
				break;
			}
			
			System.out.println(v_retorno);

			// FInaliza o procedimento
			return;
		}
		catch(NullPointerException e)
		{
			// Emite um erro - Imprime a mensagem de erro
        	System.out.println(e.getMessage());
        	// Finaliza o procedimento
        	return;
		}
	} // public static void executa

	

	
	/*********************
	 * @param p_codigo
	 * @param p_extensao
	 * @param p_altura
	 * @param p_largura
	 * @param p_tipo
	 * @param p_saida
	 * @param p_diretorio
	 *********************/
	// Método de sobrecarga apenas para diretório
	public static void executa(String p_codigo
						      ,String p_extensao
						      ,int    p_altura
						      ,int    p_largura
						      ,int    p_tipo
						      ,int    p_saida
						      ,String p_diretorio
						      )
	{
		codifica.executa(p_codigo
				      	,p_extensao
				      	,p_altura
				      	,p_largura
				      	,p_tipo
				      	,p_saida
				      	,p_diretorio
				      	,null
				      	,null
				      	,null
				      	);
	} // public static void executa
	
	
	/********************
	 * @param p_codigo
	 * @param p_extensao
	 * @param p_altura
	 * @param p_largura
	 * @param p_tipo
	 * @param p_saida
	 ********************/
	// Método de sobrecarga para dados do tipo base64
	public static void executa(String p_codigo
						      ,String p_extensao
						      ,int    p_altura
						      ,int    p_largura
						      ,int    p_tipo
						      ,int    p_saida
						      )
	{
		codifica.executa(p_codigo
				    	,p_extensao
				    	,p_altura
				    	,p_largura
				    	,p_tipo
				    	,p_saida
				    	,null
				    	,null
				    	,null
				    	,null
				    	);
	} // public static void executa
	
	
	public static void main(String[]  args)
	{
		// Declaração das variáveis que receberão os dados
		String 	v_codigo       	= null;
		String 	v_extensao		= null;
		String  v_diretorio     = null;
		String  v_end_ftp       = null;
		String  v_usuario		= null;
		String  v_senha  		= null;
		int		v_altura		= 0;
		int     v_largura		= 0;
		int     v_tipo          = 0;
		int     v_saida         = 0;

		String  v_parametro     = null;
		
		// Loop para coleta dos dados
		for(int i=0;i<args.length;i++)
		{
			// Verifica o tipo de parâmetro informado e coleta o tipo de procedimento à ser chamado
			v_parametro = args[i].toString().toLowerCase().substring(0, 2); // Obtém a primeira casa e percorre até a última

			// Realiza a atribuição dos dados à suas respectivas variáveis
			if(v_parametro.trim().equals("-c"))
			{
				v_codigo 			= args[i].trim().substring(2, args[i].length());
			}
			else if(v_parametro.trim().equals("-e"))
			{
				v_extensao 			= args[i].trim().substring(2, args[i].length()).toUpperCase();
			}
			else if(v_parametro.trim().equals("-a"))
			{
				v_altura			= Integer.parseInt(args[i].trim().substring(2, args[i].length()));
			}
			else if(v_parametro.trim().equals("-l"))
			{
				v_largura			= Integer.parseInt(args[i].trim().substring(2, args[i].length()));
			}
			else if(v_parametro.trim().equals("-t"))
			{
				v_tipo				= Integer.parseInt(args[i].trim().substring(2, args[i].length()));
			}
			else if(v_parametro.trim().equals("-s"))
			{
				v_saida				= Integer.parseInt(args[i].trim().substring(2, args[i].length()));
			}
			else if(v_parametro.trim().equals("-d"))
			{
				v_diretorio			= args[i].trim().substring(2, args[i].length());
			}
			else if(v_parametro.trim().equals("-f"))
			{
				v_end_ftp			= args[i].trim().substring(2, args[i].length());
			}
			else if(v_parametro.trim().equals("-u"))
			{
				v_usuario			= args[i].trim().substring(2, args[i].length());
			}
			else if(v_parametro.trim().equals("-w"))
			{
				v_senha				= args[i].trim().substring(2, args[i].length());
			}
			else
			{
				System.out.println("[Parâmetro] - <<Erro - BarcodeNM0001>> Parâmetro inválido! Processo será encerrado.");
				return;
			}

		} // for(int i=0;i<=args.length;i++)


		// Teste para verificação de parâmetros antes da execução inicial
		if((v_codigo.isEmpty()   || v_codigo.trim().equals(""))                                 || // Verifica se o código foi preenchido
		   (v_extensao.isEmpty() || v_extensao.trim().equals("")) 		 						|| // Verifica se a extensão foi preenchida
		   (v_altura  == 0)                                        								|| // Verifica se a altura foi informada
		   (v_largura == 0)                                        								|| // Verifica se a largura foi informada
		   (v_tipo    == 0)                                        								|| // Verifica o tipo de barcode à ser gerado
		   (v_saida   == 0)                                        								|| // Verifica o tipo de saída à ser gerada
		   (v_saida   == 2 && (v_diretorio.isEmpty() || v_diretorio.trim().equals("")))         ||
		   (v_saida   == 3 && (v_diretorio.isEmpty() || v_diretorio.trim().equals("")) && (v_end_ftp.isEmpty() || v_end_ftp.trim().equals(""))) 
		  )
		{
			System.out.println("[Parâmetro] - <<Erro - BarcodeNM0002>> Parâmetros obrigatórios(Código e/ou extensão) não foram totalmente satisfeitos! Processo será encerrado.");
			return;
		}


		codifica.executa(v_codigo
				        ,v_extensao
				        ,v_altura
				        ,v_largura
				        ,v_tipo
				        ,v_saida
				        ,v_diretorio
				        ,v_end_ftp
				        ,v_usuario
				        ,v_senha
				        );
	}

}
