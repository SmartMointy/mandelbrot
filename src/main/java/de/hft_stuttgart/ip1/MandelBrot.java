package de.hft_stuttgart.ip1;

import java.util.ArrayList;

public class MandelBrot {
    public static ArrayList<Integer[]> calculate(int x, int y, int width, int height, int scale, int iterations, int threads) {
        ArrayList<Thread> threadsList = new ArrayList<>();
        ArrayList<Integer[]> pixel = new ArrayList<>();

        for( int i = 0; i < threads; i++ ) {
            int threadI = i;

            Thread thread = new Thread(() -> {
                double a;
                double b;
                double ax;
                double ay;
                double xx;
                int s;
                int r;
                int z;

                for (s = x+((width /threads) * threadI); s <= x + ((width /threads) * (threadI+1))+threadI; s++) {
                    a = -2 + 2.0/((200)*scale) * s;

                    for (r = y; r <= y + height; r++) {
                        ax = 0;
                        ay = 0;
                        b = -1 + 2.0 / ( (200) * scale) * r;
                        z = 0;

                        while ((z < iterations) && ( Math.sqrt(ax * ax + ay * ay) < 2 )) {
                            z++;
                            xx = ax * ax - ay * ay + a;
                            ay = 2 * ax * ay + b;
                            ax = xx;
                        }

                        synchronized (pixel) {
                            pixel.add(new Integer[]{s, r, z});
                        }
                    }
                }
            });

            threadsList.add(thread);
        }

        threadsList.forEach(Thread::start);

        threadsList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        return pixel;
    }
}
