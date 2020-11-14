package com.thoughtworks.codepairing.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCart {
    private List<Product> products;
    private Customer customer;

    public ShoppingCart(Customer customer, List<Product> products) {
        this.customer = customer;
        this.products = products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Order checkout() {
        HashSet<String> set = new HashSet<>();
        double totalPrice = 0;
        int loyaltyPointsEarned = 0;
        for (Product product : products) {
            double discount = 0;
            if (product.getProductCode().startsWith("DIS_10")) {
                discount = (product.getPrice() * 0.1);
                loyaltyPointsEarned += (product.getPrice() / 10);
            } else if (product.getProductCode().startsWith("DIS_15")) {
                discount = (product.getPrice() * 0.15);
                loyaltyPointsEarned += (product.getPrice() / 15);
            } else if(product.getProductCode().startsWith(("DIS_20"))){
                discount = (product.getPrice() * 0.2);
                loyaltyPointsEarned += (product.getPrice() / 20);
            }else if (product.getProductCode().startsWith("BULK_BUY_2_GET_1")){
                String productCode = product.getProductCode();
                if(set.contains(productCode)){
                    discount = product.getPrice() * 1;
                    set.remove(productCode);
                }else{
                    set.add(productCode);
                }
            }else{
                loyaltyPointsEarned += (product.getPrice() / 5);
            }
            totalPrice += product.getPrice() - discount;
        }
        totalPrice = totalPrice > 500 ? totalPrice * 0.95 : totalPrice;
        return new Order(totalPrice, loyaltyPointsEarned);
    }

    @Override
    public String toString() {
        return "Customer: " + customer.getName() + "\n" + "Bought:  \n" + products.stream().map(p -> "- " + p.getName()+ ", "+p.getPrice()).collect(Collectors.joining("\n"));
    }
}
