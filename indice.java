import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.lang.model.util.ElementScanner6;

public class indice {//arquivo de indice
    protected int Plinha;
    protected int cpf1;
    protected long registro1;
    protected int cpf2;
    protected long registro2; 
    protected int cpf3;
    protected long registro3;
    protected int cpf4;
    protected long registro4;
    
    public indice(int p) {//construtor 1
      this.Plinha=p;
      this.cpf1=0;
      this.registro1=0;
      this.cpf2=0;
      this.registro2=0;
      this.cpf3=0;
      this.registro3=0;
      this.cpf4=0;
      this.registro4=0;
    }
    //construtor 2
    public indice(int p, int cpf1, long registro1,int cpf2, long registro2,int cpf3, long registro3,int cpf4, long registro4) {
      this.Plinha=p;
      this.cpf1=cpf1;
      this.registro1=registro1;
      this.cpf2=cpf2;
      this.registro2=registro2;
      this.cpf3=cpf3;
      this.registro3=registro3;
      this.cpf4=cpf4;
      this.registro4=registro4;
    }
    public byte[] toByteArray() throws IOException {//converte os valores do objeto para um vetor de byte
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(Plinha);
        dos.writeInt(cpf1);
        dos.writeLong(registro1);
        dos.writeInt(cpf2);
        dos.writeLong(registro2);
        dos.writeInt(cpf3);
        dos.writeLong(registro3);
        dos.writeInt(cpf4);
        dos.writeLong(registro4);
        return baos.toByteArray();
      }
    public void fromByteArray(byte[] ba) throws IOException {//converte um vetor de bytes para os valores do objeto
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        Plinha = dis.readInt();
        cpf1=dis.readInt();
        registro1 = dis.readLong();
        cpf2=dis.readInt();
        registro2 = dis.readLong();
        cpf3=dis.readInt();
        registro3 = dis.readLong();
        cpf4=dis.readInt();
        registro4 = dis.readLong();
      }
      public String toString() {//imprime o conte??do do objeto
        return "\nP': " + this.Plinha + 
               "\ncpf: " + this.cpf1 + "\nponteiro: " + this.registro1 +
               "\ncpf: " + this.cpf2 + "\nponteiro: " + this.registro2 +
               "\ncpf: " + this.cpf3 + "\nponteiro: " + this.registro3 +
               "\ncpf: " + this.cpf4 + "\nponteiro: " + this.registro4;
      }
      public int VerificaLugarNoBucket(int c,long r, int p){//Fun??o utilizada para verificar lugar no Bucket 
        /*condi????es: se algum dos cpf for igual a zero ent??o ser?? inserido o novo cpf nessa posi??ao
        Se n??o retorna zero se o a profundidade local(p') for diferente da prufundidade global(p)
        caso consiga a inser????o retorna 1
        */
        if(this.cpf1 == 0){  
          this.cpf1=c;
          this.registro1=r;
        }
        else if(this.cpf2 == 0){  
          this.cpf2=c;
          this.registro2=r;
        }
        else if(this.cpf3 == 0){ 
          this.cpf3=c;
          this.registro3=r;
        }
        else if(this.cpf4 == 0){
          this.cpf4=c;
          this.registro4=r;
        }
        else{
          if(this.Plinha!=p){
            this.Plinha=this.Plinha+1;
            return 0;
          }
          else{ 
            this.Plinha=this.Plinha+1;
            return -1;   
        }
        }
          
        return 1;
      }
      public void reordenaCpf(){
        /*Fun????o utilizada para reoordenar os cpfs a fim de que evite ter um cpf e depois um zero
        e possivelmente depois um outro cpf.
        
        Basicamente ser?? verificado cada posi????o do bucket e quando achar zero, coloca nessa posi????o 
        o valor do cpf da proxima posi??ao
        */

        if(this.cpf1==0){
        this.cpf1=this.cpf2;
        this.registro1=this.registro2;
        this.cpf2=0;
        this.registro2=0;
      }
      if(this.cpf2==0){
        if(this.cpf1==0){
        this.cpf1=this.cpf3;
      this.registro1=this.registro3;
      this.cpf3=0;
        }  
        else
          this.cpf2=this.cpf3;
        this.registro2=this.registro3;    
        this.cpf3=0;
        this.registro3=0;
    }
      if(this.cpf3==0){
      if(this.cpf2==0 && this.cpf1==0){
      this.cpf1=this.cpf4;
      this.registro1=this.registro4;
      this.cpf4=0;    
  }  
      else if(this.cpf2==0 && this.cpf1 != 0){
        this.cpf2=this.cpf4;
      this.registro2=this.registro4;
      this.cpf4=0;  
      }  
  
    else{
      this.cpf3=this.cpf4;
      this.registro3=this.registro4; 
      this.cpf4=0;
    }
  }
    if(this.cpf4==0)
    this.registro4=0;  
}
    
     public int []VerificaCpf(int modulo, int tamanho){
       /*Fun????o utilizada para verificar o/os cpf/s que n??o far??o 
       mais parte desse bucket em caso de criar um novo bucket ou duplicar o diret??rio
       os cpf que sa??rem ser??o armazenados em um vetor de inteiros
       */
      int vetor[]=new int[5];
      int cont=0;
      int cont1=0;
      if(this.cpf1%tamanho==modulo){
        vetor[1]=this.cpf1;
        cont++;
      }
      else
      vetor[1]=0;
      if(this.cpf2%tamanho==modulo){
        vetor[2]=this.cpf2;
        cont++;
      }
      else
      vetor[2]=0;
      if(this.cpf3%tamanho==modulo){
        vetor[3]=this.cpf3;
        cont++;
      }
      else
      vetor[3]=0;
      if(this.cpf4%tamanho==modulo){
        vetor[4]=this.cpf4;
        cont++;
      }
      else
      vetor[4]=0;
      vetor[0]=cont;
      return vetor;
     }
    
     public long[] VerificaRegistros(int vetor[]){
      /*Fun????o que recebe como par??metro o vetor de inteiros com os cpfs que sair??o do Bucket
      a fim de armazenar em um vetor de long a posi????o desse cpfs.
      */
      long vet[]=new long[4];
      if(this.cpf1==vetor[1]){
        vet[0]=this.registro1;
        this.cpf1=0;
      }
      if(this.cpf2==vetor[2]){
        vet[1]=this.registro2;
        this.cpf2=0;
      }
      if(this.cpf3==vetor[3]){
        vet[2]=this.registro3;
        this.cpf3=0;
      }
      if(this.cpf4==vetor[4]){
        vet[3]=this.registro4;
        this.cpf4=0;
      }
      return vet;
    }  
    public int ExcluiCpf(int cpf){
      /*Fun????o utilizada para exc??uir um cpf, recebe como par??metro um inteiro cpf
      e procura ele pelo bucket, assim que encotrar esse cpf ir?? assumir o valor de ZERO e retorna 1
      caso n??o encontre esse cpf retornar?? 0 que significa que o cpf n??o foi encontrado e excluido.
      */
      if(this.cpf1==cpf){
        this.cpf1=0;
        return 1;
      }
      else if(this.cpf2==cpf){
        this.cpf2=0;
        return 1;
      }
      else if(this.cpf3==cpf){
        this.cpf3=0;
        return 1;
      }
      else if(this.cpf4==cpf){
        this.cpf4=0;
        return 1;
      }
        return 0;
    }
    public int atualizaPonteiro(int cpf, long ponteiro){
      /*Fun????o utilizada para atualizar um ponteiro de um cpf
      Ela ser?? utilizada caso o usu??rio deseja atualizar algum dado, 
      com isso esse dado ser?? inserido no fim do arquivo mestre e ter??
      que ser mudado o ponteiro desse dado que pertence a um cpf, caso o 
      cpf seja encontrado retorna 1 que significa que o ponteiro foi atualizado, 
      se n??o retorna ZERO, que significa que n??o encontrou o cpf e nao atualizou o ponteiro.
      */
      if(this.cpf1==cpf){
        this.registro1=ponteiro;
        return 1;
      }
      else if(this.cpf2==cpf){
        this.registro2=ponteiro;
        return 1;
      }
      else if(this.cpf3==cpf){
        this.registro3=ponteiro;
        return 1;
      }
      else if(this.cpf4==cpf){
        this.registro4=ponteiro;
        return 1;
      }
        return 0;
    } 
    public int ExisteCpf(int cpf){
      //Fun????o que verifica se um cpf est?? contido no Bucket retorna 1 se tiver e ZERO se nao tiver
      if(this.cpf1==cpf){
        return 1;
      }
      else if(this.cpf2==cpf){
        return 1;
      }
      else if(this.cpf3==cpf){
        return 1;
      }
      else if(this.cpf4==cpf){
        return 1;
      }
        return 0;
    }
    public long PosArqMestre(int cpf){
      /*Fun????o que verifica a posicao de um cpf no arquivo mestre
      Recebe um cpf e verifica la no Bucket qual a posi????o e retorna como long
      */
      if(this.cpf1==cpf){
        return this.registro1;
      }
      else if(this.cpf2==cpf){
        return this.registro2;
      }
      else if(this.cpf3==cpf){
        return this.registro3;
      }
      else if(this.cpf4==cpf){
        return this.registro4;
      }
        return -1;
    }
  }