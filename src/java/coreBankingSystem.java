// Imports
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class coreBankingSystem {
    public static void main(String[] args) {
        try {
            // Load both files
            String transJson = Files.readString(Paths.get("data/pending_transactions.json"));
            String accountsJson = Files.readString(Paths.get("data/accounts.json"));

            // Parse the JSON strings into lists of objects
            List<Transaction> transactions = Transaction.parseTransactions(transJson);
            List<Account> accounts = Account.parseAccounts(accountsJson);

            // Process transactions
            for (Transaction txn : transactions) {
                // Find the matching account
                Account targetAccount = null;
                for (Account acc : accounts) {
                    if (acc.customerId.equals(txn.getCustomerId())) {
                        targetAccount = acc;
                        break;
                    }
                }

                // If no matching account, skip this transaction
                if (targetAccount == null) continue;

                // Process based on transaction type
                if (Transaction.getType(txn).equals("deposit")) 
                {
                    // Update balance
                    targetAccount.balance += txn.getAmount();

                    // Apply 1% bonus for large deposits
                    if (txn.getAmount() > 1000) 
                    {
                        targetAccount.balance += (txn.getAmount() * 0.01);
                    }
                } 
                else if (Transaction.getType(txn).equals("withdrawal")) 
                {
                    // Check for sufficient funds before withdrawal
                    if (targetAccount.balance >= txn.getAmount()) 
                    {
                        // Update balance
                        targetAccount.balance -= txn.getAmount();
                    } 
                    // If insufficient funds, skip this transaction and print a message
                    else 
                    {
                        System.out.println("REJECTED: Insufficient funds for " + targetAccount.customerName);
                    }
                }
            }

            // --- Save updated accounts back to JSON ---

            // Create a new stringbuilder to construct the JSON array
            StringBuilder sb = new StringBuilder("[\n");

            // Loop through accounts and convert each to JSON string
            for (int i = 0; i < accounts.size(); i++) {
                sb.append(accounts.get(i).toJson());
                if (i < accounts.size() - 1) sb.append(",\n");
            }

            // Close the JSON array
            sb.append("\n]");

            // Write the string to accounts.json
            Files.writeString(Paths.get("data/accounts.json"), sb.toString());

            // Print success message
            System.out.println("Success: Processed " + transactions.size() + " transactions and updated accounts.json.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}