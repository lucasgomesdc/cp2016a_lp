import java.io.*;
import java.lang.*;
import java.util.HashMap;
import java.util.Map;

public class Cp{


   //Hash Tabela de Simbolos
   public static Map<String, String> tS = new HashMap<String, String>();
   public static String path, linha, lex, lex2;
   public static int posLinha;
   public static BufferedReader buffRead;   
   
   //construtor da classe
   public Cp(){
      inicializarHash();

   }
   
      
   //insere na Hash a token desejada
   public static void setHash(String token){
       tS.put(token, lex2);
   }
   
   //Efetua uma busca na hash pelo token desejado, retorna null se n�o encontrado
   public static String buscaHash(String token){
        return tS.get(token);
   }
   
   //FUN��O PARA VERICAR SE TOKEN JA EXISTE NA TABELA, SE ELE NAO EXISTIR, INSERE O TOKEN
   public static void chamaTabela(){
      if(buscaHash(lex) == null){
         setHash(lex);
      }
   }
 
   //Inicializa a Tabela de Simbolos
   public static void inicializarHash(){
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
   
   public static void analisadorSintatico()throws IOException{
      //INICIALIZ UMA VARIAVEL AUXILIAR DE TOKENS
      String token;
      lex2 = "";
      
      // PERCORRE LINHA A LINHA PARA ANALISAR TODOS OS TOKENS
      while( (linha = buffRead.readLine())!= null ){  
           posLinha = 0;
           //System.out.println(linha.length());
           while(posLinha < linha.length()){
            lex = "";
            token = analisadorLexico();
            if(token == "atribuicao"|| token == "(" || token == ")" || token == "-" || token == "," || token == ";" || token == "+" ||token == "*" || token == "/" || token == "<" || token.charAt(0) == '"') posLinha+=1;
            System.out.println(token);
            
           } 
       }
   }
  
   
   public static String analisadorLexico(){
       return automatoLexico();  
   }
   
   public static String automatoLexico(){
      int estado = 0;
      
      for(int i = posLinha; i<=linha.length();i++){
         switch (estado){
            case 0:
               if(Character.isLetter(linha.charAt(i)) || linha.charAt(i) == '_'){
                  lex += linha.charAt(i);
                  estado = 1;
               }else if(Character.isDigit(linha.charAt(i))){
                  lex += linha.charAt(i);
                  estado = 11;
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
               }else if(linha.charAt(i) == '/' ){
                  lex+= linha.charAt(i);
                  estado = 8;
               }else if(linha.charAt(i) == '"'){
                  lex += linha.charAt(i);
                  estado = 13;
               }else if(linha.charAt(i) == ' '){
                  if(i == ( linha.length()-1)){
                     posLinha = i+1;
                     return " ";
                  }
               }
               
               break;
               //---------FIM CASE 0 ----------
            case 1:
               if(Character.isLetter(linha.charAt(i)) || linha.charAt(i) == '_' || Character.isDigit(linha.charAt(i))){
                  lex += linha.charAt(i);
                  if(i == (linha.length()-1)){
                     chamaTabela();
                     i++;
                     posLinha = i;
                     return buscaHash(lex);
                  }
               }else{
                  lex2 = "id";
                  estado = 2;
               }
               break;
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
               break;
               // --------- FIM CASE 3 ----------
            case 4:
               if(linha.charAt(i) == '&'){
                  lex += linha.charAt(i);
                  estado = 2;
               }else{
                  estado = 666; 
               }
               break;
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
               break;
               // --------- FIM CASE 5 ----------
             case 6:
               if(linha.charAt(i) == '='){
                  lex += linha.charAt(i);
                  estado = 2;              
               }else{
                  estado = 2; 
               }
               break;
               // --------- FIM CASE 6 ----------
             case 7:
               if(linha.charAt(i) == '='){
                  lex += linha.charAt(i);
                  estado = 2;              
               }else{
                  estado = 2; 
               }
               break;
               // --------- FIM CASE 7 ----------
             case 8:
               if(linha.charAt(i) == '*'){
                  estado = 9;              
               }else{
                  lex += linha.charAt(i);              
                  estado = 2; 
               }
               break;
               // --------- FIM CASE 8 ----------
             case 9:
               if(linha.charAt(i) != '*'){
                  lex = "";
                  estado = 9;              
               }else{              
                  estado = 10; 
               }
               break;
               // --------- FIM CASE 9 ----------
             case 10:
               if(linha.charAt(i) == '/'){
                  i++;
                  posLinha = i;
                  return "comentario";              
               }else if(linha.charAt(i) == '*'){              
                  estado = 10; 
               }else{
                  estado = 9;
               }
               break;
             // --------- FIM CASE 10 ----------
             case 11:
               if(Character.isDigit(linha.charAt(i))){
                  lex += linha.charAt(i);
                  estado = 11;   
               }else if(linha.charAt(i) == '.'){              
                  estado = 12; 
               }else{
                  estado = 2;
               }
               break;
             // --------- FIM CASE 11 ----------
             case 12:
               if(Character.isDigit(linha.charAt(i))){
                  lex += linha.charAt(i);
                  estado = 12;   
               }else if(linha.charAt(i) != '.' && !(Character.isDigit(linha.charAt(i)))){              
                  estado = 2; 
               }else{
                  estado = 666;
               }
               break;
             // --------- FIM CASE 12 ----------
             case 13:
               if(linha.charAt(i) != '"'){
                  lex += linha.charAt(i);
                  estado = 13;    
               }else{
                  lex += linha.charAt(i);
                  estado = 2;
                  lex2 = lex;
               }
               break;
             // --------- FIM CASE 13 ----------
             case 666:
                  System.out.println("ERRO");
                  break;
             // --------- FIM CASE 666 ----------
             default:
               System.out.println("ERRO");
      
            }//FIM SWITCH
         }
         return null;
      }

   
   //Como validar se e' letra ou digit
   public void validar(){
      boolean valida;
      char caractere = 'a';
      valida = Character.isLetter(caractere);
      valida = Character.isDigit(caractere);
   }
   
   public static void main(String [] args)throws IOException{
         inicializarHash();
         path = "C:/Users/Pedro/Documents/FACULDADE/Compilador/cp2016a_lp/exemplo2.l.txt";
         //path = args[0];
         buffRead = new BufferedReader(new FileReader(path));
         //System.out.println(path);
         analisadorSintatico();
         //while( (linha = buffRead.readLine())!= null ){
            //if(!linha.equals("")){ //IGNORAR QUEBRA DE LINHA NO ARQUIVO
               //System.out.println(linha.length());
            //}
         //}
   }
}