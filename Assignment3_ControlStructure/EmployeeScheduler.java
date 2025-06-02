import java.util.*;

public class EmployeeScheduler {
    static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    static final String[] SHIFTS = {"Morning", "Afternoon", "Evening"};

    //priority-ranked shift preferences
    static class Employee {
        String name;
        Map<String, List<String>> preferences = new HashMap<>();
        int daysWorked = 0;

        Employee(String name) {
            this.name = name;
        }
    }

    static Map<String, Map<String, List<String>>> schedule = new HashMap<>();
    static List<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of employees: ");
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.print("Enter employee name: ");
            String name = scanner.nextLine();
            Employee emp = new Employee(name);

            for (String day : DAYS) {
                System.out.println("Enter shift preferences (ranked, comma separated) for " + name + " on " + day + ":");
                System.out.print("Example: Morning,Evening,Afternoon: ");
                String[] rankedShifts = scanner.nextLine().split(",");
                emp.preferences.put(day, Arrays.asList(rankedShifts));
            }
            employees.add(emp);
        }

        generateSchedule();
        ensureMinimumStaffing();
        printSchedule();
    }

    static void generateSchedule() {
        for (String day : DAYS) {
            schedule.put(day, new HashMap<>());
            for (String shift : SHIFTS) {
                schedule.get(day).put(shift, new ArrayList<>());
            }

            for (Employee emp : employees) {
                if (emp.daysWorked >= 5) continue;
                boolean assigned = false;

                List<String> rankedPrefs = emp.preferences.get(day);
                for (String shift : rankedPrefs) {
                    List<String> assignedList = schedule.get(day).get(shift);
                    if (assignedList.size() < 2 && !assignedList.contains(emp.name) && !isAlreadyScheduled(emp.name, day)) {
                        assignedList.add(emp.name);
                        emp.daysWorked++;
                        assigned = true;
                        break;
                    }
                }

                // Try next day's shift if today's are full, and employee hasn't worked 5 days
                if (!assigned && emp.daysWorked < 5) {
                    for (String nextDay : DAYS) {
                        if (nextDay.equals(day)) continue;
                        List<String> fallbackPrefs = emp.preferences.getOrDefault(nextDay, new ArrayList<>());
                        for (String shift : fallbackPrefs) {
                            List<String> nextAssigned = schedule.get(nextDay).get(shift);
                            if (nextAssigned.size() < 2 && !nextAssigned.contains(emp.name) && !isAlreadyScheduled(emp.name, nextDay) && emp.daysWorked < 5) {
                                nextAssigned.add(emp.name);
                                emp.daysWorked++;
                                assigned = true;
                                break;
                            }
                        }
                        if (assigned) break;
                    }
                }
            }
        }
    }

    // Helper method to check if employee is already scheduled for that day
    static boolean isAlreadyScheduled(String name, String day) {
        Map<String, List<String>> dailyShifts = schedule.get(day);
        for (List<String> list : dailyShifts.values()) {
            if (list.contains(name)) return true;
        }
        return false;
    }

    // Ensure that at least 2 employees are assigned to each shift per day
    static void ensureMinimumStaffing() {
        Random rand = new Random();
        for (String day : DAYS) {
            for (String shift : SHIFTS) {
                List<String> assigned = schedule.get(day).get(shift);
                while (assigned.size() < 2) {
                    List<Employee> available = new ArrayList<>();
                    for (Employee emp : employees) {
                        if (!assigned.contains(emp.name) && emp.daysWorked < 5 && !isAlreadyScheduled(emp.name, day)) {
                            available.add(emp);
                        }
                    }
                    // Shuffle available employees
                    Collections.shuffle(available, rand);

                    if (available.isEmpty()) {
                        System.out.println("⚠️  Could not assign enough employees for " + day + " " + shift);
                        break;
                    }

                    // after shuffle, pick first
                    Employee pick = available.get(0); 
                    assigned.add(pick.name);
                    pick.daysWorked++;
                }
            }
        }
    }

    static void printSchedule() {
        for (String day : DAYS) {
            System.out.println(day + ":");
            for (String shift : SHIFTS) {
                List<String> assigned = schedule.get(day).get(shift);
                System.out.println("  " + shift + ": " + assigned);
            }
        }
    }
}
