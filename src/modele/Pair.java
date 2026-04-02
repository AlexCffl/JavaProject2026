package modele;

public class Pair<T, U> {
    private T left;
    private U right;

    public Pair(T left, U right) {
        this.left = left;
        this.right = right;
    }
    
    public boolean contains (Object obj) {
    	return obj==left || obj==right;
    }

    public T getLeft() { return left; }
    public U getRight() { return right; }
    
    public void setLeft(T t) {left = t;}
    public void setRight(U u) {right = u;}
}