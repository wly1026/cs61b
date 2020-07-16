package hw3.hash;

import java.util.LinkedList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {

        LinkedList<Oomage>[] buckets = new LinkedList[M];
        int N = oomages.size();
        for (int i = 0; i < M; i++) {
            buckets[i] = new LinkedList<Oomage>();
        }
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketNum].add(o);
        }
        for (LinkedList<Oomage> bucket : buckets) {
            int bucketSize = bucket.size();
            if (bucketSize <= (N / 50) || bucketSize >= (N / 2.5)) {
                return false;
            }
        }
        return true;
    }
}
