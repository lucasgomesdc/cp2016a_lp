import java.io.*;
import java.lang.*;
import java.util.HashMap;
import java.util.Map;

public class Cp{


   //Hash Tabela de Simbolos
   public static Map<String, String> tS = new HashMap<String, String>();
   public static String path, linha, lex;
   public static int posLinha;
   public static BufferedReader buffRead;   
   
   //construtor da classe
   public Cp(){
      inicializarHash();

   }
   
      
   //insere na Hash a token desejada
   public static void setHash(String token){
       tS.put(token, token);
   }
   
   //Efetua uma busca na hash pelo token desejado, retorna null se não encontrado
   public static String buscaHash(String token){
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
   
   public void analisadorSintatico()throws IOException{
      String token;
      
      while( (linha = buffRead.readLine())!= null ){  
           posLinha = 0;
           while(posLinha != linha.length()){
            token = analisadorLexico();
            System.out.println(token);
            lex = "";
           } 
       }
   }
   
   public static String analisadorLexico(){
       return automatoLexico();  
   }
   
   public static String automatoLexico(){
      int estado = 0;
      
      for(int i = posLinha; i<linha.length()-1;i++){
         switch (estado){
            case 0:
               if(Character.isLetter(linha.charAt(i)) || linha.charAt(i) == '_'){
                  lex += linha.charAt(i);
                  estado = 1;
               }else if(linha.charAt(i) == '|'){
                  lex += linha.charAt(i);
                  estado = 3;
               }else if(linha.charAt(i) == '&'){
                  lex += linha.charAt(i);
                  estado = 4;
               }else if(linha.charAt(i) == '<'){
                  lex += linha.charAt(i);
                  estado = 5;
               }else if(linha.charAt(i) == '>'){
                  lex += linha.charAt(i);
                  estado = 6;
               }else if(linha.charAt(i) == '!'){
                  lex += linha.charAt(i);
                  estado = 7;
               }else if(linha.charAt(i) == '(' || linha.charAt(i) == ')' || linha.charAt(i) == '-' || linha.charAt(i) == '+' || linha.charAt(i) == '*' || linha.charAt(i) == ',' || linha.charAt(i) == ';'){
                  lex += linha.charAt(i);
                  estado = 2;
               }
               //---------FIM CASE 0 ----------
            case 1:
               if(Character.isLetter(linha.charAt(i)) || linha.charAt(i) == '_' || Character.isDigit(linha.charAt(i))){
                  lex += linha.charAt(i);
               }else{
                  estado = 2;
               }
               // -------- FIM CASE 1 ---------
           case 2:
               chamaTabela();
               i--;
               posLinha = i;
               return buscaHash(lex);
              // --------- FIM CASE 2 ----------
           case 3:
               if(linha.charAt(i) == '|'){
                  lex += linha.charAt(i);
                  estado = 2;
               }else{
                  estado = 666; 
               }
               // --------- FIM CASE 3 ----------
            case 4:
               if(linha.charAt(i) == '&'){
                  lex += linha.charAt(i);
                  estado = 2;
               }else{
                  estado = 666; 
               }
               // --------- FIM CASE 4 ----------
            case 5:
               if(linha.charAt(i) == '='){
                  lex += linha.charAt(i);
                  estado = 2;
               }else if(linha.charAt(i) == '-'){
                  lex += linha.charAt(i);
                  estado = 2;               
               }else{
                  estado = 2; 
               }
               // --------- FIM CASE 5 ----------
             case 6:
               if(linha.charAt(i) == '='){
                  lex += linha.charAt(i);
                  estado = 2;              
               }else{
                  estado = 2; 
               }
               // --------- FIM CASE 6 ----------
             case 7:
               if(linha.charAt(i) == '='){
                  lex += linha.charAt(i);
                  estado = 2;              
               }else{
                  estado = 2; 
               }
               // --------- FIM CASE 7 ----------
            }//FIM SWITCH
         }
         return null;
      }
   //FUNÇÃO PARA VERICAR SE TOKEN JA EXISTE NA TABELA, SE ELE NAO EXISTIR, INSERE O TOKEN
   public static void chamaTabela(){
      if(buscaHash(lex) == null){
         setHash(lex);
      }
   }
   
   //Como validar se e' letra ou digit
   public void validar(){
      boolean valida;
      char caractere = 'a';
      valida = Character.isLetter(caractere);
      valida = Character.isDigit(caractere);
   }
   
   public static void main(String [] args)throws IOException{

         path = "C:/Users/Pedro/Documents/FACULDADE/Compilador/cp2016a_lp/exemplo.l.txt";
         //path = args[0];
         buffRead = new BufferedReader(new FileReader(path));
         //System.out.println(path);
         analisadorLexico();
         //while( (linha = buffRead.readLine())!= null ){
            //if(!linha.equals("")){ //IGNORAR QUEBRA DE LINHA NO ARQUIVO
               //System.out.println(linha.length());
            //}
         //}
   }
}