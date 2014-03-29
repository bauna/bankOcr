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

    private static Map<Digit, Integer> toInt = new HashMap<Digit, Integer>() {
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

    private Digit(Character[][] segments) {
        this.segments = new ArrayList<>(3);
        for (Character[] cs : segments) {
            this.segments.add(new ArrayList<Character>(Arrays.asList(cs)));
        }
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
}
