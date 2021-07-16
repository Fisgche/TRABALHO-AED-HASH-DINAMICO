/*
Este programa consiste em um sistema de cadastro de prontuários 
para uma empresa de planos de saúde usando arquivo de dados sequencial indexado com índice 
baseado em hashing dinâmico e opções de inserção, alteração, exclusão e impressão.

Na escolha 6, será executado um teste de inserção de 1 até 10000 inteiros no arquivo 
de índice, para ser marcado o tempo dessa execução e depois será recuperado essas inserções
marcando também o tempo de recuperação

Implementado por Igor Moreira Righi e Gustavo de Oliveira Fisher 
05/2021
*/

import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;

////////////////////////////////////////////////////////////////////////////////////
public class App {
    
  public static int menu(){//MENU de escolhas

        System.out.println(">>>>Menu de Escolhas<<<<");
        System.out.println("0---Sair do programa");
        System.out.println("1---criar arquivo");
        System.out.println("2---inserir registro");
        System.out.println("3---editar registro");
        System.out.println("4---remover registro");
        System.out.println("5---imprimir arquivos");
        System.out.println("6---simulação");

        Scanner entrada=new Scanner(System.in);
        int escolha=entrada.nextInt();
        System.out.println();
        return escolha;
    }
    ////////////////////////////////////////////////////////////////////////////////////
    public static long verificaPosDir(long diretorio[], int cpf, int tamanho){//Fun;áo para verificar a posi;áo no diretório(Hash)
      int resto=cpf%tamanho;
      return diretorio[resto];
    }
    ////////////////////////////////////////////////////////////////////////////////////
    public static long escritaPrincipal(Usuario l){
      //Função para escrever no arquivo mestre
      RandomAccessFile arq;
      byte ba[];
      long pos1=0;
      try{
      arq = new RandomAccessFile("dados/Banco.db", "rw");
      pos1 = arq.length();
      arq.seek(pos1);
      ba = l.toByteArray();
      arq.writeInt(ba.length);
      arq.write(ba);
      arq.close();
      
    }catch (IOException e) {
        e.printStackTrace();
      }
      return pos1;//retona a posição do registro que foi escrito no arquivo mestre
    }
  ////////////////////////////////////////////////////////////////////////////////////
  public static void leituraPrincipal1(Usuario l){//Funão que le o arquivo mestre
    /*Recebe um usuário para fazer a leitura do arquivo
    Essa leitura irá imprimir todos os dados do arquivo que ja foram inseridos,
    inclusive os que ja foram removidos, pois a remoção não acontece no arquivo 
    mestre e sim no arquivo de indice.
    */
    System.out.println(">>>>ARQUIVO MESTRE<<<<");
    RandomAccessFile arq;
    byte ba[];
    int tam;
    long i=0;
    try{
      arq = new RandomAccessFile("dados/Banco.db", "rw");
    arq.seek(0);
    long pos1 = arq.length();
    while(pos1>i){
      tam = arq.readInt();
      ba = new byte[tam];
    arq.read(ba);
    l.fromByteArray(ba);
    System.out.println(l);//imprime o dado lido
    i=i+4+tam; 
    }
    System.out.println();
    arq.close();
  }catch (IOException e) {
    e.printStackTrace();
  }
}
public static void leituraPrincipal(Usuario l,indice n){//Funão que le o arquivo mestre
  /*Recebe um usuário e um indice que contém uma posição no arquivo mestre e um cpf 
   onde move-se o ponteiro para essa posição e imprime o dado que contém la
   essa impressão desconsidera algum dado excluído pois depende de uma posição,
   que está armazenada no arquivo de indices, para imprimir o dado
    */
  
  RandomAccessFile arq;
  byte ba[];
  int tam;
  long i=0;
  int ver=0;//verificador
  
  try{
    arq = new RandomAccessFile("dados/Banco.db", "rw");
  for(int j=0;j<4;j++){
  if(j==0){//se j for 1 é o primeiro cpf
  //se algum cpf ofr zero, não irá imprimir
    if(n.cpf1!=0){
    System.out.println("--------------------------------");
    System.out.print("CPF: "+ n.cpf1);
  arq.seek(n.registro1);//move o arquivo mestre para a posição do dado
  ver=1;
}
  }
  else if(j==1){//se j for 2 é o segundo cpf
  if(n.cpf2!=0){
    System.out.println("--------------------------------");
    System.out.print("CPF: "+ n.cpf2);
    arq.seek(n.registro2);//move o arquivo mestre para a posição do dado
  ver=1;  
}
  }
  else if(j==2){//se j for 3 é o terceiro cpf
  if(n.cpf3!=0){
    System.out.println("--------------------------------");
    System.out.print("CPF: "+ n.cpf3);
    arq.seek(n.registro3);//move o arquivo mestre para a posição do dado
  ver=1;  
}
  }
  else if(j==3){//se j for 4 é o quarto cpf
  if(n.cpf4!=0){
    System.out.println("--------------------------------");
    System.out.print("CPF: "+ n.cpf4);
    arq.seek(n.registro4);//move o arquivo mestre para a posição do dado
  ver=1;  
}
  }
  if(ver==1){//se o verificador for 1 significa que foi impresso o cpf e que tem uma posição no arquivo mestre para ser impresso o dado 
  //le o dado e imprime
  tam = arq.readInt();
  ba = new byte[tam];
  arq.read(ba);
  l.fromByteArray(ba);
  System.out.println(l);
  ver=0;
  System.out.println();
}
  }
  arq.close();
}catch (IOException e) {
  e.printStackTrace();

}
}
////////////////////////////////////////////////////////////////////////////////////
public static int CriaDiretorio(){//Função para criar o diretório
  diretorio novo=new diretorio();
  RandomAccessFile arq;
  RandomAccessFile arq1;
  indice n=new indice(0);
  try{
    arq = new RandomAccessFile("dados/diretorio.db", "rw");
    if(arq.length()>0){//se o ponteiro do arquivo de diretorio for maior que 0 significa que ele ja foi criado
      arq.close();
      return 1;
      }
    else if(arq.length()==0){//senão será criado também o arquivo de índice com dois buckets inicializados com zeros nos registros e cpfs
      arq1 = new RandomAccessFile("dados/indice.db", "rw");
      byte ba[];
      byte bb[];
      long pos=0;
      long pos1=0;
      for(int i=0;i<2;i++){//2 buckets
      pos1 = arq1.length();
      pos = arq.length();
      arq1.seek(pos1);
      arq.seek(pos);
      n=new indice(1);//1 é o p'
      ba = n.toByteArray();
      arq1.writeInt(ba.length);
      arq1.write(ba);
      //pos1 é a posição que o bucket foi inserido no arquivo de índices
      novo=new diretorio(pos1);
      //insere no diretório a pos1
      bb = novo.toByteArray();
      arq.writeInt(bb.length);
      arq.write(bb);
      }
      arq1.close();
      arq.close();
      return 1;
    }
    arq.close();
  }catch (IOException e) {
    e.printStackTrace();
  }
  //se tudo der certo retorna 1 se não retorna 0;  
  return 0;
}  
////////////////////////////////////////////////////////////////////////////////////
 
  public static int TamanhoDiretorio(){//Função que verifica o tamanho do diretório, a quantidade de dados no diretório 
    int verificador=CriaDiretorio();//primeiramente verifica se o diretório está criado
    int tamanho=0;
    if(verificador==1){//se sim eu vou ler todos os dados do diretorio e vou contar a quantidade de dados lidos
    RandomAccessFile arq;
    diretorio l=new diretorio();
    byte ba[];
    int tam;
    long i=0;
    try{
      arq = new RandomAccessFile("dados/diretorio.db", "rw");
    arq.seek(0);
    long pos1=arq.length();
    while(pos1>i){
      tam = arq.readInt();
      ba = new byte[tam];
    arq.read(ba);
    l.fromByteArray(ba);
    i=i+4+tam; 
    tamanho=tamanho+1;//contando a quantidade de dados lida
  }
    arq.close();
  }catch (IOException e) {
    e.printStackTrace();
  }
  return tamanho;//retorna o tamanho do diretorio
  }
    return 0;
  }
  ////////////////////////////////////////////////////////////////////////////////////
  public static void DuplicaDir(int t){
    //método para duplicar o diretório, recebe como parâmetro o tamanho atual do diretório
    long vetor[]=new long[t];//vetor que conterá os ponteiros do diretório
    RandomAccessFile arq;
    diretorio l=new diretorio();
    byte ba[];
    byte bb[];
    int tam;
    long i=0;
    int cont=0;
    try{
      arq = new RandomAccessFile("dados/diretorio.db", "rw");
    arq.seek(0);
    long pos1 = arq.length();
    long pos2=arq.length();//ultima posição no arquivo de diretório 
    //le os dados do diretório e armazena no vetor long
    while(pos1>i){
      tam = arq.readInt();
      ba = new byte[tam];
    arq.read(ba);
    l.fromByteArray(ba);
    i=i+4+tam;
    vetor[cont]=l.convertlong();
    cont++;
    }
    for(int j=0;j<cont;j++){
      /*duplica diretório faz um for indo de 0 até o tamnho do diretório atual
      em que vai inserindo cada posição do arquivo de long no diretório e assim 
      duplica ele
      */
      pos1=arq.length();
      arq.seek(pos1);//vai para o final do arquivo
      l=new diretorio(vetor[j]);//insere os ponteiros que estão no vetor
      bb = l.toByteArray();
      arq.writeInt(bb.length);
      arq.write(bb);

    }
    arq.close();
  }catch (IOException e) {
    e.printStackTrace();
  }
  }
  ////////////////////////////////////////////////////////////////////////////////////
  public static long CriaBucket(int PLinha){
    //método que cria um novo Bucket, recebe como parâmetro p'
    RandomAccessFile arq;
    byte ba[];
    long pos1=0;
    indice novo=new indice(PLinha);//cria o objeto
    try{
    //insere o novo objeto criado na ultima posição do arquivo de indices 
      arq = new RandomAccessFile("dados/indice.db", "rw");
    pos1 = arq.length();
    arq.seek(pos1);
    ba = novo.toByteArray();
    arq.writeInt(ba.length);
    arq.write(ba);
    
    arq.close();
    
  }catch (IOException e) {
      e.printStackTrace();
    }
   return pos1;//retorna a posição do bucket inserido no arquivo de índices
  }
  ////////////////////////////////////////////////////////////////////////////////////
  public static void MudaDiretorio(long pos, int i){
    /*Método utilizado para mudar um ponteiro no arquivo de diretório
    recebe como parâmetro a posição que deseja colocar no diretorio e um inteiro indicando qual será o ponteiro que irá mudar
    */
    RandomAccessFile arq;
    diretorio l=new diretorio();
    byte ba[];
    byte bb[];
    int tam;
    long p=0;
    try{
      arq = new RandomAccessFile("dados/diretorio.db", "rw");
      arq.seek(0);
      for(int j=0;j<i+1;j++){//for para chegar no ponteiro que deseja mudar
      p=arq.getFilePointer();
      tam = arq.readInt();
      ba = new byte[tam];
      arq.read(ba);
      l.fromByteArray(ba);
    }
      l.mudaLong(pos);//muda a posição no objeto
      arq.seek(p);//coloca o ponteiro do arquivo de diretório na posição que irá ser inserido o objeto com o ponteiro atualizado
      bb = l.toByteArray();
      arq.writeInt(bb.length);
      arq.write(bb);//insere esse objeto com o ponteiro atualizado
      arq.close();

    }catch (IOException e) {
      e.printStackTrace();
      }


  }
  ////////////////////////////////////////////////////////////////////////////////////
  public static void WriteIndice(indice n,long i){
    /*método para escrever no arquivo de índices
    Recebe como parâmetro um objeto com o Bucket e um ponteiro de onde esse Bucket será inserido
    */
    byte ba[];
    RandomAccessFile arq;
    try{
      arq = new RandomAccessFile("dados/indice.db", "rw");
      arq.seek(i);//move o ponteiro para a posição para onde esse Bucket será inserido
      ba = n.toByteArray();
      arq.writeInt(ba.length);
      arq.write(ba);//insere o objeto no arquivo
      arq.close();
    }catch (IOException e) {
      e.printStackTrace();
      }
  }
  ////////////////////////////////////////////////////////////////////////////////////
  public static indice ReadIndice(indice l, long pos){
    /*método para ler arquivo de índices, não imp rime conteúdo
    Recebe como parâmetro um objeto com o Bucket e um ponteiro de onde esse Bucket será lido
    */
  RandomAccessFile arq;
  byte ba[];
  int tam;
  try{
    arq = new RandomAccessFile("dados/indice.db", "rw");
    arq.seek(pos);//move o ponteiro para a posição de onde o Bucket será lido
    tam = arq.readInt();
    ba = new byte[tam];
    arq.read(ba);//le o conteudo do arquivo
    l.fromByteArray(ba);
    arq.close();
  }catch (IOException e) {
    e.printStackTrace();
    }
    return l;
  }
  ////////////////////////////////////////////////////////////////////////////////////
  public static void escritaIndice(long retorno,int cpf,long pos, int tamanho,long vetor[], int p){
    /*método para escrever no arquivo de índice
    Recebe como parâmetro um ponteiro que será inserido 
    junto com o cpf, uma posição de onde esse cpf será isnerido
    o vetor dos ponteiros do diretório e um inteiro que é o p
    */
      int QualBucket=cpf%tamanho; 
      int e;  
      int ret=0;
      int ver=0;
      indice l=new indice(0);
      indice n=new indice(0);
      RandomAccessFile arq;
      byte ba[];
      byte bb[];
      int tam;
      long i;
      int TDir;
      int c[]=new int[5];
      long registro[]=new long[4];
      l=ReadIndice(l,pos);
    ret=l.VerificaLugarNoBucket(cpf,retorno,p);//verifica se possui lugar no bucket de opnde o cpf será inserido
    if(ret==-1){//se não possuir lugar no bucket, tem que dobrar o diretorio
      while(ver!=1){
      DuplicaDir(tamanho);//dobra o diretório
      TDir=TamanhoDiretorio();//pega o tamanho do diretorio
      i=CriaBucket(l.Plinha);//cria um novo bucket
      int cont=0;
      int j;
      vetor=new long[TDir];//cria um novo vetor para ler o diretório duplicado
      vetor=Diretorio(TDir);//le o novo diretório
      for(j=0;j<TDir;j++){
        if(vetor[j]==pos){
          cont++;
          if(cont==2)
            break;
        }
      }
      c=l.VerificaCpf(j, TDir);//verifica os cpfs que vão continuar nesse bucket, os que saírem ficam armazenados em um vetor
      registro=l.VerificaRegistros(c);//muda os ponteiro dos cpf que irão sair armazenando em um vetor
      l.reordenaCpf();//chama função para reordenar os cpfs, evitando ter um com 0 no meio de dois
      WriteIndice(l,pos);//escreve esse bucket de novo no arquivo de índices
      indice novo=new indice(p+1,c[1],registro[0],c[2],registro[1],c[3],registro[2],c[4],registro[3]);
      novo.reordenaCpf();//cria novo objeto com os cpfs que saíram do antigo bucket
      WriteIndice(novo,i);//escreve esse novo bucket na posição que ele foi criado anteriormente
      MudaDiretorio(i,j);//muda no diretório o ponteiro desse novo bucket criado 
      long dir[]=new long[TDir];
      dir=Diretorio(TDir);//le o diretorio novamente
      i=verificaPosDir(dir, cpf,TDir);//verifica o cpf que deseja inserir se agora, com o diretõrio duplicado, possui lugar para ele
      n=ReadIndice(n,i);//le o arquivo de indices
      ver=n.VerificaLugarNoBucket(cpf,retorno,p);//armazena o cpf no bucket correto
      if(ver==1)
      WriteIndice(n,i);
    }
  }
    if(ret==0)//Bucket está lotado, não precisa dobrar o diretório, mas tem que criar outro Bucket
    {
      while(ver!=1){
      TDir=TamanhoDiretorio();//pega tamanho do diretorio
      i=CriaBucket(l.Plinha);//cria um novo bucket
      int cont=0;
      int j;
      vetor=new long[TDir];
      vetor=Diretorio(TDir);//le o diretorio e armazena no arquivo
      for(j=0;j<TDir;j++){
        if(vetor[j]==pos){
          cont++;
          if(cont==2)
            break;
        }
      }
      c=l.VerificaCpf(j, TDir);//verifica os cpfs que vão continuar nesse bucket, os que saírem ficam armazenados em um vetor
      registro=l.VerificaRegistros(c);//muda os ponteiro dos cpf que irão sair armazenando em um vetor
      l.reordenaCpf();//chama função para reordenar os cpfs, evitando ter um com 0 no meio de dois
      WriteIndice(l,pos);//escreve esse bucket de novo no arquivo de índices
      indice novo=new indice(p,c[1],registro[0],c[2],registro[1],c[3],registro[2],c[4],registro[3]);
      novo.reordenaCpf();//cria novo objeto com os cpfs que saíram do antigo bucket
      WriteIndice(novo,i);//escreve esse novo bucket na posição que ele foi criado anteriormente
      MudaDiretorio(i,j);//muda no diretório o ponteiro desse novo bucket criado 
      long dir[]=new long[TDir];
      dir=Diretorio(TDir);//le o diretorio novamente
      i=verificaPosDir(dir, cpf,TDir);//verifica o cpf que deseja inserir se agora, com o diretõrio duplicado, possui lugar para ele
      n=ReadIndice(n,i);//le o arquivo de indices
      ver=n.VerificaLugarNoBucket(cpf,retorno,p);//armazena o cpf no bucket correto
      if(ver==1)
      WriteIndice(n,i);
  }
}
    else if(ret==1)//posso inserir o novo dado no arquivo de indice
    {
      WriteIndice(l,pos);
    }
  }
  ////////////////////////////////////////////////////////////////////////////////////
  public static void leituraIndice1(){//método para ler o arquivo de índices 2 imprimindo o conteúdo
  System.out.println(">>>>ARQUIVO INDICE<<<<");
  indice l=new indice(0);
  RandomAccessFile arq;
  byte ba[];
  int tam;
  long i=0;
  try{
    arq = new RandomAccessFile("dados/indice.db", "rw");
  arq.seek(0);//move o ponteiro para a posição 0
  long pos1 = arq.length();
  while(pos1>i){//le os dados
    tam = arq.readInt();
    ba = new byte[tam];
  arq.read(ba);
  l.fromByteArray(ba);
  System.out.println(l);//imprime
  i=i+4+tam; 
  }
  System.out.println();
  arq.close();
}catch (IOException e) {
  e.printStackTrace();
}
  }
  
  public static void leituraIndice(){
    /*método para ler o arquivo de índices 1 imprimindo o conteúdo relacionando 
    com os dados do arquivo mestre, ou seja, será impresso o cpf de cada usuário 
    jundo com o dado pertencente a ele do arquivo mestre
    */
    Usuario novo=new Usuario();
    indice l=new indice(0);
    RandomAccessFile arq;
    byte ba[];
    byte bb[];
    int tam;
    long i=0;
    System.out.println(">>>>ARQUIVO MESTRE<<<<");
    try{
      arq = new RandomAccessFile("dados/indice.db", "rw");
    arq.seek(0);//move o ponteiro para a posição zero
    long pos1 = arq.length();//ponteiro da ultima posição do arquivo
    while(pos1>i){
      tam = arq.readInt();
      ba = new byte[tam];
    arq.read(ba);
    l.fromByteArray(ba);
    leituraPrincipal(novo, l);//le e imprime o dado do arquivo mestre relacionado ao cpf
    i=i+4+tam; 
    }
    System.out.println(">>>>ARQUIVO INDICE<<<<");
    arq.seek(0);//move o ponteiro para a posição zero
    pos1 = arq.length();
    i=0;
    while(pos1>i){
    tam = arq.readInt();
    bb = new byte[tam];
    arq.read(bb);
    l.fromByteArray(bb);
    System.out.println(l);//imprime o aqruivo de indice separado do arquivo mestre
    i=i+4+tam; 
    }
    System.out.println();
    arq.close();
  }catch (IOException e) {
    e.printStackTrace();
  } 
}
  ////////////////////////////////////////////////////////////////////////////////////
  public static boolean removeCpf(int cpf, long pos){
  /*método usado para remover um cpf
    recebe como parâmetro um cpf que deseja excluir e a posição desse cpf
  */
  int a=0;
  indice novo=new indice(0); 
  novo=ReadIndice(novo,pos);//chama função para ler o bucket que está na posição do cpf desejado
  a=novo.ExcluiCpf(cpf);//chama função para excluir esse cpf, colocando zero nele
  
  if(a==1){
  WriteIndice(novo,pos);//reescreve o bucket com o cpf como zero na posição que ele estava antes  
  return true;//se deu para remover retorna verdadeiro
  }
  else
    return false;//se não retorna falso, se não encontrou o cpf ou se não pode remover
  }
  ////////////////////////////////////////////////////////////////////////////////////
  public static boolean atualizaDados(int cpf, long pos, long posArqMestre, Usuario b){
    /*método utilizado para atualizar os dados do arquivo mestre
    recebe como parâmetro um cpf de quem deseja atualizar od dados
    um posição desse cpf, uma posição desse cpf no arqui mestre e um 
    Usuário com os dados atualizados.
    */
  int a=0;
  long retorno=0;
  indice novo=new indice(0);
  novo=ReadIndice(novo,pos);//chama função para ler o bucket que esta na posição do cpf desejado
  a=novo.ExisteCpf(cpf);//função para verificar se o cpf a que deseja atualizar os dados existe
  if(a==1){//se o cpf existe
  retorno=escritaPrincipal(b);//chama função para escreve o dado atualizado no fim do arquivo
  a=novo.atualizaPonteiro(cpf,retorno);//chama função para atualizar o ponteiro do cpf para a posição retorno
}
    else //se não a recebe 0
      a=0;
if(a==1){//se a continuar 1 deu tudo certo na atualização
  WriteIndice(novo,pos);//escreve o cpf com o ponteiro atualizado  
  return true;//retorna verdadeiro se deu tudo certo na atualização
  }
  else
    return false;//retorna falso se deu errado
  }
  public static long[] Diretorio(int tamanho){
    /*método utilizado para ler o diretório para sempre ficar carregado em memória principal
    Recebe como parâmetro um vetor de long

    */
  long vetor[]=new long[tamanho];
  RandomAccessFile arq;
    diretorio l=new diretorio();
    byte ba[];
    int tam;
    long i=0;
    int cont=0;
    try{
    arq = new RandomAccessFile("dados/diretorio.db", "rw");
    arq.seek(0);//move o ponteiro para a poisção 0
    long pos1 = arq.length();
    while(pos1>i){
      tam = arq.readInt();
      ba = new byte[tam];
    arq.read(ba);
    l.fromByteArray(ba);//lê o dado do arquivo
    vetor[cont]=l.convertlong(); //armazena o dado lido no vetor de long
    i=i+4+tam;
    cont++;
  }
  arq.close();
}catch (IOException e) {
  e.printStackTrace();
}
  return vetor;//retorna o vetor
  }
  ///////////////////////////////////////////////
  public static void leituraDiretorio(){
    /*método utilizado para imprimir o conteúdo do diretório
    */
    System.out.println(">>>>ARQUIVO DIRETORIO<<<<");
    RandomAccessFile arq;
    diretorio l=new diretorio();
    byte ba[];
    int tam;
    long i=0;
    try{
      arq = new RandomAccessFile("dados/diretorio.db", "rw");
    arq.seek(0);//move o ponteiro para a posião 0 do arquivo
    long pos1 = arq.length();
    while(pos1>i){
      tam = arq.readInt();
      ba = new byte[tam];
    arq.read(ba);
    l.fromByteArray(ba);//lê o dado
    System.out.println(l);//imprime o dado
    i=i+4+tam; 
    }
    arq.close();
  }catch (IOException e) {
    e.printStackTrace();
  }
    }
    public static void RecRegArqMestre(long ponteiro){
      /*função utilizada para recuperar um registro do arquivo mestre
      Recebe como parâmetro a posição que o registro desejado se encontra
      */
      RandomAccessFile arq;
      byte ba[];
      int tam;
      long i=0;
      Usuario l=new Usuario();
      try{
        arq = new RandomAccessFile("dados/Banco.db", "rw");
      arq.seek(ponteiro);//move o ponteiro para a posição desejada
      tam = arq.readInt();
      ba = new byte[tam];
      arq.read(ba);//le o dado
      l.fromByteArray(ba);
      System.out.println(l);//imprime o dado
      System.out.println();
      arq.close();
    }catch (IOException e) {
      e.printStackTrace();
    }
    }
    ///////////////////////////////////////////////
    public static void main(String[] args) {
    Scanner entrada= new Scanner(System.in);
    int TamanhoDir=0;
    byte ba[];
    byte ba1[];
    int id;
    int aux=0;
    int p=0;
    String nome;
    String nascimento;
    char sexo;
    String anotacao;
    int cpf=0;
    Usuario l1=new Usuario();
    Usuario l2=new Usuario();
    long retorno=0;
    int escolha=1;
    while(escolha!=0){
    
      escolha=menu();  
    TamanhoDir=TamanhoDiretorio();
    aux=TamanhoDir;
    p=0;
    while(aux>1){
      aux=aux/2;
      p++;
    }
    long vetor[]=new long[TamanhoDir];
    vetor=Diretorio(TamanhoDir);
      if(escolha==1){//cria os arquivos
        try{
        RandomAccessFile arq;
        RandomAccessFile indice;
        RandomAccessFile diretorio;
        arq = new RandomAccessFile("dados/Banco.db", "rw");
        indice = new RandomAccessFile("dados/indice.db", "rw");
        diretorio = new RandomAccessFile("dados/diretorio.db", "rw");
        System.out.println("arquivos criados com sucesso");
        arq.close();
        indice.close();
        diretorio.close();
        }catch (IOException e) {
          e.printStackTrace();
      }
    }
        else if(escolha==2){//inserir novos dados
           
            System.out.println("digite o nome do paciente: ");
            nome=entrada.nextLine();
            System.out.println("digite a data de nascimento do paciente: ");
            nascimento=entrada.nextLine();
            System.out.println("digite o sexo do paciente: ");
            sexo=entrada.next().charAt(0);
            entrada.nextLine();
            System.out.println("digite a anotacao do medico: ");
            anotacao=entrada.nextLine();
            do{
            System.out.println("digite o CPF: ");
            cpf=entrada.nextInt();
            if(cpf<=0)
            System.out.println("CPF NAO PODE SER NEGATIVO OU IGUAL A ZERO FAVOR DIGITAR NOVAMENTE");
            }while(cpf<=0);
            entrada.nextLine();
            l1 = new Usuario(nome, nascimento, sexo,anotacao);
             retorno=escritaPrincipal(l1);
            long pos=verificaPosDir(vetor,cpf, TamanhoDir);
             escritaIndice(retorno,cpf,pos, TamanhoDir,vetor,p);            
            }
        
      else if(escolha==3){//editar um dado
          System.out.println("digite o cpf do paciente que deseja editar: ");
          cpf=entrada.nextInt();
          entrada.nextLine();
          long po=verificaPosDir(vetor, cpf, TamanhoDir);
            System.out.println("digite o nome do paciente: ");
            nome=entrada.nextLine();
            System.out.println("digite a data de nascimento do paciente: ");
            nascimento=entrada.nextLine();
            System.out.println("digite o sexo do paciente: ");
            sexo=entrada.next().charAt(0);
            entrada.nextLine();
            System.out.println("digite a anotacao do medico: ");
            anotacao=entrada.nextLine();
            long tempoInicial = System.currentTimeMillis();

            
            l1 = new Usuario(nome, nascimento, sexo,anotacao);
          boolean ver=atualizaDados(cpf,po,retorno, l1);
          long tempoFinal = System.currentTimeMillis()-tempoInicial;
          System.out.println("TEMPO de pesquisa e recuperação do registro: " +tempoFinal+ " Millisegundos");
          if(ver)
          System.out.println("DADOS DO PACIENTE ATUALIZADOS COM SUCESSO");
          else
          System.out.println("DADOS DO PACIENTE NAO FOI ATUALIZADO, POIS NAO FOI ENCONTRADO ESSE PACIENTE NA BASE DE DADOS");
        }
        else if(escolha==4){//remover um dado
          System.out.println("digite o cpf do paciente que deseja remover: ");
          cpf=entrada.nextInt();
          long po=verificaPosDir(vetor, cpf, TamanhoDir);
          boolean ver=removeCpf(cpf,po);
          entrada.nextLine();
          if(ver)
          System.out.println("CPF REMOVIDO COM SUCESSO");
          else
          System.out.println("CPF NAO FOI REMOVIDO POIS NAO FOI ENCOTRADO");
        }
        else if(escolha==5){//imprimir arquivos
          int esc=0;
          do{
          System.out.println("1 - Deseja imprimir o cpf junto com a arquivo mestre da cada Paciente");
          System.out.println("2 - Deseja imprimir os arquivos do jeito que eles estao agora");
          esc=entrada.nextInt();
          entrada.nextLine();
          if(esc==1){
          leituraDiretorio();
          leituraIndice();
          }
          else if(esc==2){
            leituraDiretorio();
            leituraIndice1();
            leituraPrincipal1(l2);
          }
          else
          System.out.println("Escolha nao encontrada digite novamente");
        }while(esc!=1 && esc !=2);
        
      }
        else if(escolha==6){//simulação marrcando o tempo para inserção e recuperação de uma quantidade de cpfs indo de 1 a 10000
          long tempoInicial = System.currentTimeMillis();
          for(int i=1;i<=10000;i++){
            TamanhoDir=TamanhoDiretorio();
            aux=TamanhoDir;
            p=0;
            while(aux>1){
            aux=aux/2;
            p++;
          }
            long vet[]=new long[TamanhoDir];  
            vet=Diretorio(TamanhoDir);
            l1 = new Usuario("ab", "ab", 'a',"ab");
             retorno=escritaPrincipal(l1);
            long pos=verificaPosDir(vet,i, TamanhoDir);
             escritaIndice(retorno,i,pos, TamanhoDir,vet,p);
          }
          long tempoFinal = System.currentTimeMillis()-tempoInicial;
        long tempoInicial1 = System.currentTimeMillis();
        for(int i=1;i<=10000;i++){  
          TamanhoDir=TamanhoDiretorio();
          aux=TamanhoDir;
          p=0;
          while(aux>1){
          aux=aux/2;
          p++;
        }
          long vet[]=new long[TamanhoDir];  
          vet=Diretorio(TamanhoDir);
          long pos=verificaPosDir(vet,i, TamanhoDir);
          indice l=new indice(0);
          l=ReadIndice(l, pos);
          long PAMestre=l.PosArqMestre(i);
          RecRegArqMestre(PAMestre);
        }
        long tempoFinal2 = System.currentTimeMillis()-tempoInicial1;
        System.out.println("Tempo para inserçao: " +tempoFinal/1000+" Segundos");
        System.out.println("Tempo para pesquisa: " +tempoFinal2/1000+" Segundos");
      
      }
        else if(escolha==0)
          break;
        else{
          System.out.println("ESCOLHA NAO ECONTRADA DIGITE OUTRA OPCAO");
          System.out.println();
          escolha=menu(); 

        } 
        }
  System.out.println(">>>>ATE MAIS<<<<");
}
}