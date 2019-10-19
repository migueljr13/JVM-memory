import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class PoolString {

    // Teste de Permanet Generation com String - POOL -

    public static final int MEGA = 1024 * 1024;
    public static final String FORMAT = "%.2f mb ";

    public static void main(String args[]) {

        String str = "Oiee Marte";

        List textos = new ArrayList();
        MemoryPoolMXBean mp = getPermGenMemory();

        for (int i = 0; true; i++) {
            String str2 = (str + i).intern();

            if (i % 100000 == 0) {
                MemoryUsage mu = mp.getUsage();
                out.println("PermGen inicial: " + String.format(FORMAT, (double) mu.getInit() / MEGA) +
                            "commitada: "       + String.format(FORMAT, (double) mu.getCommitted() / MEGA) +
                            "utilizada: "       + String.format(FORMAT, (double) mu.getUsed() / MEGA) +
                            "Max: "             + String.format(FORMAT, (double) mu.getMax() / MEGA));
            }

            if (i == 200000) {
                out.println("Retirar a referencia das Strings do Pool");
                textos = new ArrayList();
            }

            textos.add(str2);
        }
    }

    public static MemoryPoolMXBean getPermGenMemory() {

        return ManagementFactory.getMemoryPoolMXBeans()
                .stream()
                .filter(PoolString::IS_MEMORY_NON_HEAP_AND_METASPACE)
                .findFirst()
                .orElse(null);
    }
    /**
     * método testa os atributos 'getType' seja NON_HEAP e 'getName' contenha "METASPACE".
     * @param m
     * @return boolean
     **/
    private static boolean IS_MEMORY_NON_HEAP_AND_METASPACE(MemoryPoolMXBean m) {
        return (m.getType() == MemoryType.NON_HEAP) &&
                m.getName().toUpperCase().contains("METASPACE");
    }
}
