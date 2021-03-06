package com.github.maxopoly.finale.listeners;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.github.maxopoly.finale.Finale;
import com.github.maxopoly.finale.misc.ArmourModifier;
import com.github.maxopoly.finale.misc.ItemUtil;
import com.github.maxopoly.finale.misc.WeaponModifier;

public class WeaponModificationListener implements Listener {
	
	private final WeaponModifier manager = Finale.getPlugin().getManager().getWeaponModifer();

	@EventHandler
	public void weaponMod(InventoryClickEvent e) {
		ItemStack is = e.getCurrentItem();
		if (is == null) {
			return;
		}
		WeaponModifier weaponMod = Finale.getPlugin().getManager().getWeaponModifer();
		
		double adjustedDamage = weaponMod.getDamage(is.getType());
		double adjustedAttackSpeed = weaponMod.getAttackSpeed(is.getType());
		if (adjustedAttackSpeed == -1.0 && adjustedDamage == -1) {
			return;
		}
		ItemStack result = ItemUtil.setDamage(ItemUtil.setAttackSpeed(is, adjustedAttackSpeed), adjustedDamage);
		e.setCurrentItem(result);
	}
	
	@EventHandler
	public void armourMod(InventoryClickEvent e) {
		ItemStack is = e.getCurrentItem();
		if (is == null) {
			return;
		}
		ArmourModifier armourMod = Finale.getPlugin().getManager().getArmourModifier();
		
		double toughness = armourMod.getToughness(is.getType());
		double armour = armourMod.getArmour(is.getType());
		double knockbackResistance = armourMod.getKnockbackResistance(is.getType());

		if (toughness == -1 && armour == -1 && knockbackResistance == -1) {
			return;
		}
		if (toughness == -1) {
			toughness = ItemUtil.getDefaultArmourToughness(is);
		}
		if (armour == -1) {
			armour = ItemUtil.getDefaultArmour(is);
		}
		if (knockbackResistance == -1) {
			knockbackResistance = ItemUtil.getDefaultKnockbackResistance(is);
		}

		ItemStack result = ItemUtil.setArmour(ItemUtil.setArmourToughness(ItemUtil.setArmourKnockbackResistance(is, knockbackResistance), toughness), armour);
		e.setCurrentItem(result);
	}

}
