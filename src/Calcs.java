public class Calcs {

    public static int clamp( int var, int min, int max) {
//        if (var >= max) {
//            return max;
//        }
//
//        if (var <= min) {
//            return min;
//        }
//
//        return var;

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
}
