import java.util.*;

class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }


    public void updatePrice() {
        
        this.price += (Math.random() * 10 - 5);
        if (this.price < 1) this.price = 1; 
    }
}

class Portfolio {
    private Map<String, Integer> holdings;
    private double balance;

    public Portfolio(double initialBalance) {
        this.holdings = new HashMap<>();
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void buyStock(String symbol, int quantity, double price) {
        double cost = quantity * price;
        if (cost > balance) {
            System.out.println("Insufficient funds!");
            return;
        }

        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
        balance -= cost;
        System.out.println("Bought " + quantity + " shares of " + symbol);
    }

    public void sellStock(String symbol, int quantity, double price) {
        if (!holdings.containsKey(symbol) || holdings.get(symbol) < quantity) {
            System.out.println("Not enough shares to sell!");
            return;
        }

        holdings.put(symbol, holdings.get(symbol) - quantity);
        balance += quantity * price;
        System.out.println("Sold " + quantity + " shares of " + symbol);
    }

    public void viewPortfolio(Map<String, Stock> market) {
        System.out.println("\nYour Portfolio:");
        for (var entry : holdings.entrySet()) {
            String symbol = entry.getKey();
            int shares = entry.getValue();
            double stockPrice = market.get(symbol).getPrice();
            System.out.println(symbol + ": " + shares + " shares @ Rs" + stockPrice);
        }
        System.out.println("Balance: Rs" + balance);
    }
}

public class StockTradingPlatform {
    private static Map<String, Stock> market = new HashMap<>();
    private static Portfolio portfolio = new Portfolio(10000); 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        setupMarket();

        System.out.println("Welcome to the Stock Trading Simulator!");
        while (true) {
            System.out.println("\n1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> viewMarket();
                case 2 -> buyStock(scanner);
                case 3 -> sellStock(scanner);
                case 4 -> portfolio.viewPortfolio(market);
                case 5 -> {
                    System.out.println("Exiting... Happy Trading!");
                    return;
                }
                default -> System.out.println("Invalid option! Try again.");
            }
        }
    }

    private static void setupMarket() {
        market.put("AAPL", new Stock("AAPL", 150));
        market.put("GOOGL", new Stock("GOOGL", 2800));
        market.put("TSLA", new Stock("TSLA", 700));
    }

    private static void viewMarket() {
        System.out.println("\nStock Market Prices:");
        for (Stock stock : market.values()) {
            stock.updatePrice(); 
            System.out.println(stock.getSymbol() + ": Rs" + stock.getPrice());
        }
    }

    private static void buyStock(Scanner scanner) {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.next().toUpperCase();
        if (!market.containsKey(symbol)) {
            System.out.println("Stock not found!");
            return;
        }

        System.out.print("Enter quantity to buy: ");
        int quantity = scanner.nextInt();
        portfolio.buyStock(symbol, quantity, market.get(symbol).getPrice());
    }

    private static void sellStock(Scanner scanner) {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.next().toUpperCase();
        if (!market.containsKey(symbol)) {
            System.out.println("Stock not found!");
            return;
        }

        System.out.print("Enter quantity to sell: ");
        int quantity = scanner.nextInt();
        portfolio.sellStock(symbol, quantity, market.get(symbol).getPrice());
    }
}
