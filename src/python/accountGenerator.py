# Import necessary libraries
import uuid
import random
import json

# Current customers
current_customers = [
    {"id": str(uuid.uuid4()), "name": "Alice"},
    {"id": str(uuid.uuid4()), "name": "Bob"},
    {"id": str(uuid.uuid4()), "name": "Charlie"},
    {"id": str(uuid.uuid4()), "name": "Diana"},
    {"id": str(uuid.uuid4()), "name": "Eve"}
]

# Generate a json file "accounts.json" with the current customers id and name and a random starting balance
accounts = []
for customer in current_customers:
    account = {
        "id": str(uuid.uuid4()),  # Generate a unique ID for the account
        "customer_id": customer["id"],  # Link the account to the customer ID
        "customer_name": customer["name"],  # Store the customer's name
        "balance": round(random.uniform(1000, 50000), 2)  # Generate a random starting balance between 1000 and 50000
    }
    accounts.append(account)

# Save the accounts to "accounts.json"
with open("data/accounts.json", "w") as f:
    json.dump(accounts, f, indent=4)

# Print a message indicating that accounts have been generated
print("Python: Generated accounts for current customers and saved them to 'accounts.json'.")