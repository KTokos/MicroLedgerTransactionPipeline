// Imports
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private String transactionId;
    private String customerId;
    private double amount;
    private String date; 
    private String transactionType;

    // Constructor
    public Transaction(String transactionId, String customerId, double amount, String date, String transactionType) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.amount = amount;
        this.date = date;
        this.transactionType = transactionType;
    }

    // Static method to parse a JSON string into a list of Transaction objects
    public static List<Transaction> parseTransactions(String jsonString) {
        List<Transaction> transactions = new ArrayList<>();

        // 1. Clean up the string: Remove the outer square brackets [ ]
        String cleanJson = jsonString.trim();
        if (cleanJson.startsWith("[")) cleanJson = cleanJson.substring(1);
        if (cleanJson.endsWith("]")) cleanJson = cleanJson.substring(0, cleanJson.length() - 1);

        // 2. Split the string into individual objects. 
        // Objects in your JSON are separated by "},"
        String[] objectStrings = cleanJson.split("\\},");

        for (String obj : objectStrings) {
            // Each 'obj' looks like: { "id": "...", "amount": 100.00 ...
            // We extract each field by looking for the "key"
            String id = getValue(obj, "id");
            String custId = getValue(obj, "customer_id");
            String amtStr = getValue(obj, "amount");
            String timestamp = getValue(obj, "timestamp");
            String type = getValue(obj, "type");

            // Convert amount string to a double
            double amt = amtStr.isEmpty() ? 0.0 : Double.parseDouble(amtStr);

            // Create the object and add to our list
            transactions.add(new Transaction(id, custId, amt, timestamp, type));
        }

        return transactions;
    }

    // Helper method to find the value of a specific key in a JSON-like string
    private static String getValue(String data, String key) {
        String target = "\"" + key + "\":";
        int startIdx = data.indexOf(target);
        if (startIdx == -1) return "";

        // Value starts after the colon
        int valueStart = startIdx + target.length();
        String remainder = data.substring(valueStart).trim();

        if (remainder.startsWith("\"")) {
            // It's a string value: find the text between quotes
            return remainder.substring(1, remainder.indexOf("\"", 1));
        } else {
            // It's a number value: find the text until the next comma or end of object
            int commaIdx = remainder.indexOf(",");
            if (commaIdx == -1) {
                // Remove trailing brace if it's the last item in the object
                return remainder.replace("}", "").trim();
            }
            return remainder.substring(0, commaIdx).trim();
        }
    }

    // Getter for transaction type
    public static String getType(Transaction txn) {
        return txn.transactionType;
    }

    // Getter for customer id
    public String getCustomerId() {
        return customerId;
    }

    // Getter for amount
    public double getAmount() {
        return amount;
    }
}