package edu.brown.cs32.fall21.Nov04;

import java.util.Collection;

public abstract class Animal implements Talkative {
    private String name;
    private Collection<Animal> friends;

    Animal(String name, Collection<Animal> friends) {
        this.name = name;
        this.friends = friends; // ???
    }

    public String getName() {
        return name;
    }

    public Collection<Animal> getFriends() {
        return friends;
    }

    public boolean addFriend(Animal friend) {
        if(this.friends.contains(friend)) return false;
        friends.add(friend);
        return true;
    }
}
