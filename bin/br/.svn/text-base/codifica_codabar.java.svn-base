/**************************************************************************
 * Autor    : Michel Brauna Rodrigues                    Data: 2016/07/19 *
 * ********************************************************************** *
 * Descrição: Codificador de texto para Codabar                           *
 **************************************************************************/

package br;

// Para escolha do charset evitando problemas de caracteres especiais
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CharacterCodingException;

// Para conversão da chave à ser criptografada
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

// Para geração do arquivo binário
import br.com.novomundo.barcode.Writer;
import br.com.novomundo.barcode.WriterException;
import java.util.Hashtable;
import br.com.novomundo.barcode.EncodeHintType;
import br.com.novomundo.barcode.common.*;
import br.com.novomundo.barcode.BarcodeFormat;

//Para tratamento de imagens
import java.io.UnsupportedEncodingException;


// Para o Codabar
import br.com.novomundo.barcode.oned.CodaBarWriter;

public class codifica_codabar
{

    // Processo principal de codificação para Codabar
    public static BitMatrix codabar(String   p_chave
			                       ,String   p_extensao
			                       ,int      p_altura
			                       ,int      p_largura
			              	       )
    {
    	if(p_chave.isEmpty() || p_chave.trim().equals(""))
    	{
    		// Finaliza e marca como falha (Retorno nulo).
    		return null;
    	}// if(p_chave == null || p_chave.trim().equals(""))

    	// Marca o charset de leitura para UTF-8, assim recebendo caracteres especiais
        Charset meu_charset        = Charset.forName("UTF-8"); // Cria um charset padrão para o tipo UTF-8
        CharsetEncoder meu_encoder = meu_charset.newEncoder(); // Cria o encoder para palavras recebidas
        
        // Limpa a coleção binária que irá comportar a imagem
        byte[] v_b_chave = null;
        
        // Tratamento para a entrada da chave à ser criptografada, verifica se é possível conversão para UTF-8
        try
        {
        	ByteBuffer bbuf = meu_encoder.encode(CharBuffer.wrap(p_chave));
        	v_b_chave       = bbuf.array();
        }
        catch(CharacterCodingException e)
        {
        	// Em caso de falha - Imprime a mensagem de erro
        	System.out.println(e.getMessage());
        	// Finaliza o procedimento
        	return null;
        }
    	
        
        // Verifica se a string pode realmente receber o dado criado em buffer
        // Monta os parâmetros para montagem do processo da imagem
        try
        {
        	// Utiliza da nova string criada em buffer
        	String v_chave     = new String(v_b_chave, "UTF-8");
        	// Cria a variável vazia para inserção do padrão da imagem e configurações
        	BitMatrix v_imagem = null;
        	// Inicia o processo de composição da imagem
        	Writer v_escritor  = new CodaBarWriter();
        	
        	// Verifica se a codificação não resultou em algum problema ou está vazio
        	if (v_chave.isEmpty())
        	{
        		// Caso esteja vazia... finaliza
        		return null;
        	}
        	
        	try
        	{
        		// Cria uma tabela contendo o padrão[ALtura][Largura] com a imagem
                Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>(2);
                // Inicia o processo de codificação da string para imagem no formato UTF-8
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                // Cria a imagem como um bitmatrix, que nada mais é que um cartesiano[ALtura][Largura]
                v_imagem = v_escritor.encode(p_chave
                		                    ,BarcodeFormat.CODABAR
                		                    ,p_largura
                		                    ,p_altura
                		                    ,null
                		                    );
                return v_imagem;
            }
        	catch (WriterException e)
        	{
        		// Emite um erro - Imprime a mensagem de erro
            	System.out.println(e.getMessage());
            	// Finaliza o procedimento
            	return null;
            }
        	
        }
        catch(UnsupportedEncodingException e)
        {
        	// Em caso de falha - Imprime a mensagem de erro
        	System.out.println(e.getMessage());
        	// Finaliza o procedimento
        	return null;
        }

    } // public String codabar
}
