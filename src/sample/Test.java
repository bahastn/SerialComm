package sample;

import java.util.Random;

public class Test implements Runnable {
    @Override
    public void run() {

            int i = 1;
            while (i > 0) {
                numbers();

            }

        }

    private Integer numbers() {
        Random random = new Random();
        Integer integer = random.nextInt(100);
        System.out.println(integer);
        return integer;
    }

}
