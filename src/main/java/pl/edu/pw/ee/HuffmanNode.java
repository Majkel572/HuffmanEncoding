package pl.edu.pw.ee;

import java.io.Serializable;

public class HuffmanNode implements Comparable<HuffmanNode>, Serializable {
    private static final long serialVersionUID = 1L;
    private int frequency;
    private char sign;
    private HuffmanNode leftChild;
    private HuffmanNode rightChild;
    private int unusedBits;
    private int howManyBytes;

    public HuffmanNode() {
        this.frequency = 0;
    }

    public HuffmanNode(Character sign, int frequency) {
        this.sign = sign;
        this.frequency = frequency;
    }

    public HuffmanNode(char c, HuffmanNode leftChild, HuffmanNode rightChild, int uB){
        this.sign = c;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.unusedBits = uB;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return frequency - o.frequency;
    }

    public boolean isLeafNode(){
        if(this.leftChild == null){
            return true;
        } else {
            return false;
        }
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public char getSign() {
        return sign;
    }

    public void setSign(char sign) {
        this.sign = sign;
    }

    public HuffmanNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(HuffmanNode n) {
        this.leftChild = n;
    }

    public HuffmanNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(HuffmanNode n) {
        this.rightChild = n;
    }

    public int getUnusedBits() {
        return unusedBits;
    }

    public void setUnusedBits(int u) {
        this.unusedBits = u;
    }
    public int getHowManyBytes() {
        return howManyBytes;
    }

    public void setHowManyBytes(int u) {
        this.howManyBytes = u;
    }
}
