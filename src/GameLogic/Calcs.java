package GameLogic;

public class Calcs {

    public static int clamp( int var, int min, int max) {
        return (int) clamp((double) var, min, max);
    }

    public static double clamp( double var, double min, double max) {
        if (var >= max) {
            return max;
        }

        if (var <= min) {
            return min;
        }

        return var;
    }

    public static int[] stringToIntArray(String string, String delimiter) {

        String[] strings = string.trim().split(delimiter);
        int[] ints = new int[strings.length];

        for (int i = 0; i < ints.length; i++) {
            ints[i] = Integer.parseInt(strings[i]);
        }
        return ints;
    }
}
