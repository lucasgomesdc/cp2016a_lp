import java.io.*;
import java.lang.*;
import java.util.HashMap;
import java.util.Map;

public class Cp{


   //Hash Tabela de Simbolos
   public static Map<String, String> tS = new HashMap<String, String>();
   public static String path, linha, lex, lex2, token_atual;
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
   
   //Efetua uma busca na hash pelo token desejado, retorna null se não encontrado
   public static String buscaHash(String token){
        return tS.get(token);
   }
   
   //FUNÇÃO PARA VERICAR SE TOKEN JA EXISTE NA TABELA, SE ELE NAO EXISTIR, INSERE O TOKEN
   public static void chamaTabela(){
      if(buscaHash(lex) == null){
         setHash(lex);
      }
   }
   
   public static void quebraLinha() throws IOException{
      if(posLinha == linha.length()-1){
           linha = buffRead.readLine();
           posLinha = 0;  
      }
   }
 
   //Inicializa a Tabela de Simbolos
   public static void inicializarHash(){
      tS.put("final", "final");
      tS.put("int", "int");
      tS.put("byte", "byte");
      tS.put("string", "string");
      tS.put("boolean", "boolean");   
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
   }
   
   public static void verificaTokenAtual(){
      if(token_atual == "atribuicao" || token_atual == "sconstante" || token_atual == "(" || token_atual == ")" || token_atual == "-" || token_atual == "," || token_atual == ";" || token_atual == "+" ||token_atual == "*" || token_atual == "/" || token_atual == "<" || token_atual.charAt(0) == '"') posLinha+=1;
   }
   
   public static void analisadorSintatico()throws IOException{
      //INICIALIZ UMA VARIAVEL AUXILIAR DE TOKENS

      lex2 = "";
      
      // PERCORRE LINHA A LINHA PARA ANALISAR TODOS OS TOKENS
      while( (linha = buffRead.readLine())!= null ){  
           posLinha = 0;
           //System.out.println(linha.length());
           while(posLinha < linha.length()){
            lex = "";
            analisadorLexico();
            //CODIGO();
            
            System.out.println(token_atual);
            
           } 
       }
   }
  
   
   public static void analisadorLexico(){
       token_atual = automatoLexico();
       verificaTokenAtual();
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
                  lex2 = "dconstante";
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
                  lex2 = "sconstante";
                  estado = 2;
                  
                  //lex2 = lex;
                  
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
   
   
   
   //==========================================ANALISADOR SINTATICO==========================
   
   /*
      Procedimento casaToken
      Retorna:
        - verdadeiro se for o token esperado
        - falso se não for o token esperado 
   */
   public static boolean casaToken(String tok_esp){
      boolean resposta = false;
      
      if(token_atual == tok_esp){
         resposta = true;
      }
      return resposta;
   }
   
   public static void CODIGO() throws IOException{   
      if(casaToken("int") || casaToken("byte") || casaToken("string") || casaToken("boolean")){
         analisadorLexico();
         DV();
      }else if(casaToken("final")){
         analisadorLexico();
         DC();
      }else if(casaToken("while")){
         analisadorLexico();
         CR();
      }else if(casaToken("if")){
         analisadorLexico();
         //CT();
      }else if(casaToken(";")){
         //fim da linha
      } else if(casaToken("readln")){
         analisadorLexico();
         CL();
      } else if(casaToken("id")){
         analisadorLexico();
         CA();
      }else{
         System.out.println("ERRO SINTATICO");
      }
   }
   
   //DV -> TIPO (CA);
   public static void DV(){
      if(casaToken("id")){
         analisadorLexico();
         X();
      }else {
         System.out.println("ERRO SINTATICO");
      }
   }
     
   //DC -> final TIPO id{,id};
   public static void DC(){       
      if(casaToken("id")){
         analisadorLexico();
         CA();
      } else{
         System.out.println("ERRO SINTATICO");
      }
   }
   
   
   public static void CA(){
      if(casaToken("<-")){
         analisadorLexico();
         //EXP();
         analisadorLexico();
         if(casaToken(";")){
            //fim da linha
         }else{
            System.out.println("ERRO SINTATICO");
         }
      }else{
         System.out.println("ERRO SINTATICO");
      }
   }
   
   
   // X -> <-EXP[,idX]; | {,idX};
   public static void X(){
      if(casaToken("<-")){
         analisadorLexico();
         //EXP();
         analisadorLexico();
         if(casaToken(",")){
            DV();
         }//else if(casaToken(
      }else if(casaToken(",")){
         analisadorLexico();
         V();
      }else if(casaToken(";")){
         //fim da linha
      }
   }
   
   public static void V(){
      if(casaToken("id")){
         analisadorLexico();
         X();
      }
   }
   
   
   public static void CR() throws IOException{
      if(casaToken("(")){
         analisadorLexico();
         //EXP();
         analisadorLexico();
         if(casaToken(")")){
            analisadorLexico();
            Y();
         }
      }
   }
   
   public static void Y() throws IOException{
      quebraLinha(); //se precisar
      if(casaToken("begin")){
         while(casaToken("endwhile")==false){
            quebraLinha();//se precisar
            analisadorLexico();
            if(casaToken("endwhile")==false){
               CODIGO();
            }
         }
      }else if(casaToken("id")){
         analisadorLexico();
         CA();
      }else if(casaToken("readln")){
         analisadorLexico();
         CL();
      }else if(casaToken("write")){
         analisadorLexico();
         //CE();
      }else if(casaToken("writeln")){
         analisadorLexico();
         //CE();
      }else if(casaToken("if")){
         
      }else if(casaToken("while")){
         analisadorLexico();
         CR();
      }
   }
   
   public static void CT() throws IOException{
      quebraLinha(); //se precisar
      if(casaToken("begin")){
         while(casaToken("endif")==false){
            quebraLinha();//se precisar
            analisadorLexico();
            if(casaToken("endif")==false){
               CODIGO();
            }
         }
         quebraLinha(); //se precisar
         analisadorLexico();
         if(casaToken("else")){
            quebraLinha(); //se precisar
            analisadorLexico();
            //CT_B();
         }//não pode ter estado de erro       
      }else if(casaToken("id")){
         analisadorLexico();
         CA();
      }else if(casaToken("readln")){
         analisadorLexico();
         CL();
      }else if(casaToken("write")){
         analisadorLexico();
         //CE();
      }else if(casaToken("writeln")){
         analisadorLexico();
         //CE();
      }else if(casaToken("if")){
         
      }else if(casaToken("while")){
         analisadorLexico();
         CR();
      }
   }
   
      
   //Comando de Leitura
   public static void CL(){
      if(casaToken("(")){
         analisadorLexico();
         if(casaToken("id")){
            analisadorLexico();
            if(casaToken(")")){ 
               analisadorLexico();
               if(casaToken(";")){
                  //fim da linha
               } else {
                  System.out.println("ERRO SINTATICO");
               } 
            }else {
               System.out.println("ERRO SINTATICO");
            } 
         }else {
            System.out.println("ERRO SINTATICO");
         } 
      }else {
         System.out.println("ERRO SINTATICO");
      } 
   }
   
   
   //FALTA CE E EXPRESSOES
   
   
   
   public static void main(String [] args)throws IOException{
         inicializarHash();
         path = "C:/Users/Lucas/Desktop/cp2016a_lp/exemplo2.l.txt";
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