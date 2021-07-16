import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
public class diretorio {//clasee diretório
    protected long d;
    public diretorio() {//construtor 1
        d=0;
    }
    public diretorio(long dir){//construtor 2
        d=dir;
    }
    public byte[] toByteArray() throws IOException {//converte um valor long para um vetor de bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeLong(d);
        return baos.toByteArray();
      }
    public void fromByteArray(byte[] ba) throws IOException {//recebe um vetor de bytes e converte para long
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        d = dis.readLong();
      }
      public String toString() {//imprime o conteúdo do objeto
        return " " + this.d;
      }
     public long convertlong(){//Função que retorna o long do objeto, criada para evitar ficar acessando o contepudo do objeto pelo main
        return d;
     }
     public void mudaLong(long a){//Função usada para mudar o valor long do objeto
       this.d=a;
     }
    }
