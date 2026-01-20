public class OrderIdGenerator {

    private static long currentId = 1000;

    public static synchronized long generateId() {
        return ++currentId;
    }
}