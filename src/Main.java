import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;
import java.util.Locale;

import static java.lang.System.out;

public class Main {

    public static int MEGA = 1024 * 1024;
    public static String FORMAT = " (%.2fmb)";

    public static void main(String args[]) {

        //o intellij ja traz os importas automaticamente referente a memória
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        //Aqui há uma identificação de memória com resposta HEAP
        out.println(":::::Memória heap:::::");
        out.println();
        printMemoryUsage(memoryMXBean.getHeapMemoryUsage());

        //AGORA VAMOS FAZER PRATICAMENTE A MESMA COISA PORÉM COM A MEMÓRIA NON-HEAP
        out.println(":::Memória non-heap:::");
        out.println();
        printMemoryUsage(memoryMXBean.getNonHeapMemoryUsage());

        // Funcionamento do pool de memórias na JVM
        out.println(":: Pool de memórias Organ. JVM ::");
        out.println();
        List<MemoryPoolMXBean> list = ManagementFactory.getMemoryPoolMXBeans();
        list.forEach(Main::printPoolDetails);
    }

    /**
     * imprime, em detalhes, o uso de memoria pela JVM .
     * @param memoryUsage
     **/
    private static void printMemoryUsage(MemoryUsage memoryUsage) {

        out.println("Tamanho Inicial :: " + memoryUsage.getInit() +
                String.format(Locale.US, FORMAT, (double) memoryUsage.getInit() / MEGA));
        out.println("Tamanho atual :: " + memoryUsage.getInit() +
                String.format(Locale.US, FORMAT, (double) memoryUsage.getCommitted() / MEGA));
        out.println("Usado :: " + memoryUsage.getUsed() +
                String.format(Locale.US, FORMAT, (double) memoryUsage.getUsed() / MEGA));
        out.println("Máximo :: " + memoryUsage.getMax() +
                String.format(Locale.US, FORMAT, (double) memoryUsage.getMax() / MEGA));
        out.println();
    }

    private static void printPoolDetails(MemoryPoolMXBean m) {
        out.println("Nome do Pool :: " + m.getName());
        out.println("Grupo ::" + m.getType());
        out.println();
        printMemoryUsage(m.getUsage());
    }
}