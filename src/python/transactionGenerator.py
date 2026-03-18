# Import necessary libraries
import uuid
import datetime
import random
import json

# Possible transaction types
transaction_types = ["withdrawal", "deposit"]

# Current customers, get id and name from accounts.json
with open("data/accounts.json", "r") as f:
    accounts = json.load(f)
current_customers = [{"id": account["customer_id"], "name": account["customer_name"]} for account in accounts]                                          

# Function to generate a new transaction
def generate_transaction():
    new_transaction = {
        "id": str(uuid.uuid4()),  # Generate a unique ID for the transaction
        "customer_id": random.choice(current_customers)["id"],  # Select a random customer ID
        "amount": round(random.uniform(100, 10000), 2),  # Generate a random amount between 100 and 10000
        "timestamp": datetime.datetime.now().isoformat(),  # Store the current timestamp
        "type": random.choice(transaction_types)  # Set the transaction type
    }
    return new_transaction

# Generate ten random transactions and save them to "pending_transactions.json"
transactions = [generate_transaction() for _ in range(10)]
with open("data/pending_transactions.json", "w") as f:
    json.dump(transactions, f, indent=4)

# Print a message indicating that transactions have been generated
print("Python: Generated 10 random transactions and saved them to 'pending_transactions.json'.")