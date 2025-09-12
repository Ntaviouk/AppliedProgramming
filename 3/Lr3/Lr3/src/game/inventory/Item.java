package game.inventory;

import game.droids.Droid;

public abstract class Item {
    protected String name;
    public Item(String name) { this.name = name; }
    public abstract void use(Droid user, Droid target);
    @Override
    public String toString() { return name; }
}