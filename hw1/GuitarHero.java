import es.datastructur.synthesizer.GuitarString;

public class GuitarHero {
    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static final int len = keyboard.length();
    GuitarString[] stringArray = new GuitarString[len];

    public GuitarHero() {
        for (int i = 0; i < len; i++){
            stringArray[i] = new GuitarString(440 * Math.pow(2, (i-24)/12));
        }
    }

    public static void main(String[] args) {
        /* create 27 guitar strings */
        GuitarHero guitar = new GuitarHero();

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                if (index >= 0) {
                    guitar.stringArray[index].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (int i = 0; i < guitar.len; i ++) {
                sample += guitar.stringArray[i].sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < guitar.len; i ++) {
                guitar.stringArray[i].tic();
            }
        }
    }
}
