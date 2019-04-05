import java.utils
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class Principal {

	public static int hash(long cep){
		return (int)cep % 900001;
	}
	
    public static void main(String[] args) throws IOException {
    	RandomAccessFile fileCep = new RandomAccessFile("cep.dat", "r");
    	RandomAccessFile fileIndex = new RandomAccessFile("indice.dat", "rw");
    	  	
    	Endereco endAux = new Endereco();
    	long numReg = 0;
    	int totalColisoes = 0;
    	
    	int[] colisoes;
    	colisoes = new int[900001]; //Este array contém o número de colisões em cada hash code.
    	for(int i=0; i<colisoes.length; i++) {
    		colisoes[i] = -1;
    	}
    	
    	while( fileCep.getFilePointer() < fileCep.length() ) {
    		ElementoIndice indAux = new ElementoIndice();
    		endAux.leEndereco(fileCep);
    		int H = hash(Long.valueOf(endAux.getCep()));
    		fileIndex.seek(H*32);
    		
    		//Se a posição está vazia, cria um registro no indice.
    		if(fileIndex.readLong() == -1) {		
	    		indAux.setHashCode(H);
	    		indAux.setNumReg(numReg);
	    		indAux.setCep(Long.valueOf(endAux.getCep()));
	    		indAux.setProximo(0);
	    		fileIndex.seek(H*32);
	    		indAux.escreve(fileIndex);
	    	
	    		colisoes[H] = 0;
	    	//Caso contrário cria registro no fim do indice e costura os ponteiros.
    		} else {
    			fileIndex.seek(fileIndex.getFilePointer()+16);
    			long thisProx = fileIndex.readLong();
    			fileIndex.seek(fileIndex.getFilePointer()-8);
    			fileIndex.writeLong(fileIndex.length());
    			fileIndex.seek(fileIndex.length());
    			
	    		indAux.setHashCode(H);
	    		indAux.setNumReg(numReg);
	    		indAux.setCep(Long.valueOf(endAux.getCep()));
	    		indAux.setProximo(thisProx);
	    		indAux.escreve(fileIndex);
	    		
	    		colisoes[H]++; //Incrementa colisão deste hash.
	    		totalColisoes++;
    		}
    		numReg++;
    	}
    	
    	/* Este trecho agrupa as quantidades de colisão dos hash codes usando a ordenação; Sempre que 
    	 * ocorre mudança na quantidade de colisão, o agrupamento de colisões anterior é printado e o contador
    	 * é resetado para contar o próximo agrupamento. 
    	 */
    	Arrays.sort(colisoes);
    	int colCounter = 1;
    	for(int i=0; i<colisoes.length; i++) {
			if(i>0 && colisoes[i-1] != colisoes[i]) {
				if(colisoes[i-1] != -1) {	
					System.out.println("Ocorrências de " + colisoes[i-1] + " colisões: " + colCounter);
					colCounter = 1;
				}
				colCounter = 1;
			} else {
				colCounter++;
			}
			if(i == 900001)
				System.out.println("Ocorrências de " + colisoes[i-1] + " colisões: " + colCounter);
		}
    	System.out.println("\nTotal de " + totalColisoes + " colisões.");
    	System.out.println((numReg-totalColisoes) + " CEPs não colidiram no índice.");
    }
}
