package pl.edu.pw.ee;

public class NodeSignCode {
    private char sign;
    private String codedSign;

    public NodeSignCode(char s, String cS) {
        this.sign = s;
        this.codedSign = cS;
    }
    public void setSign(char s){
        this.sign = s;
    }
    public char getSign(){
        return sign;
    }
    public void setCodedSign(String cs){
        this.codedSign = cs;
    }
    public String getCodedSign(){
        return codedSign;
    }
    @Override
    public String toString() {
        return sign + ":" + codedSign;
    }
}
