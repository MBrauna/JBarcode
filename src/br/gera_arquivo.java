/****************************************************************************
 * Autor    : Michel Brauna Rodrigues                      Data: 2016/07/19 *
 * ************************************************************************ *
 * Descrição: Processo de geração de imagem local.   						*
 *  * gera_arquivo.jar														*
 *  ** Parâmetros:															*
 *    *** matriz    - Matrix codificada da imagem     - String    -			*
 *    *** extensao  - Extensão da imagem de retorno   - String    -			*
 *    *** saida     - Se saída de texto               - Integer   -			*
 *    *** end_ftp   - URL/Porta para acesso ao FTP    - String    -			*
 *    *** diretorio - Diretório para salvar os dados  - String    -			*
 *    *** user_ftp  - Usuário de conexão ao FTP       - String    -			*
 *    *** senha_ftp - Senha de conexão ao FTP         - String    -			*
 ****************************************************************************/

package br;

// Bibliotecas importantes para geração dos arquivos locais
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

// Bibliotecas importantes para a conversão da data
import java.util.Date;
import java.text.SimpleDateFormat;

// Biblioteca para uso e leitura da Matriz de dados da imagem e seus hints
import br.com.novomundo.barcode.common.*;
import br.com.novomundo.barcode.MatrixToImageWriter;

public class gera_arquivo
{
	// Remover a mensagem de versão depreciada para a escrita em arquivo
	@SuppressWarnings("deprecation")
	
	/*****************************
	 * @param p_imagem
	 * @param p_extensao
	 * @param p_saida
	 * @param p_endereco
	 * @param p_diretorio
	 * @param p_usuario
	 * @param p_senha
	 * @return
	 *****************************/
	public static String envia_Destino(BitMatrix   p_imagem
									  ,String      p_extensao
			                          ,int	       p_saida
			                          ,String      p_endereco
				                      ,String	   p_diretorio
				                      ,String      p_usuario
				                      ,String      p_senha
				                      )
	{
		// Obtencao da data da geração
		Date				v_data      = new Date();
		SimpleDateFormat 	formatacao  = new SimpleDateFormat("ddmmyyyyhhmmss");


		// Cria uma string para armazenar o diretório da imagem
		String v_diretorio              = p_diretorio + "/barcode_" + p_extensao.toLowerCase() + formatacao.format(v_data) + "." + p_extensao.toLowerCase();

		if(p_diretorio.isEmpty() || p_diretorio.trim().equals(""))
		{
			return "[Inválido] - <<Erro - BarcodeNM0004>>O processo de geração de arquivo não permite geração em diretório nulo! Verifique.";
		} // if(p_diretorio.isEmpty() || p_diretorio.trim().equals(""))

		switch(p_saida)
		{
		case 2:
			// [(2) - Saída para diretório - Binário]
			try
			{
				// Declaração da variável que irá salvar os dados
				File v_salva = new File(v_diretorio);
				
				// Pŕocesso padrão para escrever o arquivo no diretório
				MatrixToImageWriter.writeToFile(p_imagem
						                       ,p_extensao
						                       ,v_salva
						                       );

				// Finaliza o processo
			    return "[Sucesso] - O arquivo foi gerado: " + v_diretorio;
			}
			catch(IOException e)
			{
				// Em caso de erro informa a cusa e encerra o programa
				return "[Erro] - <<Erro - BarcodeNM0005>> Não foi possível salvar o arquivo: " + e.getMessage();
			}
			// Se nenhum dos procedimentos acima finalizar...
		case 3:
			// [(3) - Saída para FTP - Binário      ]
			// Cria uma string contendo os dados de acesso ao FTP
			String v_ftp_url = "ftp://%s:%s@%s/%s;type=i";
			v_ftp_url        = String.format(v_ftp_url
								            ,p_usuario
								            ,p_senha
								            ,p_endereco
								            ,v_diretorio
								            );

			// Inicia o processo de abertura do FTP e geração do arquivo
			try
			{
				// Gera o arquivo e o prepara para a exportação no FTP
				// Declaração da variável que irá salvar os dados
			    FileOutputStream v_salva       = new FileOutputStream(new File("barcode_" + p_extensao.toLowerCase() + formatacao.format(v_data) + "." + p_extensao.toLowerCase()));
			    // Processo padrão para escrever o arquivo
			    MatrixToImageWriter.writeToStream(p_imagem
							                     ,p_extensao
							                     ,v_salva
							                     );             

			    
			    // Inicia a exportação do arquivo
			    // Cria uma URL personalizada de acesso
			    URL             v_url           = new URL(v_ftp_url);
			    // Abre uma conexão para geração do arquivo
			    URLConnection   v_conn          = v_url.openConnection();
			    // Cria uma conexão de transmissão de dados
			    OutputStream    v_outputStream  = v_conn.getOutputStream();
			    
			    FileInputStream v_inputStream   = new FileInputStream("barcode_" + p_extensao.toLowerCase() + formatacao.format(v_data) + "." + p_extensao.toLowerCase());
			    
			    byte[] buffer = new byte[1048576]; // Cria um buffer de 1 mega
			    int bytesRead = -1;
			    while ((bytesRead = v_inputStream.read(buffer)) != -1)
			    {
			    	v_salva.write(buffer, 0, bytesRead);
			    }

			    v_inputStream.close();
			    v_outputStream.close();

			    return "[Sucesso] - O arquivo foi gerado: " + v_diretorio;
			}
			catch (IOException ex)
			{
			    return "[Inválido] - <<Erro - BarcodeNM0008>> Ocorreu um erro durante a escrita do arquivo! " + ex.getMessage();
			}
		default:
			return "[Inválido] - <<Erro - BarcodeNM0006>> O processo de geração de arquivo não é válido ou permitido! Verifique.";
		}
	} // public static String envia_Destino
	

}
