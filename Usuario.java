import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class Usuario {//Usuario que será o arquivo mestre
  protected String nome;
  protected String nascimento;
  protected char sexo;
  protected String anotacao;

  public Usuario(String n, String na, char s,String a) {//construtor 1
    this.nome = n;
    this.nascimento = na;
    this.sexo = s;
    this.anotacao=a;
  }

  public Usuario() {//construtor 2
    this.nome = "";
    this.nascimento = "";
    this.sexo = ' ';
    this.anotacao="";
  }

  public String toString() {//método para imprimir o objeto
   

    return "\nnome: " + this.nome + "\nnascimento: " + this.nascimento + "\nsexo: "
        + this.sexo + "\nanotacao:  " + this.anotacao;
  }

  public byte[] toByteArray() throws IOException {//método que transforma os dados de um objeto para um vetor de bytes
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeUTF(nome);
    dos.writeUTF(nascimento);
    dos.writeChar(sexo);
    dos.writeUTF(anotacao);
    return baos.toByteArray();
  }

  public void fromByteArray(byte[] ba) throws IOException {//método que recebe um vetor de bytes e converte para os valores de um objeto
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    nome = dis.readUTF();
    nascimento = dis.readUTF();
    sexo = dis.readChar();
    anotacao = dis.readUTF();
  }

}