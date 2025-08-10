package com.divineworld.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

public class BookFactory {

    public static ItemStack genesisCodex() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setTitle("Genesis Codex");
        meta.setAuthor("Oracle");
        meta.setPages(List.of("Use this book to create the first civilizations."));
        book.setItemMeta(meta);
        return book;
    }

    public static ItemStack firstFlameBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setTitle("Teachings of the First Flame");
        meta.setAuthor("Oracle");
        meta.setPages(List.of("This world began in flame. From that fire, you were born."));
        book.setItemMeta(meta);
        return book;
    }
}
