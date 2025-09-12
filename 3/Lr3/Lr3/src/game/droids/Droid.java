package game.droids;

import game.inventory.Item;
import java.util.ArrayList;
import java.util.List;

public class Droid {
    protected String name;
    protected int health;
    protected int damage;
    protected List<Item> inventory = new ArrayList<>();

    public Droid(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getDamage() { return damage; }
    public boolean isAlive() { return health > 0; }

    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0) health = 0;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void useItem(int index, Droid target) {
        if (index >= 0 && index < inventory.size()) {
            inventory.get(index).use(this, target);
            inventory.remove(index);
        }
    }

    public List<Item> getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        return name + " [HP=" + health + ", DMG=" + damage + ", Items=" + inventory.size() + "]";
    }
}
