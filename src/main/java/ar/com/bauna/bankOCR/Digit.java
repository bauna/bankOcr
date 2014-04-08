package ar.com.bauna.bankOCR;

import java.util.*;

public class Digit {
    public static final Digit ZERO = new Digit(new Character[][]{
            new Character[]{' ', '_', ' '},
            new Character[]{'|', ' ', '|'},
            new Character[]{'|', '_', '|'}});
    
    public static final Digit ONE = new Digit(new Character[][]{
            new Character[]{' ', ' ', ' '},
            new Character[]{' ', ' ', '|'},
            new Character[]{' ', ' ', '|'}});
    
    public static final Digit TWO = new Digit(new Character[][]{
            new Character[]{' ', '_', ' '},
            new Character[]{' ', '_', '|'},
            new Character[]{'|', '_', ' '}});

    public static final Digit THREE = new Digit(new Character[][]{
            new Character[]{' ', '_', ' '},
            new Character[]{' ', '_', '|'},
            new Character[]{' ', '_', '|'}});

    public static final Digit FOUR = new Digit(new Character[][]{
            new Character[]{' ', ' ', ' '},
            new Character[]{'|', '_', '|'},
            new Character[]{' ', ' ', '|'}});

    public static final Digit FIVE = new Digit(new Character[][]{
            new Character[]{' ', '_', ' '},
            new Character[]{'|', '_', ' '},
            new Character[]{' ', '_', '|'}});

    public static final Digit SIX = new Digit(new Character[][]{
            new Character[]{' ', '_', ' '},
            new Character[]{'|', '_', ' '},
            new Character[]{'|', '_', '|'}});

    public static final Digit SEVEN = new Digit(new Character[][]{
            new Character[]{' ', '_', ' '},
            new Character[]{' ', ' ', '|'},
            new Character[]{' ', ' ', '|'}});

    public static final Digit EIGHT = new Digit(new Character[][]{
            new Character[]{' ', '_', ' '},
            new Character[]{'|', '_', '|'},
            new Character[]{'|', '_', '|'}});

    public static final Digit NINE = new Digit(new Character[][]{
            new Character[]{' ', '_', ' '},
            new Character[]{'|', '_', '|'},
            new Character[]{' ', '_', '|'}});

    private static final Map<Digit, Integer> toInt = new HashMap<Digit, Integer>() {
        {
            put(ZERO, 0);
            put(ONE, 1);
            put(TWO, 2);
            put(THREE, 3);
            put(FOUR, 4);
            put(FIVE, 5);
            put(SIX, 6);
            put(SEVEN, 7);
            put(EIGHT, 8);
            put(NINE, 9);
        }
    };
    
    private final ArrayList<ArrayList<Character>> segments;

    public Digit(Character[][] segments) {
        this.segments = new ArrayList<>(3);
        for (Character[] cs : segments) {
            this.segments.add(new ArrayList<Character>(Arrays.asList(cs)));
        }
    }
    
    private Digit(ArrayList<ArrayList<Character>> segments) {
    	this.segments = segments;
    }
    
    public boolean isValid() {
    	return toInt.get(this) != null;
    }

    public static int toInt(Digit number) {
        Integer intNum = toInt.get(number);
        return intNum == null ? -1 : intNum;
    }

    public static int toInt(Character[][] segments) {
        return toInt(new Digit(segments));
    }

    public int toInt() {
        return toInt(this);
    }
    
    @Override
    public int hashCode() {
        return segments.hashCode();
    }

    public char charAt(int row, int col) {
    	return segments.get(row).get(col);
    }
    
    public Digit changeCharAt(int row, int col, char character) {
    	ArrayList<ArrayList<Character>> newSegments = new ArrayList<ArrayList<Character>>(3);
    	for (int i = 0; i < segments.size(); i++) {
    		ArrayList<Character> rowArray = new ArrayList<>(segments.get(i));
    		if (i == row) {
    			rowArray.set(col, character);
    		}
			newSegments.add(rowArray);
		}
    	return new Digit(newSegments);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Digit other = (Digit) obj;
        if (segments == null) {
            if (other.segments != null)
                return false;
        } else if (!segments.equals(other.segments))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
    	return String.valueOf(toInt());
    }
}
