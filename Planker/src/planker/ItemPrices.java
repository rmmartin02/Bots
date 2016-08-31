package planker;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ItemPrices {

    private final static ConcurrentHashMap<Integer, Long> priceMap = new ConcurrentHashMap<>();
    private final static ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static long getPrice(final int id) {
        if (priceMap.containsKey(id)) {
            return priceMap.get(id);
        } else {
            priceMap.put(id, -1L);
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    priceMap.put(id, extractPrice(id));
                }
            });
            return -1;
        }
    }

    private static long extractPrice(final int id) {
        try {
            URL oracle = new URL("http://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=" + id);
            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

            String inputLine = in.readLine();
            if (inputLine != null && inputLine.contains("current")) {
                in.close();
                return evaluate(inputLine.replaceAll(".*current.*?price\"?:\"?(.*?)\"?}.*", "$1"));
            } else {
                in.close();
                return 0;
            }
        } catch (Exception ignore) {
            return -2;
        }
    }

    private static long evaluate(String value) {
        try {
            double val = Double.parseDouble(value.replaceAll("([0-9.,]+).*", "$1").replace(",", ""));
            if (value.matches("[0-9,]+")) {
                return (long) val;
            } else {
                char c = value.replaceAll(".*([a-z]).*", "$1").charAt(0);
                if (c == 'k' || c == 'K') {
                    return (long) (val * 1000D);
                }
                if (c == 'm' || c == 'M') {
                    return (long) (val * 1000000D);
                }
                if (c == 'b' || c == 'B') {
                    return (long) (val * 1000000000D);
                }
                return (long) val;
            }
        } catch (Exception ignore){
            return -1;
        }
    }
}