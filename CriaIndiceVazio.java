import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

class CriaIndiceVazio {
	
    public static void main(String[] args) throws IOException {
    	
    	File file = new File("indice.dat");
    	
    	file.createNewFile();
    	
        RandomAccessFile indice = new RandomAccessFile(file, "rw");

        ElementoIndice indTemp = new ElementoIndice();
        
        indTemp.setHashCode(-1);
        indTemp.setCep(-1);
        indTemp.setNumReg(-1);
        indTemp.setProximo(-1);
         
        for(int i=0; i<900001; i++) {     
            indTemp.escreve(indice);
        }
        indice.close();    
    }
}
