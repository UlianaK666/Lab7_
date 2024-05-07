package application;

import java.util.LinkedHashSet;
import java.util.Set;

public class IDGenerator {

    private static Set<Long> IDs = new LinkedHashSet<>();

    private IDGenerator() {}

    public static void saveId(long id) {
        IDs.add(id);
    }

    public static void removeId(long id) {
        IDs.remove(id);
    }

    public static void removeAllIds() {
        IDs.clear();
    }

    public static boolean checkIfIDUnique(long id) {
        return !IDs.contains(id);
    }

    public static long generateID() {
        long currentId = 1L;
        boolean flag = false;
        for (long id : IDs) {
            flag = true;
            if (IDs.contains(currentId)) {
                currentId++;
            } else {
                return currentId;
            }
        }
        if (flag) {
            return currentId + 1;
        } else {
            return currentId;
        }
    }

    public static void setIDs(Set<Long> ids) {
        IDs = ids;
    }
}
