import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class BuscaIndice {
	
    public static void main(String[] args) throws IOException {
		Scanner leCep = new Scanner(System.in);
		System.out.println("Digite um CEP para buscar: ");
		long buscado = leCep.nextLong();
		leCep.close();
		
		int H = Principal.hash(buscado);
		RandomAccessFile fileCep = new RandomAccessFile("cep.dat", "r");
		RandomAccessFile fileIndex = new RandomAccessFile("indice.dat", "r");
		fileIndex.seek(H*32);		
		
		buscaNoIndice(fileIndex, fileCep, buscado);
    }	

	public static void buscaNoIndice(RandomAccessFile fileIndex, RandomAccessFile fileCep, long buscado) throws IOException {		
		ElementoIndice indAux = new ElementoIndice();
		indAux.le(fileIndex);
		long comparado = indAux.getCep();
		long posicaoNoCepDat = indAux.getNumReg();
		long comparadoProx = indAux.getProximo();
		
		if(buscado == comparado) {
			fileCep.seek(posicaoNoCepDat*300);
			Endereco endAux = new Endereco();
			endAux.leEndereco(fileCep);
			System.out.println(endAux.getCep() + "  " + endAux.getLogradouro() + endAux.getBairro() + endAux.getCidade());
			fileIndex.close();
			fileCep.close();
		} else {
			fileIndex.seek(comparadoProx);
			fileIndex.readLong();
			fileIndex.readLong();
			fileIndex.readLong();
			fileIndex.readLong();
			fileIndex.seek(fileIndex.getFilePointer() - 32);
			
			buscaNoIndice(fileIndex, fileCep, buscado);
		}
	}
}
