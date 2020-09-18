package com.example.graymatter.Social;

import java.util.Optional;

public interface PlayerMapperInterface {
    Optional<Player> find(int friendID);
    void insert(Player player) throws PlayerMapperException;
    void update(Player player) throws PlayerMapperException;
    void delete(Player player) throws PlayerMapperException;

}
