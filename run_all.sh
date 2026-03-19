#!/bin/bash

# --- Color Codes for pretty output ---
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}Step 1: Python - Generating Accounts and Transactions...${NC}"
python src/python/accountGenerator.py
python src/python/transactionGenerator.py

echo -e "\n${BLUE}Step 2: Java - Compiling Engine...${NC}"
javac src/java/*.java

echo -e "${BLUE}Step 3: Java - Processing Transactions...${NC}"
# Run Java from root, pointing to the class files in src/java
java -cp src/java coreBankingSystem

echo -e "\n${GREEN}Pipeline Complete!${NC}"
echo -e "To start the web server, run: php -S localhost:8000 -t src/php"