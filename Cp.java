import java.io.*;
import java.lang.*;
import java.util.HashMap;
import java.util.Map;

public class Cp{


   //Hash Tabela de Simbolos
   Map<String, String> tS = new HashMap<String, String>();
   public static String path;
   public static BufferedReader buffRead;   
   
   //construtor da classe
   public Cp(){
      inicializarHash();

   }
   
      
   //insere na Hash a token desejada
   public void setHash(String token){
       tS.put(token, token);
   }
   
   //Efetua uma busca na hash pelo token desejado, retorna null se não encontrado
   public String buscaHash(String token){
        return tS.get(token);
   }
   
 
   //Inicializa a Tabela de Simbolos
   public void inicializarHash(){
      tS.put("final", "final");
      tS.put("int", "int");
      tS.put("byte", "byte");
      tS.put("string", "string");
      tS.put("while", "while");
      tS.put("if", "if");
      tS.put("else", "else");
      tS.put("&&", "logica");
      tS.put("||", "logica");
      tS.put("!", "negacao");
      tS.put("<-", "atribuicao");
      tS.put("(", "(");
      tS.put(")", ")");
      tS.put("<", "comparacao");
      tS.put("<=", "comparacao");
      tS.put(">", "comparacao");
      tS.put(">=", "comparacao");
      tS.put("!=", "comparacao");
      tS.put("=", "comparacao");
      tS.put(",", ",");
      tS.put("+", "+");
      tS.put("*", "*");
      tS.put("/", "/");
      tS.put("-", "-");
      tS.put(";", ";");
      tS.put("begin", "begin");
      tS.put("endwhile", "endwhile");
      tS.put("endif", "endif");
      tS.put("endelse", "endelse");
      tS.put("readln", "readln");
      tS.put("write", "write");
      tS.put("writeln", "writeln");
      tS.put("TRUE", "TRUE");
      tS.put("FALSE", "FALSE");
      tS.put("boolean", "boolean");   
   }
   
   
   
   //Como validar se e' letra ou digit
   public void validar(){
      boolean valida;
      char caractere = 'a';
      valida = Character.isLetter(caractere);
      valida = Character.isDigit(caractere);
   }
   
   public static void main(String [] args)throws IOException{
         
         path = "C:/Users/Pedro/Documents/FACULDADE/Compilador/cp2016a_lp";
         buffRead = new BufferedReader(new FileReader(path));
}