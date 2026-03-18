// Imports
import java.util.ArrayList;
import java.util.List;

public class Account {
    public String id;
    public String customerId;
    public String customerName;
    public double balance;

    // Constructor
    public Account(String id, String customerId, String customerName, double balance) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.balance = balance;
    }

    // Static method to parse a JSON string into a list of Account objects
    public static List<Account> parseAccounts(String jsonString) {

        // Create an empty list to hold the parsed accounts
        List<Account> accounts = new ArrayList<>();

        // 1. Clean up the string: Remove the outer square brackets [ ]
        String cleanJson = jsonString.trim();
        if (cleanJson.startsWith("[")) cleanJson = cleanJson.substring(1);
        if (cleanJson.endsWith("]")) cleanJson = cleanJson.substring(0, cleanJson.length() - 1);

        // 2. Split the string into individual objects
        String[] objectStrings = cleanJson.split("\\},");
        for (String obj : objectStrings) {
            String id = getValue(obj, "id");
            String custId = getValue(obj, "customer_id");
            String name = getValue(obj, "customer_name");
            String balStr = getValue(obj, "balance");

            double bal = balStr.isEmpty() ? 0.0 : Double.parseDouble(balStr);
            accounts.add(new Account(id, custId, name, bal));
        }

        // Return the list of parsed accounts
        return accounts;
    }

    // Reuse transaction getValue logic here
    private static String getValue(String data, String key) {
        String target = "\"" + key + "\":";
        int startIdx = data.indexOf(target);
        if (startIdx == -1) return "";
        int valueStart = startIdx + target.length();
        String remainder = data.substring(valueStart).trim();

        if (remainder.startsWith("\"")) {
            return remainder.substring(1, remainder.indexOf("\"", 1));
        } else {
            int commaIdx = remainder.indexOf(",");
            if (commaIdx == -1) return remainder.replace("}", "").trim();
            return remainder.substring(0, commaIdx).trim();
        }
    }

    // Manual method to convert the object back to a JSON string
    public String toJson() {
        return String.format("    {\n        \"id\": \"%s\",\n        \"customer_id\": \"%s\",\n        \"customer_name\": \"%s\",\n        \"balance\": %.2f\n    }", 
                id, customerId, customerName, balance);
    }
}