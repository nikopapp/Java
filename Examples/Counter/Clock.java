/* Simulate a digital clock with hours, minutes, seconds. */

class Clock {
    private Counter hours, minutes, seconds;

    public static void main(String[] args) {
        Clock clock = new Clock();
        int[] time = clock.getTime(args);
        clock.setup(time);
        clock.run();
    }

    int[] getTime(String[] args) {
        int[] time = {0, 0, 0};
        if (args.length > 3) fail();
        try {
            if (args.length >= 1) time[0] = Integer.parseInt(args[0]);
            if (args.length >= 2) time[1] = Integer.parseInt(args[1]);
            if (args.length >= 3) time[2] = Integer.parseInt(args[2]);
        } catch (Exception e) { fail(); }
        return time;
    }

    void fail() {
        System.err.println("Use: java Clock [h] [m] [s]");
        System.exit(1);
    }

    void setup(int[] time) {
        hours = new Counter(24, time[0], null);
        minutes = new Counter(60, time[1], hours);
        seconds = new Counter(60, time[2], minutes);
    }

    void run() {
        while (true) {
            String time =
                hours.display() + ":" +
                minutes.display() + ":" +
                seconds.display();
            System.out.println(time);
            try { Thread.sleep(1000); }
            catch (Exception e) { throw new Error(e); }
            seconds.tick();
        }
    }
}
