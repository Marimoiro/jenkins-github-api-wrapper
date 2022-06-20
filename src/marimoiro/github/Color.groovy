package marimoiro.github

import static java.lang.Math.abs

//reference from https://stackoverflow.com/questions/9221414/generating-a-random-color

/**
 * Color Utility
 */
public class Color 
{
    private static int floor(x) { x as int }

    /**
    * @return random vivid color
    */
    public static String randomVividRgbCode() {
        def rnd = new Random()
        def h = rnd.nextFloat()
        def s = 0.65f + rnd.nextFloat() * 0.35f
        def l = 0.5f
        return rgbToColorCode(hslToRgb(h as float, s as float, l as float))
    }

    /**
    * @return random pastel color
    */
    public static String randomPastelRgbCode() {
        def rnd = new Random()
        def h = rnd.nextFloat()
        def s = 1.0f
        def l = 0.7f + rnd.nextFloat() * 0.15f
        return rgbToColorCode(hslToRgb(h as float, s as float, l as float))
    }


    public static String rgbToColorCode(List vector) {
        def r = vector[0]
        def g = vector[1]
        def b = vector[2]

        def rgb = floor(r * 255) << 16 |
                floor(g * 255) << 8  | 
                floor(b * 255)
        return Integer.toString(rgb, 16).padLeft(6, '0')
    }

    public static def hslToRgb(float h, float s, float l) {
        def c = (1 - abs(2 * l - 1)) * s // Chroma.
        def h1 = h * 6
        def x = c * (1 - abs(h1 % 2 - 1))
        def rgb = h1 < 1 ? [c, x, 0] :
                h1 < 2 ? [x, c, 0] :
                h1 < 3 ? [0, c, x] :
                h1 < 4 ? [0, x, c] :
                h1 < 5 ? [x, 0, c] :
                        [c, 0, x]
        def (r, g, b) = rgb
        def m = l - c * 0.5
        // r   g      b
        return [r + m, g + m, b + m]
    }

}