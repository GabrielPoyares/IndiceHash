import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
 
public class ElementoIndice {

	private long hashCode;
	private long numReg;  
	private long cep;     
	private long proximo; 
 
    public void le(DataInput din) throws IOException
    {
    	this.hashCode = din.readLong();
        this.numReg = din.readLong();
        this.cep = din.readLong();
        this.proximo = din.readLong();
    }
 
    public void escreve(DataOutput dout) throws IOException
    {
        dout.writeLong(this.hashCode);
        dout.writeLong(this.numReg);
        dout.writeLong(this.cep);
        dout.writeLong(this.proximo);
    }
 
    public long getCep() {
        return cep;
    }
     
    public void setCep(long cep) {
        this.cep = cep;
    }
 
    public long getNumReg() {
        return this.numReg;
    }
 
    public void setNumReg(long numReg) {
        this.numReg = numReg;
    }
    
    public long getProximo() {
    	return proximo;
    }
    
    public void setProximo(long proximo) {
    	this.proximo = proximo;
    }
 
    public long getHashCode() {
    	return hashCode;
    }
    
    public void setHashCode(int hashCode) {
    	this.hashCode = hashCode;
    }
    
}