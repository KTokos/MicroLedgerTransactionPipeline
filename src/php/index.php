<?php
    // Logic: Load and decode data
    $jsonData = file_get_contents('../../data/accounts.json');
    $accounts = json_decode($jsonData, true);

    // Helper function for currency formatting
    function formatMoney($number) {
        return '$' . number_format($number, 2);
    }
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MicroLedger | Member Portal</title>
    <style>
        :root {
            --primary: #2c3e50;
            --accent: #27ae60;
            --danger: #e74c3c;
            --bg: #f4f7f6;
        }
        body { 
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; 
            background-color: var(--bg);
            color: var(--primary);
            margin: 0;
            padding: 40px;
        }
        .container {
            max-width: 900px;
            margin: auto;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        header {
            border-bottom: 2px solid var(--bg);
            margin-bottom: 30px;
            padding-bottom: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        h1 { margin: 0; font-size: 1.5rem; color: var(--primary); }
        
        table { 
            width: 100%; 
            border-collapse: collapse; 
            margin-top: 10px;
        }
        th { 
            text-align: left; 
            background-color: var(--bg); 
            padding: 15px;
            font-weight: 600;
        }
        td { 
            padding: 15px; 
            border-bottom: 1px solid #eee; 
        }
        tr:hover { background-color: #f9f9f9; }

        .balance { font-family: 'Courier New', monospace; font-weight: bold; }
        .low-balance { color: var(--danger); }
        .good-balance { color: var(--accent); }

        .badge {
            font-size: 0.8rem;
            padding: 4px 8px;
            border-radius: 4px;
            background: #eee;
        }
    </style>
</head>
<body>

<div class="container">
    <header>
        <h1>MicroLedger Member Portal</h1>
        <div class="badge">System Status: Online</div>
    </header>

    <table>
        <thead>
            <tr>
                <th>Customer Name</th>
                <th>Account ID</th>
                <th>Current Balance</th>
            </tr>
        </thead>
        <tbody>
            <?php foreach ($accounts as $account): ?>
                <?php 
                    $isLow = $account['balance'] < 15000; // Highlight if under $15k
                    $balanceClass = $isLow ? 'low-balance' : 'good-balance'; 
                ?>
                <tr>
                    <td><strong><?php echo htmlspecialchars($account['customer_name']); ?></strong></td>
                    <td><code style="font-size: 0.8rem;"><?php echo $account['customer_id']; ?></code></td>
                    <td class="balance <?php echo $balanceClass; ?>">
                        <?php echo formatMoney($account['balance']); ?>
                        <?php if($isLow): ?> ⚠️ <?php endif; ?>
                    </td>
                </tr>
            <?php endforeach; ?>
        </tbody>
    </table>
    
    <p style="margin-top: 20px; font-size: 0.8rem; color: #888;">
        Last updated: <?php echo date("Y-m-d H:i:s"); ?>
    </p>
</div>

</body>
</html>