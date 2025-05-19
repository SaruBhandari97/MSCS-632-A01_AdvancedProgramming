public class MemoryManagement {
    public static void main(String[] args) {
        int[] data = new int[5]; 
        for (int i = 0; i < data.length; i++) {
            data[i] = i + 1;
        }

        printArray(data);
        data = null; 
        System.gc(); 
    }

    public static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.println(value);
        }
    }
}
