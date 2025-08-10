package com.divineworld.story;

import com.divineworld.tribe.Doctrine;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.Material;

public class DoctrineBookGenerator {

    public static ItemStack generateBook(Doctrine doctrine) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setTitle("📖 Teachings of " + doctrine.getName());
        meta.setAuthor("Scribe of " + doctrine.getFounder());

        StringBuilder content = new StringBuilder("These are the beliefs of our tribe:\n\n");
        for (String belief : doctrine.getBeliefs()) {
            content.append("- ").append(belief).append("\n");
        }

        meta.addPages(content.toString().split("(?<=\\G.{240})"));
        book.setItemMeta(meta);
        return book;
    }
}
