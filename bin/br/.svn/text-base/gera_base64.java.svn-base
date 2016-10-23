package br;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import br.com.novomundo.barcode.MatrixToImageWriter;
import br.com.novomundo.barcode.common.BitMatrix;

// String qrCodeString = new Base64().encodeToString(out.toByteArray());

public class gera_base64
{
	public static String base64(BitMatrix   p_imagem
							   ,String      p_extensao
				               )
	{

		// Obtém os dados para o streamming da imagem
		ByteArrayOutputStream v_saida = new ByteArrayOutputStream();

		
		// Cria uma imagem com a extensão pré-definida no parâmetro
		try
		{
			// Cria uma streamming da imagem
			MatrixToImageWriter.writeToStream(p_imagem
					                         ,p_extensao
					                         ,v_saida
					                         );

		    // Converte a saída dos dados para byte array
			byte[] v_lista_byte        = v_saida.toByteArray();
			
			// transforma em base64 a imagem pré-definida.
			String v_resultado         = Base64.getEncoder().encodeToString(v_lista_byte);
			
			// Finaliza o procedimento
			return "data:image/" + p_extensao.toLowerCase() + ";base64," + v_resultado;
		}
		catch (IOException e)
		{
			// Em caso de erro retorna uma mensagem de erro
			return "[Inválido] - <<Erro - BarcodeNM0007>> Não foi possível converter a imagem para a base64! " + e.getMessage();
		}
	}
}
