package util;

public class Timer {

        private long startTime;

        public Timer() {
            startTime = System.currentTimeMillis();
        }

        public long getTimeElapsed() {
            return System.currentTimeMillis() - startTime;
        }

        public long getSecondsElapsed() {
            return getTimeElapsed() / 1000 % 60;
        }

        public long getMintuesElapsed() {
            return getTimeElapsed() / 1000 / 60 % 60;
        }

        public long getHoursElapsed() {
            return getTimeElapsed() / 1000 / 60 / 60;
        }

        public String getFormattedTime() {
            return getFormattedTime(getTimeElapsed());
        }

        public long calculatePerHour(long gained) {
            return gained * 3600000 / getTimeElapsed();
        }

        public static String getFormattedTime(long time) {
            return String.format("%d:%02d:%02d",
                    Long.valueOf(time / 1000 / 60 / 60),
                    Long.valueOf(time / 1000 / 60 % 60),
                    Long.valueOf(time / 1000 % 60));
        }
    }